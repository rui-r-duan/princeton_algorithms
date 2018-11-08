.PHONY: deploy-src compile run-test

INSTALLDIR = /Users/ryan/code/princeton_algorithms
SEAM_DIR = /Users/ryan/code/princeton_algorithms/44sp/exercises/seam

run-test: compile
	cd $(SEAM_DIR)/mytest && java -cp ".:$(INSTALLDIR)/stdlib.jar:$(INSTALLDIR)/algs4.jar:$(INSTALLDIR)/classmexer.jar:$(INSTALLDIR)/Jama-1.0.3.jar" -XX:-UseCompressedOops -javaagent:"$(INSTALLDIR)/classmexer.jar" MemTest

compile: deploy-src
	cd $(SEAM_DIR)/mytest && javac -cp ".:$(INSTALLDIR)/algs4.jar:$(INSTALLDIR)/classmexer.jar:$(INSTALLDIR)/Jama-1.0.3.jar" -g -encoding UTF-8 -Xlint:all MemTest.java

deploy-src:
	cp *.java mytest/ && cp test/PolynomialRegression.class mytest/ && cp test/SCUtility.class mytest/

