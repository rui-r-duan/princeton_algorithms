@echo off

:: ***************************************************
:: javac-algs4
:: ------------------
:: Wrapper for java that includes algs4.jar
:: (including stdlib and selected exercise code)
:: ***************************************************

set INSTALLDIR=%USERPROFILE%\code\princeton_algorithms

"%JAVA_HOME%"\bin\javac -cp ".;%INSTALLDIR%\algs4.jar" -g -encoding UTF-8 -Xlint:all %*
