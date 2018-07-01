/**
 * @file server.c
 * @author Michael Zahrl <e01627763@student.tuwien.ac.at>
 * @date 2017-06-11
 *
 * @brief Server for OSUE exercise 1B `Battleship'.
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

// Static variables for things you might want to access from several functions:
static const char *port = DEFAULT_PORT; // the port to bind to

// Static variables for resources that should be freed before exiting:
static struct addrinfo *ai = NULL;      // addrinfo struct
static int sockfd = -1;                 // socket file descriptor
static int connfd = -1;                 // connection file descriptor

// Struct for a battleship
typedef struct{
	int length;
	int position[4];
}battleship;

// Array containing the ships
battleship ships[6];

//Array containing the number of possible ships,
//needed for checking if the right amount of ships with right size where given
int maxShips[3] = {2,3,1};
int shipsunk = 0;
int playedrounds = 0;
char *programname = "server";
unsigned char status;
unsigned char serverMessage;

/*
 * @brief 	calculates the number to a coordination
 * @param 	x,defines the first value which should be a UpperCase Letter
 * 			y,defines the second value which should be a number
 */
static int calcNum(char x,char y){
	int b = x - 65;
	int z = y - 48;
	return b + (z*10);
}


/*
 * @brief 	enters the length and postion of a ship to the battleships array
 * @param 	start, defines the start coordinate of the ship
 * 			end, defines the end coordinate of the ship
 * 			direction, how the ship is aligned
 * 			shiplength, length of the ship
 * 			shipnumber, number of the ship
 */
static void enterShipCoord(int start,int end,int direction,int shiplength,int shipnumber){
	int counter=0;
	ships[shipnumber].length = shiplength;
	for(int i = start; i <= end; i+=direction) {
		ships[shipnumber].position[counter] = i;
		counter++;
	}
	maxShips[counter-2]--;
	while(counter<4){
		ships[shipnumber].position[counter] = -1;
		counter++;
	}
}

/*
 * @brief 	calculated the gap distance between ship coordinates and if the ship is correctly placed
 * @param	start, defines the start coordinate of the ship
 * 			end, defines the end coordinate of the ship
 * 			shipnumber, number of the ship
 *
 * @description	first the function checks how the coordinates have been entered and deceits what the multiplier is.
 * 				then if the distance is > 10, the difference modulo 10 should be 0 or the coordinates are wrong
 * 				after that it calls the enterShipCoord funtion
 */
static int calcShipCoord(int start, int end, int shipnumber){
	if(start < 0 || start > 99 || end < 0 || end > 99){
		return 1;
	}
	int multiplier = 1;
	if(start<end){
		multiplier = -1;
	}
	int distance = (start-end)*multiplier;
	if(distance > 10){
		if(distance % 10 != 0){
			return 2;
		}
		if((distance/10)+1 > 4){
			return 3;
		}
		multiplier == -1 ? enterShipCoord(start,end,10,(distance/10)+1,shipnumber) : enterShipCoord(end,start,10,(distance/10)+1,shipnumber);
	}
	else{
		if(distance+1 > 4){
			return 3;
		}
		multiplier == -1 ? enterShipCoord(start,end,1,distance+1,shipnumber) : enterShipCoord(end,start,1,distance+1,shipnumber);
	}
	return 0;
}

/*
 * @brief	outputs error messages
 * @param	code,is the code that was returned after calculating the ship coordinations
 * 			pch,are the current proceted coordination from the input
 */
void shiperror(int code, char *pch){
	if(code == 1){
		fprintf(stderr,"[%s] ERROR: coordinates outside of map: %s\n", programname,pch);
		exit(EXIT_FAILURE);

	}
	else if(code == 2){
		fprintf(stderr,"[%s] ERROR: ships must be aligned either horizontally or vertically: %s\n", programname,pch);
		exit(EXIT_FAILURE);
	}
	else if(code == 3){
		fprintf(stderr,"[%s] ERROR: the maximum shiplength is 4: %s\n", programname,pch);
		exit(EXIT_FAILURE);
	}
}

