: ******************************************************************************
: zip-src zipfilename file1 file2 ...
: ------------------
: archive the files in the arguments into a specified zip file
:
: example: zip-src package-to-submit.zip *.java
: ******************************************************************************
"%JAVA_HOME%"\bin\jar.exe -cvMf %*
