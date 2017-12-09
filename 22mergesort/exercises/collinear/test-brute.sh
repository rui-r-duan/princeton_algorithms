#!/bin/sh

# find . \( -iname "input?.txt" -o -iname "input??.txt" \) -exec echo {} \; -exec java-algs4 Collinear {} brute \;
java-algs4 Collinear ./my-testing/input14-repetition.txt brute
java-algs4 Collinear ./my-testing/input14.txt brute
find . -iname "input?.txt" -exec echo {} \; -exec java-algs4 Collinear {} brute \;
find . -iname "input??.txt" -exec echo {} \; -exec java-algs4 Collinear {} brute \;
find . -iname "input???.txt" -exec echo {} \; -exec java-algs4 Collinear {} brute \;
