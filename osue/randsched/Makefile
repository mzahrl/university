CC = gcc
DEFS = -D_DEFAULT_SOURCE -D_BSD_SOURCE -D_SVID_SOURCE -D_POSIX_C_SOURCE=200809L
CFLAGS = -Wall -g -std=c99 -pedantic $(DEFS)

SCHEDULEOBJECT = schedule.o
RVENTGASOBJECT = rventgas.o
RSHUTDOWNOBJECT = rshutdown.o

.PHONY: all clean
all: schedule rventgas rshutdown

schedule: $(SCHEDULEOBJECT)
	$(CC) $(CFLAGS) -o $@ $^

rventgas: $(RVENTGASOBJECT)
	$(CC) $(CFLAGS) -o $@ $^

rshutdown: $(RSHUTDOWNOBJECT)
	$(CC) $(CFLAGS) -o $@ $^

%.o: %.c
	$(CC) $(CFLAGS) -c -o $@ $<

schedule.o = schedule.c
rventgas.o = rventgas.c
rshutdown.o = rshutdown.c

clean:
	rm -rf *.o schedule rventgas rshutdown