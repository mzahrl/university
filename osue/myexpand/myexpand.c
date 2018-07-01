#include <stdio.h>
#include <string.h>
#include <unistd.h>
#include <stdlib.h>
#include <assert.h>


/*
 *  @brief 	Examinates each character of the input
 *  @detail If a character is a tab spaces are put
 *  		between the next and last character
 *	@param: buffer: contains the input string
 *			tabstop: contains the number of spaces to
 *			output, if a character is a tab
 */
static void checkTab(char* buffer, int tabstop){
	for (int i = 0; i < strlen(buffer); i++) {
		//Check if the current character is a tab
		if (buffer[i] == '\t') {
			//calculate position of the next character
			int p = tabstop * ((i / tabstop) + 1);

			int bufferlen = strlen(buffer);
			int newposition = (tabstop - (i % tabstop)) -1;

			/*Move all characters that come after the tab 'tabstop' times back
			to expand the buffer for the new spaces
			*/
			while (bufferlen > i) {
				buffer[bufferlen + newposition] = buffer[bufferlen];
				bufferlen--;
			}
			//But spaces where the tab was
			for (int x = i; x < p; x++) {
				buffer[x] = ' ';
			}
		}
	}
}


/*
 *  @brief 	Examinates each character of the input
 *	@param: output: contains the output string
 *			linecounter: containes the number of lines to be writen
 *
 */
static void printOutput(char* output[], int linecounter) {
	for (int i = 0; i < linecounter; i++) {
		fprintf(stdout, "%s", output[i]);
	}
}

/*
 * @brief: 	reads from stdin or a file
 * @param: 	input: the source where to read from
 * 			tabstop: contains the number of spaces to
 *			output, if a character is a tab
 */
static void getInput (FILE *input, int tabstop) {
	char buffer[300];
	int linecounter=0;
	char *output[300];

	//Read from the given input into buffer
	while (fgets(buffer, sizeof(buffer), input) != NULL) {
		//Check the input for tabs
		checkTab(buffer, tabstop);
		//Increase the linecounter if the current line ended
		output[linecounter++] = strdup(buffer);
	}
	//Print the generated output text to stdout
	printOutput(output, linecounter);
}


int main(int argc, char *argv[]) {

	int opt;
	int tabstop = 8;
	FILE *fp;
	char *filename = "";
	while ((opt = getopt (argc, argv, "t:")) != -1)
	{
		switch (opt)
		{
			case 't':
				tabstop = strtol(optarg, NULL, 10);
				break;
			default:
				assert(0);
				break;
		}
	}
	//Decide wheter to read from a file or stdin
	if((argc - optind) == 1 ){
		filename = strdup(argv[argc-1]);
		//Try to open file
		fp = fopen(filename, "r");
		if(fp == NULL) {
			fprintf( stderr,"Error File %s not found", filename);
			exit(EXIT_FAILURE);
		}
		//Read from file
		getInput(fp, tabstop);
		//Close file
		fclose(fp);
	}
	else {
		//Read from stdin
		getInput(stdin, tabstop);
	}
	exit(EXIT_SUCCESS);
}

