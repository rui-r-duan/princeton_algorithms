#!/bin/sh

# find . -iname "input?.txt" -exec echo {} \; -exec java-algs4 Collinear {} fast \;
# find . -iname "input??.txt" -exec echo {} \; -exec java-algs4 Collinear {} fast \;
find . -iname "input???.txt" -exec echo {} \; -exec java-algs4 Collinear {} fast \;
