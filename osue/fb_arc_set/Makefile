CC = gcc
DEFS = -D_DEFAULT_SOURCE -D_BSD_SOURCE -D_SVID_SOURCE -D_POSIX_C_SOURCE=200809L
CFLAGS = -Wall -g -std=c99 -pedantic $(DEFS)

GOBJECT = generator.o
SOBJECT = supervisor.o

.PHONY: all clean
all: generator supervisor

generator: $(GOBJECT)
	$(CC) $(CFLAGS) -o $@ $^ -lrt -pthread

supervisor: $(SOBJECT)
	$(CC) $(CFLAGS) -o $@ $^ -lrt -pthread

%.o: %.c
	$(CC) $(CFLAGS) -c -o $@ $<

generator.o: generator.c
supervisor.o: supervisor.c

clean:
	rm -rf *.o generator supervisor