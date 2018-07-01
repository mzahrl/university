#include <stdio.h>
#include <stdlib.h>
#include <sys/mman.h>
#include <fcntl.h>
#include <ctype.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <assert.h>
#include <semaphore.h>
#include <signal.h>
#include <errno.h>
#include <stdbool.h>
#include "common.h"

char *programname = "supervisor";

int bestsolution = 9;

volatile sig_atomic_t quit = 0;

int read_pos = 0;

sem_t *free_sem, *used_sem, *mutex_sem;

struct myshm *myshm;

/**
 * @brief Print message and exit program
 */
static void error_exit(char *message) {
	fprintf(stderr,"[%s] %s\n", programname, message);
	exit(EXIT_FAILURE);
}

/**
 * @brief Read from circular buffer
 * @details Checks if anything is writen in the circular buffer
 * 			and if so reads the data from the buffer
 */
char * circ_buf_read() {
	sem_wait(used_sem); // reading requires data (used space)
	char *val = myshm->data[read_pos];
	sem_post(free_sem); // reading frees up space
	read_pos = (read_pos + 1) % MAX_DATA;
	return val;
}

/**
 * @brief Signal handling
 */
void handle_signal(int signal) {
	quit = 1;
}

/**
 * @brief Signal Configutation for sigint and sigterm signals
 */
static void signalConfig() {
	struct sigaction sa;
	sa.sa_handler = handle_signal;
	sa.sa_flags = 0;

	const int allowedSignals[] = { SIGINT, SIGTERM};
	for(int i = 0; i < 2; i++) {
		if (sigaction(allowedSignals[i], &sa, NULL) < 0) {
			error_exit("Error with sigaction");
		}
	}

}


/**
 * @brief Prints usage of the program
 */
static void usage(void) {
	fprintf(stderr,"usage: %s", programname);
	exit(EXIT_FAILURE);
}

/**
 * @brief Creates resources
 * @return Creates semaphores and shared memory
 */
static void setup_resources() {
	/* Shared memory */
	int shmfd = shm_open(SHM_NAME, O_RDWR | O_CREAT, PERMISSION);

	if(shmfd < 0) {
		error_exit("Error with shm_open");
	}

	if (ftruncate(shmfd, sizeof(struct myshm)) < 0) {
		error_exit("Error with ftruncate");
	}

	myshm = mmap(NULL, sizeof(*myshm), PROT_READ | PROT_WRITE, MAP_SHARED, shmfd, 0);
	if (myshm == MAP_FAILED) {
		error_exit("Error with mmap");
	}

	if (close(shmfd) == -1) {
		error_exit("Error closing shared memory pointer");
	}

	/* Open semaphores */
	free_sem = sem_open(SEM_1, O_CREAT | O_EXCL, PERMISSION, MAX_DATA);
	used_sem = sem_open(SEM_2, O_CREAT | O_EXCL, PERMISSION, 0);
	mutex_sem = sem_open(SEM_3, O_CREAT | O_EXCL, PERMISSION, 1);

	if (free_sem == SEM_FAILED || used_sem == SEM_FAILED || mutex_sem == SEM_FAILED) {
		error_exit("Error with sem_open");
	}
}

/**
 * @brief Free resources
 * @return Frees creates semaphores and shared memory
 */
void free_resources() {
	if (munmap(myshm, sizeof(*myshm)) == -1) {
		error_exit("Error with munmap");
	}

	if (sem_close(free_sem) == -1 || sem_close(used_sem) == -1 || sem_close(mutex_sem) == -1) {
		error_exit("Error with sem_close");
	}

	if (sem_unlink(SEM_1) == -1 || sem_unlink(SEM_2) == -1 || sem_unlink(SEM_3) == -1) {
		error_exit("Error with sem_unlink");
	}

	if (shm_unlink(SHM_NAME) == -1) {
		error_exit("Error with shm_unlink");
	}
}

int main(int argc, char *argv[]) {
	programname = argv[0];
	atexit(free_resources); //At exit call free_resources function

	signalConfig();
	int opt;
	while ((opt = getopt(argc, argv, "")) != -1) {
		switch (opt) {
			case '?':
				usage();
			default:
				assert(0);
				break;
		}
	}

	setup_resources();

	unsigned currentsolution;
	char *message;

	while(!quit){ //Execute code until sigint or sigterm occurs
		message = circ_buf_read(); //read from circular buffer
		currentsolution = (int)message[0]; //Store first character of message which contains the number of edges
		if(currentsolution != 0){
			currentsolution = message[0] - 48;
		}

		if(currentsolution < bestsolution && currentsolution>=0) { //Check if new best solution was found and print it out
			if(currentsolution == 0 && !quit){ //Graph is acyclic
				printf("The graph is acyclic\n");
				myshm->state = true;
				quit = 1;
			}
			else if(!quit) {
				bestsolution = currentsolution;
				message++;
				printf("Solution with %d edges: %s\n", bestsolution, message);
			}
		}
	}
	myshm->state = true; //set flag so generators stop

	free_resources();

	exit(EXIT_SUCCESS);
}