/*
 * @brief 	generates a ship with given coordinations
 * @param	buff, holds the chars witch the coordination
 * 			shipnumber, number of the ship
 */
int generateShip(char buff[], int shipnumber){
	char * pch;
	pch = strtok (buff," ");
	while (pch != NULL)
	{
		if(strlen(pch) != 4){
			fprintf(stderr,"[%s] ERROR: wrong syntax for ship coordinates: %s\n", programname,pch);
			exit(EXIT_FAILURE);
		}
		shiperror(calcShipCoord(calcNum(pch[0],pch[1]),calcNum(pch[2],pch[3]),shipnumber),pch);


		//Get next word
		pch = strtok (NULL, " ");
	}
	return 0;
}

/*
 * @brief 	checks if a ship has sunken
 * @param 	shiplength, length of the ship
 * 			shipnumber, number of the ship
 *
 * @description if all column of the ship are equals -1 then the ship has been hit on every spot and should sink
 */
int checkSunk(int shipnumber,int shiplength){
	for (int column = 0; column < shiplength; column++) {
		if(ships[shipnumber].position[column] !=-1){
			return 1;
		}
	}
	return 0;
}

/*
 * @brief checks if the round limit has been exceeded
 * @param playedrounds, how many round have already been played out
 */
unsigned char checkRound(int playedrounds){
	return ((playedrounds >= 80) ? 1 : 0);
}


/*
 * @brief check what the parity bit of a number should be
 * @param coord, number for what the parity bit will be calculated
 */
int checkParity(unsigned char coord){
	int count = 0;
	for (int i = 0; i < 8; i++) {
		if(((coord >> i) & 1) == 1){
			count++;
		}
	}
	if( (count % 2) == 0){
		return 0;
	}

	return 1;
}

/*
 * @brief removes the parity bit from the message of the client
 * @param the message from the client
 */
unsigned char removeParity(unsigned char message){
	message <<= 1;
	message >>= 1;
	return message;
}

/*
 * @brief generates the message for the client
 * @param status, contains the status code
 * 		  hit, contains the hit code
 *
 * @description the status code is shifted by 2 bits to the left and
 * 				status hit and 128 are or linked
 * 				the first bit (from 128) is shifted away
 */
unsigned char generateMessage(unsigned char status, unsigned char hit){
	unsigned char message = (128 | (status << 2));
	message= message | hit;
	message = removeParity(message);
	if(status == 1){
		fprintf(stdout,"[%s] client loses.\n", programname);
		write(connfd,&message,sizeof(char));
		exit(EXIT_SUCCESS);
	}
	return message;
}


/*
 * @brief checks what the outcome of the shot from the client is
 * @param field, field which the client wants to shoot at
 *
 * @description checks if the user hit a ship, sunk a ship, won the game, lost the game, or won
 */
int checkhit(unsigned char field){
	//Check if the field is not outside of the map
	if(field < 0 || field > 99){
		fprintf(stderr,"[%s] ERROR: invalid coordinate: %i\n", programname,field);
		serverMessage = generateMessage(3,0);
		write(connfd,&serverMessage,sizeof(char));
		exit(EXIT_FAILURE);
	}
	//Look if any ship is positioned on the field
	for (int row = 0; row <= 6; ++row) {
		for (int column = 0; column < ships[row].length; ++column) {
			if(field == ships[row].position[column]){
				ships[row].position[column] = -1;
				//Check if the ship sunk
				if (checkSunk(row,ships[row].length) == 0){
					//fprintf(stdout,"[%s] You sunk a ship.\n", programname);
					shipsunk++;
					//Check if all ships have sunken
					if(shipsunk == 6){
						//fprintf(stdout,"[%s] client wins in %i rounds.\n", programname,playedrounds);
						status=1;
						serverMessage = generateMessage(status,3);
						write(connfd,&serverMessage,sizeof(char));
						exit(EXIT_SUCCESS);
					}

					playedrounds++;
					status = checkRound(playedrounds);
					serverMessage = generateMessage(status,2);
					write(connfd,&serverMessage,sizeof(char));
					return 2;
				}
				//The client hit a ship
				//fprintf(stdout,"[%s] You hit a ship.\n", programname);
				playedrounds++;
				status = checkRound(playedrounds);
				serverMessage = generateMessage(status,1);
				write(connfd,&serverMessage,sizeof(char));
				return 1;
			}
		}
	}
	//The client missed all remaining ships
	//fprintf(stdout,"[%s] You missed.\n", programname);
	playedrounds++;
	status = checkRound(playedrounds);
	serverMessage = generateMessage(status,0);
	write(connfd,&serverMessage,sizeof(char));
	return 0;
}


