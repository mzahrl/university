/**
 * @file schedule.c
 * @author Michael Zahrl <e01627763@student.tuwien.ac.at>
 * @date 2017-02-12
 *
 * @brief Schedule for OSUE exercise 2 `randsched'.
 */
#include <stdlib.h>
#include <stdio.h>
#include <time.h>
#include <errno.h>
#include <fcntl.h>
#include <limits.h>
#include <stdarg.h>
#include <stdbool.h>
#include <string.h>
#include <sys/stat.h>
#include <sys/wait.h>
#include <unistd.h>
#include <assert.h>

//Value for the time window that the child process will wait before continuing, default 1 second
static unsigned int opt_s = 1;

//Value for the maximum time window that the child process will wait before continuing, default 0 seconds
static unsigned int opt_f = 0;

//Programpath which will be executed continuity
static char *programfile;

//Path to the emergency program, in case that program fails
static char *emergencyfile;

//Filename of the logfile
static char *logfile;

//Quit flag for signal handling
volatile sig_atomic_t quit= 0;

//Programname of the program, default schedule
static char *programname = "schedule";


/**
 * @brief Fork a new process which will execute a program and redirects stdout
 * @param program Name of the File that will be executed
 * @param pipefd File descriptor pointing to the standard output
 * @return 	Exit code of the function call or a negative number if the
 * 			the forked process did not exit
 */
static int execProgram(const char*program, int pipefd){
	pid_t grandchild = fork();

	if(grandchild < 0){
		(void) fprintf(stderr,"[%s]: ERROR Creating Grandchild Process\n", programname);
		return(EXIT_FAILURE);
	}

	if(grandchild == 0){

		//Duplicate pipefd to STDOUT
		if(dup2(pipefd, STDOUT_FILENO) < 0){
			(void) fprintf(stderr,"[%s]: ERROR Duplicating File Descriptor\n", programname);
			return(EXIT_FAILURE);
		}

		if(execlp(program, program, NULL) < 0){
			(void) fprintf(stderr,"[%s]: ERROR Executing Program %s\n", programname,program);
			return(EXIT_FAILURE);
		}
	}

	int status;

	//Wait for the cild process
	(void) wait(&status);

	//Check if child exiteded and return the code if so
	if(WIFEXITED(status)){
		return WEXITSTATUS(status);
	}

	//If we were interrupted by a signal handle, the child of course exiteted dirty, so just return 2
	if(!quit) {
		fprintf(stderr, "[%s]: ERROR Exiting of Child\n", programname);
		return (EXIT_FAILURE);
	}
	return 2;
}


/**
 * @brief Signal handler for signals SIGINT(Interrupt) and SIGTERM(Terminate)
 * @param signal The type of the signal
 */
static void handle_signal(int signal){
	quit = 1;
}

/**
 * @brief 	Signal configuration to handle signals SIGINT and SIGTERM by executing
 * 			handle_signal function
 *
 */
static void signalConfig(void){

	struct sigaction sa;
	sa.sa_handler = handle_signal;
	sa.sa_flags = 0;

	const int allowedSignals[] = { SIGINT, SIGTERM};
	for(int i = 0; i < 2; i++) {
		if (sigaction(allowedSignals[i], &sa, NULL) < 0) {
			(void) fprintf(stderr, "[%s]: ERROR with Sigaction\n", programname);
			exit(EXIT_FAILURE);
		}
	}

}


