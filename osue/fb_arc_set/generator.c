#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <stdbool.h>
#include <string.h>
#include <assert.h>
#include <time.h>
#include "common.h"
#include <sys/mman.h>
#include <fcntl.h>
#include <semaphore.h>

char *programname = "generator";

int quit = 0;

struct Graph* graph;

int numberVerticles = 2; //Number of verticles default 2 because min 1 edge

bool restart = false;

struct myshm *myshm;

sem_t *free_sem, // tracks free space, initialized to BUF_LEN
		*used_sem, // tracks used space, initialized to 0
		*mutex_sem; // used to ensure synchronisitation between generators

// Struct for adjacency list node
struct AdjListNode
{
	int dest;
	struct AdjListNode* next;
};

// Struct for adjacency list
struct AdjList {
	struct AdjListNode *head;
};


// Struct for graph, is an array of adjacency lists
struct Graph {
	int numberVerticles;
	struct AdjList* array;
};

/**
 * @brief Creates new adjecency node
 * @return Resulting node
 */
struct AdjListNode* newAdjListNode(int dest) {
	struct AdjListNode* newNode = (struct AdjListNode*) malloc(sizeof(struct AdjListNode));
	newNode->dest = dest;
	newNode->next = NULL;
	return newNode;
}

/**
 * @brief Creates graph with n verticles
 * @return resulting graph
 */
struct Graph* createGraph(int numberVerticles) {
	struct Graph* graph = (struct Graph*) malloc(sizeof(struct Graph));
	graph->numberVerticles = numberVerticles;

	// Create array of size numberVerticles for adjacency list
	graph->array = (struct AdjList*) malloc(numberVerticles * sizeof(struct AdjList));


	int i;
	for (i = 0; i < numberVerticles; ++i) { // Initialize each list empty
		graph->array[i].head = NULL;
	}
	return graph;
}



/**
 * @brief Print usage of program
 */
static void usage(void) {
	fprintf(stderr,"usage: %s EDGE1 EDGE2...\n", programname);
	exit(EXIT_FAILURE);
}

/**
 * @brief Print message and exit program
 */
static void error_exit(char *message) {
	fprintf(stderr,"[%s] %s\n", programname, message);
	exit(EXIT_FAILURE);
}

/**
 * @brief Creates resources
 * @return Creates semaphores and shared memory
 */
static void setup_resoucres() {
	/* Shared memory */
	int shmfd = shm_open(SHM_NAME, O_RDWR | O_CREAT, PERMISSION);

	if(shmfd < 0) {
		error_exit("Error with shm_open");
	}

	myshm = mmap(NULL, sizeof(*myshm), PROT_READ | PROT_WRITE, MAP_SHARED, shmfd, 0);

	if (myshm == MAP_FAILED) {
		error_exit("Error with mmap");
	}

	if (close(shmfd) == -1) {
		error_exit("Error with closing shared memory pointer");
	}

	/* Open the semaphores */
	free_sem = sem_open(SEM_1, MAX_DATA);
	used_sem = sem_open(SEM_2, 0);
	mutex_sem = sem_open(SEM_3, 1);

	if (free_sem == SEM_FAILED || used_sem == SEM_FAILED || mutex_sem == SEM_FAILED) {
		error_exit("Error with sem_open");
	}
}

/**
 * @brief Free resources
 * @return Frees creates semaphores and shared memory
 */
static void free_resources() {
	if (munmap(myshm, sizeof(*myshm)) == -1) {
		error_exit("Error with munmap");
	}
	if (sem_close(free_sem) == -1 || sem_close(used_sem) == -1 || sem_close(mutex_sem) == -1) {
		error_exit("Error with sem_close");
	}

	//free graph
	free(graph);
}

/**
 * @brief Adds edge to graph
 * @details Creates new adjacency list node element and adds it
 * 			to the beginning of the source element of the graph
 */
void addEdge(struct Graph* graph, int src, int dest) {
	struct AdjListNode* newNode = newAdjListNode(dest);
	newNode->next = graph->array[src].head;
	graph->array[src].head = newNode;
}

/**
 * @brief Swaps two array positions with eathother
 */
void swapArrayEntries (int *a, int *b) {
	int temp = *a;
	*a = *b;
	*b = temp;
}

/**
 * @brief Random permutation of array
 * @details Starts form the beginning of the array and
 * 			swaps entries one by one, always swaps with the first
 * 			or seconde entry of the array
 */
void randomizeArray(int arr[]) {
	srand(time(NULL));

	for (int i = 0; i <= numberVerticles; i++) {
		int j = rand() % (i+1);
		swapArrayEntries(&arr[i], &arr[j]);
	}
}

/**
 * @brief Check if value is in array
 * @return True if the value is present, false if not
 * @param val, value to search for
 * 			arr, array
 * 			size, number of values writen in the array
 */
bool isvalueinarray(int val, int *arr, int size){
	int i;
	for (i=0; i < size; i++) {
		if (arr[i] == val)
			return true;
	}
	return false;
}


/**
 * @brief Write to circular buffer
 * @details Checks if writing space is free on the buffer
 * 			and writes or waits for space to write to
 * @param val, value to write into the circular buffer
 */