int main(int argc, char *argv[])
{
	struct addrinfo hints;
	memset(&hints, 0, sizeof(hints));
	hints.ai_family = AF_INET;
	hints.ai_socktype = SOCK_STREAM;
	hints.ai_flags = AI_PASSIVE;

	unsigned char clientmessage;

	int res = getaddrinfo(NULL, port, &hints, &ai);
	if(res < 0){
		fprintf(stderr,"[%s] ERROR on getting address information\n", programname);
		exit(EXIT_FAILURE);
	}


	sockfd = socket(ai->ai_family, ai->ai_socktype, ai->ai_protocol);
	if(sockfd < 0){
		fprintf(stderr,"[%s] ERROR opening socket\n", programname);
		exit(EXIT_FAILURE);
	}


	int val = 1;
	res = setsockopt(sockfd, SOL_SOCKET, SO_REUSEADDR, &val, sizeof val);
	if(res < 0){
		fprintf(stderr,"[%s] ERROR on set socket options\n", programname);
		exit(EXIT_FAILURE);
	}


	res = bind(sockfd, ai->ai_addr, ai->ai_addrlen);
	if(res < 0){
		fprintf(stderr,"[%s] ERROR on binding\n", programname);
		exit(EXIT_FAILURE);
	}


	res = listen(sockfd, 1);
	if(res < 0){
		fprintf(stderr,"[%s] ERROR on listen\n", programname);
		exit(EXIT_FAILURE);
	}


	connfd = accept(sockfd, NULL, NULL);
	if(connfd < 0){
		fprintf(stderr,"[%s] ERROR on accept\n", programname);
		exit(EXIT_FAILURE);
	}



	programname = argv[0];
	int opt;
	int optsize=1;
	while ((opt = getopt (argc, argv, "p:")) != -1)
	{
		switch (opt)
		{
			case 'p':
				port = optarg;
				optsize+=2;
				break;
			case '?':
				exit(EXIT_FAILURE);
			default:
				assert(0);
				break;
		}
	}
	//Generates the ships with given coordinations
	for (int i = 0; i < 6; i++) {
		generateShip(argv[i+optsize],i);
	}
	//Check if the right number of ships with the right size were given
	for (int j = 0; j < 3; j++) {
		if(maxShips[j] != 0){
			fprintf(stderr,"[%s] ERROR: wrong number of ships\n", programname);
			exit(EXIT_FAILURE);
		}
	}
	do{
		read(connfd,&clientmessage, sizeof(char));
		if(checkParity(clientmessage) == 1){
			serverMessage = 8;
			fprintf(stderr,"[%s] ERROR: parity error\n", programname);
			write(connfd,&serverMessage,sizeof(char));
			exit(EXIT_FAILURE);
		}
		clientmessage = removeParity(clientmessage);
		checkhit(clientmessage);
	}while(playedrounds<=80);

	fprintf(stdout,"[%s] client loses.\n", programname);
	serverMessage = 4;
	write(connfd,&serverMessage,sizeof(char));
	exit(EXIT_SUCCESS);
}
