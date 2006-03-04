#!/bin/sh
gcc -c -O -fpic lib300fi.c
ld --shared -lcrypt -o lib300fi.so lib300fi.o
