@echo off

:: ***************************************************
:: java-algs4
:: ------------------
:: Wrapper for java that includes stdlib.jar and
:: algs4.jar (including stdlib and selected exercise
:: code)
:: ***************************************************

java -cp ".;stdlib.jar;algs4.jar" %*
