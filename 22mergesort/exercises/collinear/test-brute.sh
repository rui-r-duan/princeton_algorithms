#!/bin/sh

# find . \( -iname "input?.txt" -o -iname "input??.txt" \) -exec echo {} \; -exec java-algs4 Collinear {} brute \;
find . -iname "input?.txt" -exec echo {} \; -exec java-algs4 Collinear {} brute \;
find . -iname "input??.txt" -exec echo {} \; -exec java-algs4 Collinear {} brute \;
