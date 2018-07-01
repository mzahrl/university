#include <stdio.h>
#include <string.h>
#include <unistd.h>
#include <stdlib.h>
#include <assert.h>

/*
 * @brief: 	reads from stdin
 * @param: 	prmpt,displays a text when forced to enter a text
 * 			buff, stores the text
 * 			sz, the maximum allowed lengtht of the text
 */
static int getLine (char *prmpt, char *buff, size_t sz) {
    int ch, extra;

    // Get line with buffer overrun protection.
    if (prmpt != NULL) {

        printf ("%s", prmpt);
        fflush (stdout);

    }
    if (fgets (buff, sz, stdin) == NULL)
        exit(EXIT_SUCCESS);

    // If it was too long, there'll be no newline. In that case, we flush
    // to end of line so that excess doesn't affect the next call.
    if (buff[strlen(buff)-1] != '\n') {
        extra = 0;
        while (((ch = getchar()) != '\n') && (ch != EOF))
            extra = 1;
        return (extra == 1) ? 1 : 0;
    }

    // Otherwise remove newline and give string back to caller.
    buff[strlen(buff)-1] = '\0';
    return 0;
}

/*
 *  @brief 	Encrypts letter to words
 *	@param: AllowedChars, contains the chars which will be encrypted
 *			Wort, contains the words for the secret message
 *			filename, contains the filename which will be writen to
 *			o, is a option if the output should be writen in the file. 0: Write to stdout, 1: Write to file
 */
int hide(char AllowedChars[],const char *Wort[], char filename[], int o){
    int rc;
    int x;
    char buff[300];
	FILE *fp;
	//read from stdin
    rc = getLine (NULL, buff, sizeof(buff));
    if (rc == 1) {
        exit(EXIT_SUCCESS);
    }
	if(o == 1) {
		//open file filname
		fp = fopen(filename, "rb+");
		//if there was an error while openning the file, create the file filename
		if (fp == NULL) {
			fp = fopen(filename, "wb");
		} else {
			fp = fopen(filename, "w+");
		}
	}

    int vorherWort=0;
	int r=0;
    for (int i = 0; i < strlen(buff); ++i) {
		//checks if the current character of buff is a space
        if(buff[i] == ' '){
			//random number for adding . after some words
			r += rand() %3;
			if(o == 1) {
				vorherWort == 1 ? fprintf(fp, " %s", Wort[28]) : fprintf(fp, "%s", Wort[28]);
			}
			else{
				vorherWort == 1 ? printf(" %s", Wort[28]) : printf("%s", Wort[28]);
			}
        }
        else if (strchr(AllowedChars,buff[i]) !=NULL){
			r += rand() %3;
            if (buff[i] == '.'){
				if(o == 1) {
					vorherWort == 1 ? fprintf(fp," %s", Wort[27]) : fprintf(fp,"%s", Wort[27]);
				}
				else{
					vorherWort == 1 ? printf(" %s", Wort[27]) : printf("%s", Wort[27]);
				}
            }
            else {
                x = (buff[i] - '0') - 49;
				if(o == 1) {
					vorherWort == 1 ? fprintf(fp," %s", Wort[x]) : fprintf(fp,"%s", Wort[x]);
				}
				else{
					vorherWort == 1 ? printf(" %s", Wort[x]) : printf("%s", Wort[x]);
				}
            }
            vorherWort = 1;
        }
		//if enought words where already written, write a random . in the file or stdout
		if(r>=10){
			if(o == 1) {
				fprintf(fp,".");
			}
			else{
				printf(".");
			}
			r=0;
		}
    }
	if(o == 1) {
		fclose(fp);
	}
    return 0;
}

/*
 *  @details: Maches words from stdin with words which are used for the encoding and decodes the words to the original letters.
 *	@param: AllowedChars, contains the chars which will be encrypted
 *			Wort, contains the words for the secret message
 *			filename, contains the filename which will be writen to
 *			o, is a option if the output should be writen in the file. 0: Write to stdout, 1: Write to file
 */
int find(char AllowedChars[],const char *Wort[],char filename[], int o) {
	int rc;
	char buff[300];
	FILE *fp;

	rc = getLine(NULL, buff, sizeof(buff));
	if (rc == 1) {
		exit(EXIT_SUCCESS);
	}
	if (o == 1) {
		fp = fopen(filename, "rb+");
		if (fp == NULL) {
			fp = fopen(filename, "wb");
		} else {
			fp = fopen(filename, "w+");
		}
	}

    char * pch;
	//transforms the content of buff to tokens, while ignoring the symbols space and dot
    pch = strtok (buff," .");
    while (pch != NULL)
    {
        //printf ("%s\n",pch);
        for (int i = 0; i < 29; ++i) {
            if(strcmp(Wort[i],pch) == 0){
				if(o == 1) {
					i == 28 ? fprintf(fp, " ") : i == 27 ? fprintf(fp, ".") : fprintf(fp, "%c", AllowedChars[i]);
				}
				else{
					i == 28 ? printf(" ") : i == 27 ? printf(".") : printf("%c", AllowedChars[i]);
				}
				break;
            }
        }
        //Get next word
        pch = strtok (NULL, " .");
    }
	if(o == 1) {
		fclose(fp);
	}
	else{
		printf("\n");
	}
	return 0;
}



int main(int argc, char *argv[]) {
    char AllowedChars[] = "abcdefghijklmnopqrstuvwxyz.";
    const char *Wort[29];
    Wort[0] = "die";Wort[1] = "der";Wort[2] = "wasser";Wort[3] = "luft";Wort[4] = "erde";
    Wort[5] = "schmutz";Wort[6] = "ich";Wort[7] = "er";Wort[8] = "sie";Wort[9] = "ja";
    Wort[10] = "nein";Wort[11] = "sonne";Wort[12] = "mond";Wort[13] = "quark";Wort[14] = "komme";
    Wort[15] = "morgen";Wort[16] = "heute";Wort[17] = "trocken";Wort[18] = "muss";Wort[19] = "trinken";
    Wort[20] = "cowboy";Wort[21] = "helfen";Wort[22] = "retten";Wort[23] = "kann";Wort[24] = "wird";
    Wort[25] = "polizist";Wort[26] = "retten";Wort[27] = "zu";Wort[28] = "werden";

    int opt;
	int f = 0;
	int h = 0;
	int o = 0;
	char *filename = "";
    while ((opt = getopt (argc, argv, "fho:")) != -1)
    {
        switch (opt)
        {
            case 'f':
                f = 1;
                break;
            case 'h':
                h = 1;
                break;
            case 'o':
				filename = strdup(optarg);
                o = 1;
                break;
			default:
				assert(0);
				break;
        }
    }
	if(f == 1){
		if(find(AllowedChars,Wort,filename,o) == 1){
			fprintf( stderr,"Error in programm: %s",argv[0]);
			exit(EXIT_FAILURE);
		}
	}
	else if(h == 1){
		if(hide(AllowedChars,Wort,filename,o) == 1) {
			fprintf( stderr,"Error in programm: %s",argv[0]);
			exit(EXIT_FAILURE);
		}
	}
	printf("\n");
	exit(EXIT_SUCCESS);
}

