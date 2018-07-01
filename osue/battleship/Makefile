CC = gcc
DEFS = -D_DEFAULT_SOURCE -D_BSD_SOURCE -D_SVID_SOURCE -D_POSIX_C_SOURCE=200809L
CFLAGS = -Wall -g -std=c99 -pedantic $(DEFS)

CLIENTOBJECT = client.o
SERVEROBJECT = server.o

.PHONY: all clean
all: client server

client: $(CLIENTOBJECT)
	$(CC) $(CFLAGS) -o $@ $^

server: $(SERVEROBJECT)
	$(CC) $(CFLAGS) -o $@ $^

%.o: %.c
	$(CC) $(CFLAGS) -c -o $@ $<

client.o: client.c common.h
server.o: server.c common.h

clean:
	rm -rf *.o client server