int main(int argc, char *argv[]) {
	programname = argv[0];
	int opt;
	while ((opt = getopt (argc, argv, "s:f:")) != -1)
	{
		switch (opt)
		{
			case 's':
				opt_s = (int)strtoul(optarg, NULL, 10);;
				break;
			case 'f':
				opt_f = (int)strtoul(optarg, NULL, 10);
				break;
			case '?':
				exit(EXIT_FAILURE);
			default:
				assert(0);
				break;
		}
	}
	//Check if program is NULL or emergency program is NULL or logfile is NULL
	if (argv[optind] == NULL
		|| argv[optind + 1] == NULL
		|| argv[optind + 2] == NULL) {
		(void) fprintf(stderr,"[%s]: ERROR With Passed Arguemnts\n", programname);
		exit(EXIT_FAILURE);
	}
	programfile = argv[optind];
	emergencyfile = argv[optind + 1];
	logfile = argv[optind + 2];

	//Pipe for parents stdout and the logfile
	int printpipefd[2];

	//Pipe only for parents stdout
	int logpipefd[2];

	signalConfig();

	if(pipe(printpipefd) < 0 || pipe(logpipefd) < 0){
		(void) fprintf(stderr,"[%s]: ERROR Creating Pipes\n", programname);
		exit(EXIT_FAILURE);
	}

	//Create a new child process
	pid_t child = fork();

	if(child < 0){
		(void) fprintf(stderr,"[%s]: ERROR Creating Child Process\n", programname);
		exit(EXIT_FAILURE);
	}


	//Only true for the child process
	if(child == 0){
		int execProgStatus;

		// Close read ends of child process
		(void) close(printpipefd[0]);
		(void) close(logpipefd[0]);

		do {
			//Wait between f+s seconds then execute program in variable programfile
			sleep(opt_s + (opt_f == 0 ? 0 : rand() % (opt_f + 1)));

			if ((execProgStatus = execProgram(programfile, printpipefd[1])) < 0){
				(void) close(printpipefd[1]);
				(void) close(logpipefd[1]);
				(void) fprintf(stderr,"[%s]: ERROR Executing Program %s\n",programname,programfile);
			}
		} while(!quit && execProgStatus == 0);

		/*
		 * Program terminated with error -> execute emergency program, write in logpipe
		 * because we dont want the output in the logfile only in partens stdout
		*/
		if(execProgStatus != 0 && !quit){
			execProgStatus = execProgram(emergencyfile, logpipefd[1]);
		}

		/*
		 * Check the result(exit code) of the emergency program and write the result to the printpipe
		 * and close the write ends of the pipes because we dont need them anymore
		*/
		if(execProgStatus == EXIT_SUCCESS){
			write(printpipefd[1], "EMERGENCY SUCCESSFUL\n",21);


			//Close write ends of pipes
			(void) close(printpipefd[1]);
			(void) close(logpipefd[1]);

			exit(EXIT_SUCCESS);
		}
		else{
			write(printpipefd[1], "EMERGENCY FAILURE\n", 18);

			//Close write ends of pipes
			(void) close(printpipefd[1]);
			(void) close(logpipefd[1]);

			exit(EXIT_FAILURE);
		}
	}


	//Close write ends of parent process
	(void) close(printpipefd[1]);
	(void) close(logpipefd[1]);

	/* open file logfile if it does not exist create it,
	 * append the file with the new content, give the user read and write rights
	*/
	int logfp = open(logfile, O_WRONLY|O_APPEND|O_CREAT, S_IRUSR|S_IWUSR);

	int count;
	char buf;

	//Reading from printpipefd and writing to STDOUT and the Logfile
	while(!quit) {
		count = read(printpipefd[0], &buf, 1);


		//Check if an error occurred during reading from the printpipe
		if (count < 0) {
			(void) close(printpipefd[0]);
			(void) close(logpipefd[0]);
			(void) close(logfp);

			//Interrupted by a Signal, then we dont have to throw an error and continue
			if(quit){
				continue;
			}
			(void) fprintf(stderr, "[%s]: ERROR Reading from PrintPipe\n", programname);
			exit(EXIT_FAILURE);
		}
		//No character read, we dont need to read anymore from the printpipe
		if(count == 0){
			break;
		}
		if(write(logfp, &buf, 1) != 1 || write(STDOUT_FILENO, &buf, 1) != 1){
			(void) close(printpipefd[0]);
			(void) close(logpipefd[0]);
			(void) close(logfp);
			(void) fprintf(stderr, "[%s]: ERROR Writing to File %s or STDOUT\n", programname, logfile);
			exit(EXIT_FAILURE);
		}
	}

	(void) close(printpipefd[0]);

	//Reading from logpipe and writing to STDOUT of parent
	while(!quit) {
		count = read(logpipefd[0], &buf, 1);

		if (count < 0) {
			(void) close(logpipefd[0]);
			(void) close(logfp);


			(void) fprintf(stderr, "[%s]: ERROR Reading from LogPipe\n", programname);
			exit(EXIT_FAILURE);
		}

		if(count == 0){
			break;
		}

		if(write(STDOUT_FILENO, &buf, 1) != 1){
			(void) close(logpipefd[0]);
			(void) close(logfp);
			(void) fprintf(stderr, "[%s]: ERROR Writing to File %s or STDOUT\n", programname, logfile);
			exit(EXIT_FAILURE);
		}
	}

	(void) close(logpipefd[0]);

	//Wait for child process
	int status;
	(void) wait(&status);

	//Clean close of the logfile
	(void) close(logfp);

	exit(EXIT_SUCCESS);
}