void circ_buf_write(char *val) {
	sem_wait(free_sem); // writing requires free space
	sem_wait(mutex_sem); // multiuser synchronisation
	strcpy(myshm->data[myshm->write_pos],val);
	myshm->write_pos = (myshm->write_pos + 1) % MAX_DATA;
	sem_post(mutex_sem);
	sem_post(used_sem); // space is used by written data
}

/**
 * @brief Generates arc set for the graph
 * @details Saves already checkes verticles
 * 			in an array, while loking at a verticles
 * 			neighbors if any are already in that array
 * 			add that edge to the solution
 * @param verticlesArray, array containing randomly ordered verticles
 * 			of the graph
 */
void arcSet(int *verticlesArray) {
	//cyle through the new sorted array but not the first verticle
	//since no edge will be taken from it
	int visitedVerticle[numberVerticles+1];

	int solutionpos=0;
	restart = false;
	char edge1[numberVerticles+1];
	char edge2[numberVerticles+1];
	char str[80];
	char message[80] = "";
	for(int i = 1; i <= numberVerticles; i++) {
		if(restart) break; //check if MAX_EDGE execeded
		visitedVerticle[i-1] = verticlesArray[i-1]; //add prior verticle to visited array

		//check next verticle of the array
		struct AdjListNode* currentNode = graph->array[verticlesArray[i]].head;

		while (currentNode) {
			strcpy(str, ""); //reset string str
			//u > v occurs add edge to feedback arc set
			if(isvalueinarray(currentNode->dest,visitedVerticle,i)) {
				sprintf(edge1,"%d",verticlesArray[i]); //generate edges of feedback arc set
				sprintf(edge2,"%d",currentNode->dest);
				strcat(str, edge1);
				strcat(str, "-");
				strcat(str, edge2);
				strcat(str, " ");
				strcat(message, str);
				solutionpos++;
				if(solutionpos > MAX_EDGE) restart = true;
			}
			currentNode = currentNode->next;
		}
	}
	char finalMessage[100] = "";
	char numberArcSet[10];
	sprintf(numberArcSet,"%d",solutionpos); //Conver number of edges in arc set to string
	strcat(finalMessage, numberArcSet); //Generate message to write into circular buffer
	strcat(finalMessage, message);
	finalMessage[strlen(finalMessage)-1] = 0; //Remove last character (space)
	if(myshm->state == 1) { //Before writing check if the supervisor quit
		quit = 1;
	}
	else {
		if(solutionpos>0) { //Check if acyclic
			//printf("Writing to circular buffer: %s\n", finalMessage);
			circ_buf_write(finalMessage); //Write feedback arc set to circular buffer
		}
		else {
			//printf("Writing to circular buffer: 0\n");
			circ_buf_write("0");
		}
	}
}

int main(int argc, char *argv[]) {
	programname = argv[0];
	atexit(free_resources); //At exit call free_resources function
	int opt;
	int optsize = 1;
	while ((opt = getopt(argc, argv, "")) != -1) {
		switch (opt) {
			case '?':
				usage();
			default:
				assert(0);
				break;
		}
	}
	if(argc < 2){ //no edge given
		usage();
	}

	setup_resoucres();

	// allocate memory and copy strings of argv since we have to go over it twice
	char** new_argv = malloc((argc+1) * sizeof *new_argv);
	for(int i = 0; i < argc; ++i) {
		size_t length = strlen(argv[i])+1;
		new_argv[i] = malloc(length);
		memcpy(new_argv[i], argv[i], length);
	}
	new_argv[argc] = NULL;



	char *verticles;
	char *ptr;


	/* Determine how many verticles the graph has */
	int edge1 = -1;
	int edge2 = -1;
	for (int i = 0; i < argc-1; i++) {
		verticles = strtok(argv[i + optsize], "-");
		edge1 = strtol(verticles, &ptr, 10);
		if(edge1 > numberVerticles) numberVerticles = edge1;
		verticles = strtok(NULL, "-");
		edge2 = strtol(verticles, &ptr, 10);
		if(edge2 > numberVerticles) numberVerticles = edge2;
	}

	graph = createGraph(numberVerticles+1);
	for (int i = 0; i < argc-1; i++) {
		verticles = strtok(new_argv[i + optsize], "-");
		edge1 = strtol(verticles, &ptr, 10);
		verticles = strtok(NULL, "-");
		edge2 = strtol(verticles, &ptr, 10);
		if(edge1 == edge2){ //if verticle has edge to itself
			error_exit("Error verticles can not have edges to themselves");
		}
		addEdge(graph, edge1, edge2);
	}

	for(int i = 0; i < argc; ++i) {
		free(new_argv[i]);
	}
	free(new_argv);


	int verticlesArray[numberVerticles+1]; //create array with verticles
	for(int i = 0; i <= numberVerticles; i++){ //seed array with numbers of verticle to perform arc set
		verticlesArray[i] = i;
	}

	do { //generate arc sets until the supervisor sets flag
		randomizeArray(verticlesArray); //Shuffle array with verticles

		arcSet(verticlesArray); //generate arc set solution with new verticle order
	} while(!quit);

	exit(EXIT_SUCCESS);
}