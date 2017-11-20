/******************************************************************************
 *  Compilation:  javac -cp .:classmexer.jar:jama.jar MemoryOfStrings.java
 *  Execution:    java  -cp .:classmexer.jar:jama.jar -XX:-UseCompressedOops -javaagent:classmexer.jar MemoryOfStrings
 *  Dependencies: StdOut.java StdRandom.java classmexer.jar jama.jar LinearRegression.java PolynomialRegression.java
 *
 *  % java6 -cp .:classmexer.jar:jama.jar -XX:-UseCompressedOops -javaagent:classmexer.jar MemoryOfStrings
 *  String of length N        = 2.00 N + 64.00  (R^2 = 1.000)
 *  char[] array of length N  = 2.00 N + 24.00  (R^2 = 1.000)
 *  String[] of N suffixes    = 50.00 N + 48.00  (R^2 = 1.000)
 *  memory of genome = "CGCCTGGCGTCTGTAC"    = 96
 *  memory of codon = genome.substring(6, 9) = 96
 *  total memory of genome and codon         = 136
 *
 *  % java6 -cp .:classmexer.jar:jama.jar -XX:+UseCompressedOops -javaagent:classmexer.jar MemoryOfStrings
 *  String of length N        = 2.00 N + 48.00  (R^2 = 1.000)
 *  char[] array of length N  = 2.00 N + 16.00  (R^2 = 1.000)
 *  String[] of N suffixes    = 38.00 N + 32.00   (R^2 = 1.000)
 *  memory of genome = "CGCCTGGCGTCTGTAC"    = 80
 *  memory of codon = genome.substring(6, 9) = 80
 *  total memory of genome and codon         = 112
 *
 *  % java7 -cp .:classmexer.jar:jama.jar -XX:-UseCompressedOops -javaagent:classmexer.jar MemoryOfStrings
 *  String of length N        = 2.00 N + 56.00  (R^2 = 1.000)
 *  char[] array of length N  = 2.00 N + 24.00  (R^2 = 1.000)
 *  String[] of N suffixes    = 1.00 N^2 + 68.00 N + 24.00 bytes  (R^2 = 1.000)
 *  memory of genome = "CGCCTGGCGTCTGTAC"    = 88
 *  memory of codon = genome.substring(6, 9) = 64
 *  total memory of genome and codon         = 152
 *
 *  % java7 -cp .:classmexer.jar:jama.jar -XX:+UseCompressedOops -javaagent:classmexer.jar MemoryOfStrings
 *  String of length N        = 2.00 N + 40.00  (R^2 = 1.000)
 *  char[] array of length N  = 2.00 N + 16.00  (R^2 = 1.000)
 *  String[] of N suffixes    = 1.00 N^2 + 48.00 N + 16.00   (R^2 = 1.000)
 *  memory of genome = "CGCCTGGCGTCTGTAC"    = 72
 *  memory of codon = genome.substring(6, 9) = 48
 *  total memory of genome and codon         = 120
 *
 ******************************************************************************/

import com.javamex.classmexer.MemoryUtil;
import java.util.LinkedList;
import edu.princeton.cs.algs4.*;

public class MemoryOfStrings {

    // random string of length N consisting of only uppercase letters
    public static String random(int N) {
        if (N == 0) return "";
        if (N == 1) {
            char c = (char) ('A' + StdRandom.uniform(26));
            return c + "";
        }
        return random(N/2) + random(N - N/2);
    }


    // string of given length
    public static void string() {
        int[] sizes = {
            64, 128, 192, 256, 320, 384, 448, 512, 576, 640, 704, 768, 832, 896, 960, 1024
        };
        int M = sizes.length;

        double[] x = new double[M];
        double[] memory = new double[M];

        for (int i = 0; i < M; i++) {
            String s = random(sizes[i]);
            x[i] = s.length();
            memory[i] = MemoryUtil.deepMemoryUsageOf(s);
        }

        LinearRegression regression = new LinearRegression(x, memory);
        StdOut.println("String of length N        = " + regression);
    }

    // character array
    public static void charArray() {
        int[] sizes = {
            64, 128, 192, 256, 320, 384, 448, 512, 576, 640, 704, 768, 832, 896, 960, 1024
        };
        int M = sizes.length;

        double[] x = new double[M];
        double[] memory = new double[M];

        for (int i = 0; i < M; i++) {
            char[] s = random(sizes[i]).toCharArray();
            x[i] = s.length;
            memory[i] = MemoryUtil.deepMemoryUsageOf(s);
        }

        LinearRegression regression = new LinearRegression(x, memory);
        StdOut.println("char[] array of length N  = " + regression);
    }

    // array of suffixes
    public static void suffixes() {
        int[] sizes = {
            64, 128, 192, 256, 320, 384, 448, 512, 576, 640, 704, 768, 832, 896, 960, 1024
        };
        int M = sizes.length;

        double[] x = new double[M];
        double[] memory = new double[M];

        for (int i = 0; i < M; i++) {
            String s = random(sizes[i]);
            String[] suffixes = new String[s.length()];
            for (int j = 0; j < s.length(); j++)
                suffixes[j] = s.substring(j);
            x[i] = s.length();
            memory[i] = MemoryUtil.deepMemoryUsageOf(suffixes);
        }

        PolynomialRegression regression = new PolynomialRegression(x, memory, 2);
        StdOut.println("String[] of N suffixes    = " + regression);
    }

    // substring
    public static void substring() {
        String genome = "CGCCTGGCGTCTGTAC";
        String codon = genome.substring(6, 9);
        StdOut.println("memory of genome = \"CGCCTGGCGTCTGTAC\"    = " + MemoryUtil.deepMemoryUsageOf(genome));
        StdOut.println("memory of codon = genome.substring(6, 9) = "   + MemoryUtil.deepMemoryUsageOf(codon));
        LinkedList<String> list = new LinkedList<String>();
        list.add(genome);
        list.add(codon);
        StdOut.println("total memory of genome and codon         = " +   MemoryUtil.deepMemoryUsageOfAll(list));
        
    }

    public static void main(String[] args) {
        string();
        charArray();
        suffixes();
        substring();
    }
}
