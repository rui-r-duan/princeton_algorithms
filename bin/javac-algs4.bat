@echo off

:: ***************************************************
:: javac-algs4
:: ------------------
:: Wrapper for javac that includes stdlib.jar, and
:: algs4.jar (including stdlib and selected exercise
:: code)
:: ***************************************************

"%JAVA_HOME%"\bin\javac -cp ".;stdlib.jar;algs4.jar" -g -encoding UTF-8 -Xlint:all %*
