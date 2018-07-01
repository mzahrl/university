/**
 * @file rventgas.c
 * @author Michael Zahrl <e01627763@student.tuwien.ac.at>
 * @date 2017-02-12
 *
 * @brief rshutdown for OSUE exercise 2 `randsched'.
 */
#include <stdlib.h>
#include <stdio.h>
#include <time.h>


//Generate random number between 0-6, exit and print STDOUT depenting on the number
int main(int argc, char *argv[]) {
	srand(time(NULL));
	if(rand() % 3 != 2){
		(void) printf("SHUTDOWN COMPLETED\n");
		exit(EXIT_SUCCESS);
	}
	(void) printf("KaBOOM!\n");
	exit(EXIT_FAILURE);
}