#!/bin/sh

java-algs4 Collinear input14-repetition.txt
java-algs4 Collinear input14.txt
find . -iname "input?.txt" -exec echo {} \; -exec java-algs4 Collinear {} fast \;
find . -iname "input??.txt" -exec echo {} \; -exec java-algs4 Collinear {} fast \;
find . -iname "input???.txt" -exec echo {} \; -exec java-algs4 Collinear {} fast \;
