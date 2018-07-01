/**
 * @file websh.c
 * @author Michael Zahrl <e01627763@student.tuwien.ac.at>
 * @date 2017-05-18
 *
 * @brief Schedule for OSUE exercise 2 `websh'.
 */

#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include <unistd.h>
#include <string.h>
#include <sys/wait.h>
#include <getopt.h>

//Constant of line length
#define LINE_LENGTH 1000

//Programname of the program, default websh
static char *programname = "websh";

//Flag for option e
static bool opt_e 	= false;

//Flag for option h
static bool opt_h	= false;

//Flag for option s
static bool opt_s	= false;

//String to be searched given by s flag
static char *searchText	= "";

//Type of tag the found line will be put into
static char *markTag	= "";

//Variable for command inputs
static char **commands;

//Number of commands entered
static size_t lineCounter = 0;

//Pipe for communication between processes
static int fpipe[2];

//Variable for input stream
static FILE *stream;


/**
 * @brief Writes the usage of the program to the standard output
 */
static void usage(void) {
	fprintf(stderr, "Usage:\n\t%s: [-e] [-h] [-s WORD:TAG]\n", programname);
	exit(EXIT_FAILURE);
}



/**
 * @brief Frees all memory used by the program and closes open pipe ends
 */
static void flush(void) {
	//Check if read or write end of pipe are still open
	if(fpipe[0] > 0) {
		close(fpipe[0]);
	}
	if(fpipe[1] > 0) {
		close(fpipe[1]);
	}

	if(stream != NULL) {
		fclose(stream);
	}

	for(int j = 0; j < lineCounter; j++) {
		if(commands[j] != NULL) free(commands[j]);
	}
	if(commands != NULL){
		free(commands);
	}
}

/**
 * @briefRemoves '\n' characters in a given string, and replaces them with '\0'
 * @param str The string to remove line breaks from
 */
static void rmLinebreak(char* str) {
	//Ceck string if a line break occurs somewhere
	char *lineBreak = strchr(str, '\n');
	if(lineBreak != NULL) {
		*lineBreak = '\0';
	}
}

/**
 * @brief Execute given command in the system shell.
 * @param command Command to excecute
 * @return EXIT_SUCCESS if no error occurs
 */
static int execCommand(char *command) {
	//Array of pointers must be terminated by a NULL pointer and first must point to filename
	// here it is the system shell
	char *commands[] = {"sh", "-c", command, NULL};
	//Close read end of pipe
	close(fpipe[0]);

	//Redirect standard output to write end of fpipe
	dup2(fpipe[1], 1);

	//Execute commands in system shell as previous defined
	if(execvp("/bin/sh", commands) == -1) {
		fprintf(stderr,"[%s]: ERROR while executing command\n", programname);
		exit(EXIT_FAILURE);
	}

	return EXIT_SUCCESS;
}

/**
 * @brief Print result of given command execution 
 * 			and format it into a webpage if option is set
 * @details The results of the command execution, if successfull will be printen out
 * 			and if opt_h is set formated into a webpage
 * @param lineIndex  At what command the program currently is
 * @param command Executed command
 * @return EXIT_SUCCESS if no error occurs
 */
static int printOutput(int lineIndex, char *command) {
	//Close writing end of pipe
	close(fpipe[1]);
	char* output = malloc(LINE_LENGTH);

	//read from pipe
	// (pipe that the child process which executes the command writes too)
	stream = fdopen(fpipe[0], "r");
	if(stream == NULL) {
		fprintf(stderr,"[%s]: ERROR opening stream\n", programname);
		exit(EXIT_FAILURE);
	}

	//check if html option was set and no command was output
	if(opt_e && lineIndex == 0) {
		fprintf(stdout, "<html><head></head><body>\n");
	}

	//check is option h is set
	if(opt_h) {
		fprintf(stdout, "<h1>%s</h1>\n", command);
	}

	//Get string from stream (stdout of first child)
	while(fgets(output, LINE_LENGTH, stream) != NULL) {
		rmLinebreak(output);

		//Check if searched text is in string
		if(opt_s && strstr(output, searchText) != NULL) {
			fprintf(stdout, "<%s>%s</%s><br />\n", markTag, output, markTag);
		} else {
			fprintf(stdout, "%s<br />\n", output);
		}
	}
	if(opt_e && lineIndex == (lineCounter - 1))
		fprintf(stdout, "</body></html>\n");

	if(output != NULL) {
		free(output);
	}
	fflush(stdout);

	return EXIT_SUCCESS;
}

