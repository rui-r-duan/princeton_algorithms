.PHONY: deploy-src compile run-test

INSTALLDIR = $(HOME)/code/princeton_algorithms
SEAM_DIR = $(HOME)/code/princeton_algorithms/44sp/exercises/seam

run-test: compile
	cd $(SEAM_DIR)/mytest && java -cp ".:$(INSTALLDIR)/stdlib.jar:$(INSTALLDIR)/algs4.jar:$(INSTALLDIR)/classmexer.jar:$(INSTALLDIR)/Jama-1.0.3.jar" -XX:-UseCompressedOops -javaagent:"$(INSTALLDIR)/classmexer.jar" MemTest

compile: deploy-src
	cd $(SEAM_DIR)/mytest && javac -cp ".:$(INSTALLDIR)/algs4.jar:$(INSTALLDIR)/classmexer.jar:$(INSTALLDIR)/Jama-1.0.3.jar" -g -encoding UTF-8 -Xlint:all MemTest.java

deploy-src:
	cp *.java mytest/ && cp $(INSTALLDIR)/14analysis/PolynomialRegression.java mytest/ && cp seam-testing/SCUtility.java mytest/

