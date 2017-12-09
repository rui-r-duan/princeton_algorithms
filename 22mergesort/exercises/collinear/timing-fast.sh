#!/bin/sh

TESTDIR=./collinear-testing

for file in ${TESTDIR}/input10.txt ${TESTDIR}/input20.txt ${TESTDIR}/input40.txt ${TESTDIR}/input80.txt ${TESTDIR}/input100.txt ${TESTDIR}/input200.txt ${TESTDIR}/input400.txt ./input800.txt ./input1600.txt ./input3200.txt ./input6400.txt ${TESTDIR}/input1000.txt ${TESTDIR}/input2000.txt ${TESTDIR}/input4000.txt ${TESTDIR}/input8000.txt ${TESTDIR}/input10000.txt
do
    java-algs4 Timing $file fast
done