int main(int argc, char *argv[]) {
	programname = argv[0];
	int opt;
	while((opt = getopt(argc, argv, "ehs:")) != -1) {
		switch(opt) {
			case 'e':
				//Check if option is already set
				if(opt_e) {
					usage();
				}
				opt_e = true;
				break;
			case 'h':
				//Check if option is already set
				if(opt_h) {
					usage();
				}
				opt_h = true;
				break;
			case 's':
				//Check if option is already set or if a word was entered
				if(opt_s || searchText[0] != '\0') {
					usage();
				}
				opt_s 	= true;
				char *tag;

				//Get the tag
				tag = strchr(optarg, ':');
				if(tag == NULL) {
					usage();
				}
				markTag	= tag + 1;

				size_t optarg_length = strlen(optarg);
				size_t tag_length = strlen(tag);
				//Set the : of the string to be the end of the string so
				//that the search word can be extracted
				optarg[optarg_length - tag_length] = '\0';

				searchText 	= optarg;

				if(strlen(markTag) == 0 || strlen(searchText) == 0) {
					usage();
				}
				break;
			default:
				usage();
				break;
		}
	}
	if(argc - optind > 0)
		usage();

	//Allocate mermory for variable commands
	commands 	= (char **) malloc(sizeof(char *));
	if(commands == NULL) {
		fprintf(stderr,"[%s]: ERROR with allocation\n", programname);
		exit(EXIT_FAILURE);
	}

	lineCounter = 0;
	//Allocate memory for first command
	commands[0] = (char *) 	malloc(LINE_LENGTH);
	if(commands[0] == NULL) {
		fprintf(stderr,"[%s]: ERROR while allocation\n", programname);
		exit(EXIT_FAILURE);
	}

	//Read from stdin
	while(fgets(commands[lineCounter], LINE_LENGTH, stdin) != NULL) {
		lineCounter++;

		//Allocate new memory for commands variable
		commands = (char **) realloc(commands, (lineCounter + 1) * LINE_LENGTH);
		if(commands == NULL) {
			fprintf(stderr,"[%s]: ERROR while allocation\n", programname);
			exit(EXIT_FAILURE);
		}

		commands[lineCounter] = (char *) malloc(LINE_LENGTH);
		if(commands[lineCounter] == NULL) {
			fprintf(stderr,"[%s]: ERROR while allocation\n", programname);
			exit(EXIT_FAILURE);
		}

	}

	//Check if any line where entered
	for(int i = 0; i < lineCounter; i++) {
		//Create pid for child processes
		pid_t 	child, grandchild;

		//Get current command
		char *command = commands[i];
		//Remove any line breaks from the command
		rmLinebreak(command);

		if(pipe(fpipe) < 0) {
			fprintf(stderr,"[%s]: ERROR creating pipe\n", programname);
			exit(EXIT_FAILURE);
		}

		//Create child process for excecuting commands
		switch(child = fork()) {
			//Error while creating child process
			case -1:
				fprintf(stderr,"[%s]: ERROR with fork\n", programname);
				exit(EXIT_FAILURE);
			case 0:
				//Execute the command if child process
				if(execCommand(command) != EXIT_SUCCESS) {
					fprintf(stderr,"[%s]: ERROR while executing\n", programname);
					exit(EXIT_FAILURE);
				}
				flush();
				exit(EXIT_SUCCESS);
			default:
				//Create a child process if parent process
				switch(grandchild = fork()) {
					//Error with creating child process
					case -1:
						fprintf(stderr,"[%s]: ERROR with fork of second child\n", programname);
						exit(EXIT_FAILURE);
					case 0:
						//If child process print output of command
						if(printOutput(i, command) != EXIT_SUCCESS) {
							fprintf(stderr,"[%s]: ERROR with printing output\n", programname);
							exit(EXIT_FAILURE);
						}
						flush();
						exit(EXIT_SUCCESS);
					default:
						close(fpipe[1]);
						break;
				}
				break;
		}

		int status;
		//Wait for child process to terminate
		while(wait(&status) > 0);
	}
	flush();
	return EXIT_SUCCESS;
}