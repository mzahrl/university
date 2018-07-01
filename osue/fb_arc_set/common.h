//max edges possible in set arc
#define MAX_EDGE 8

#define SEM_1       "/01627763free_sem"
#define SEM_2       "/01627763used_sem"
#define SEM_3       "/01627763mutex_sem"

#define SHM_NAME    "/01627763myshm"

#define MAX_DATA 100
#define PERMISSION (0600)

//max value of edges in arc set
#define BUF_LEN 8

struct myshm{
	char data[MAX_DATA][MAX_DATA];
	bool state;
	int write_pos;
};
