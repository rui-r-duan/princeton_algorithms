@echo off

:: ***************************************************
:: java-algs4
:: ------------------
:: Wrapper for java that includes algs4.jar
:: (including stdlib and selected exercise code)
:: ***************************************************

set INSTALLDIR=%USERPROFILE%\code\princeton_algorithms

java -cp ".;%INSTALLDIR%\algs4.jar" %*
