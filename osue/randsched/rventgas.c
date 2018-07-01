/**
 * @file rventgas.c
 * @author Michael Zahrl <e01627763@student.tuwien.ac.at>
 * @date 2017-02-12
 *
 * @brief rventgas for OSUE exercise 2 `randsched'.
 */
#include <stdlib.h>
#include <stdio.h>
#include <time.h>

//Generate random number between 0-6, exit and print to STDOUT depenting on the number
int main(int argc, char *argv[]) {
	srand(time(NULL));
	if(rand() % 7 != 6){
		(void) printf("STATUS OK\n");
		exit(EXIT_SUCCESS);
	}
	(void) printf("PRESSURE TOO HIGH - IMMEDIATE SHUTDOWN REQUIRED\n");
	exit(EXIT_FAILURE);
}

