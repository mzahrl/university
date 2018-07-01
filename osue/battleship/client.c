/**
 * @file client.c
 * @author Michael Zahrl <e01627763@student.tuwien.ac.at>
 * @date 2017-06-11
 *
 * @brief Client for OSUE exercise 1B `Battleship'.
 */


// IO, C standard library, POSIX API, data types:
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <stdint.h>
#include <stdbool.h>
#include <string.h>
#include <limits.h>

// Assertions, errors, signals:
#include <assert.h>
#include <errno.h>
#include <signal.h>

// Time:
#include <time.h>

// Sockets, TCP, ... :
#include <sys/types.h>
#include <sys/socket.h>
#include <netdb.h>
#include <fcntl.h>

// stuff shared by client and server:
#include "common.h"

static char *port = DEFAULT_PORT;
static char *host = DEFAULT_HOST;



static struct addrinfo *ai = NULL;      // addrinfo struct
static int sockfd = -1;                 // socket file descriptor

char *programname = "client";
char parity;
int playfield[99];
unsigned char r;
unsigned char hit;

/*
 * @brief calculates the parity bit for a number
 * @param field, is the value for which the parity bit is calculated
 *
 * @description calculates how many ones the number has
 * 				by and linking ever bit of the number with 1.
 * 				If it has an even number the parity bit
 * 				is 0 else 1
 */
char calcParity(char field){
	int count = 0;
	for (int i = 0; i < 7; i++) {
		if(((field >> i) & 1) == 1){
			count++;
		}
	}
	if( (count % 2) == 0){
		return 0;
	}

	return 1;
}

/*
 * @brief generates a random number from 0-99
 * 		   and checks if the field was already shot at and calculates the parity bit.
 */
char generateMessage(void){
	do{
		r = rand() % 100;
	}while(playfield[r] != 0);
	playfield[r] = 1;
	parity = calcParity(r);
	return (r | (parity << 7));
}

/*
 * @brief interprets the message of the server
 * @param serverMessage, contains the message(Numbersequence) that the server transmitted
 */
void checkMessage(unsigned char serverMessage){
	serverMessage >>=2;
	hit <<=6;
	if(serverMessage == 1){
		fprintf(stdout,"[%s] game lost\n", programname);
		exit(EXIT_SUCCESS);
	}
	if(serverMessage == 2){
		fprintf(stderr,"[%s] ERROR: parity error\n", programname);
		exit(EXIT_SUCCESS);
	}
	if(serverMessage == 3){
		fprintf(stderr,"[%s] ERROR: invalid coordinate\n", programname);
		exit(EXIT_SUCCESS);
	}
	if(hit == 3){
		fprintf(stdout,"[%s] I won\n", programname);
		exit(EXIT_SUCCESS);
	}
}

int main(int argc, char *argv[]) {

	programname = argv[0];
	struct addrinfo hints;
	memset(&hints, 0, sizeof(hints));
	hints.ai_family = AF_INET;
	hints.ai_socktype = SOCK_STREAM;
	hints.ai_flags = AI_PASSIVE;
	int opt;
	while ((opt = getopt (argc, argv, "h:p:")) != -1)
	{
		switch (opt)
		{
			case 'h':
				host = optarg;
				break;
			case 'o':
				port = optarg;
				break;
			case '?':
				exit(EXIT_FAILURE);
			default:
				assert(0);
				break;
		}
	}

	int res = getaddrinfo(host, port, &hints, &ai);
	if(res < 0){
		fprintf(stderr,"[%s] ERROR on getting address information\n", programname);
		exit(EXIT_FAILURE);
	}
	sockfd = socket(ai->ai_family, ai->ai_socktype, ai->ai_protocol);
	if(sockfd < 0){
		fprintf(stderr,"[%s] ERROR opening socket\n", programname);
		exit(EXIT_FAILURE);
	}
	if(connect(sockfd, ai->ai_addr, ai->ai_addrlen) < 0) {
		fprintf(stderr, "%s: Error failed to connect to socket\n", programname);
		exit(EXIT_FAILURE);
	}
	int roundsplayed = 0;
	unsigned char message;
	unsigned char serverMessage;
	do{
		message=generateMessage();
		write(sockfd,&message,sizeof(char));
		read(sockfd,&serverMessage,sizeof(char));
		checkMessage(serverMessage);
		roundsplayed++;
	}while(roundsplayed<99);
	exit(EXIT_SUCCESS);
}

