#!/bin/sh

TESTDIR=./collinear-testing

for file in ${TESTDIR}/input10.txt ${TESTDIR}/input20.txt ${TESTDIR}/input40.txt ${TESTDIR}/input80.txt ${TESTDIR}/input100.txt ${TESTDIR}/input200.txt ${TESTDIR}/input400.txt ${TESTDIR}/input1000.txt
do
    java-algs4 Timing $file brute
done
