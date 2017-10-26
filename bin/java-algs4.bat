@echo off

:: ***************************************************
:: java-algs4
:: ------------------
:: Wrapper for java that includes stdlib.jar and
:: algs4.jar (including stdlib and selected exercise
:: code)
:: ***************************************************

set INSTALLDIR=%USERPROFILE%\code\PrincetonAlgorithms

java -cp ".;%INSTALLDIR%\stdlib.jar;%INSTALLDIR%\algs4.jar" %*
