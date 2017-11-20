/******************************************************************************
 *  Compilation:  javac -cp .:classmexer.jar:jama.jar MemoryOfArrays.java
 *  Execution:    java  -cp .:classmexer.jar:jama.jar -XX:-UseCompressedOops -javaagent:classmexer.jar MemoryOfArrays
 *  Dependencies: StdOut.java StdRandom.java classmexer.jar jama.jar
 *                LinearRegression.java MultipleLinearRegresssion.java PolynomialRegression.java
 *
 *  % java  -cp .:classmexer.jar:jama.jar -XX:-UseCompressedOops -javaagent:classmexer.jar MemoryOfArrays
 *  boolean[] array of length N  = 1.00 N + 24.00  (R^2 = 1.000)
 *  char[]    array of length N  = 2.00 N + 24.00  (R^2 = 1.000)
 *  int[]     array of length N  = 4.00 N + 24.00  (R^2 = 1.000)
 *  double[]  array of length N  = 8.00 N + 24.00  (R^2 = 1.000)
 *  array of N null references   = 8.00 N + 24.00  (R^2 = 1.000)
 *  Date                         = 32
 *  Date[] array of length N     = 40.00 N + 24.00  (R^2 = 1.000)
 *  N-by-N int[][] array         = 4.00 N^2 + 32.00 N + 24.00   (R^2 = 1.000)
 *  M-by-N int[][] array         = 24.00 + 32.00 M + 0.00 N + 4.00 MN bytes (R^2 = 1.000)
 *  N-by-N double[][] array      = 8.00 N^2 + 32.00 N + 24.00   (R^2 = 1.000)
 *  M-by-N double[][] array      = 24.00 + 32.00 M + 0.00 N + 8.00 MN bytes (R^2 = 1.000)
 *
 *  % java -cp .:classmexer.jar:jama.jar -XX:+UseCompressedOops -javaagent:classmexer.jar MemoryOfArrays
 *  boolean[] array of length N  = 1.00 N + 16.00  (R^2 = 1.000)
 *  char[]    array of length N  = 2.00 N + 16.00  (R^2 = 1.000)
 *  int[]     array of length N  = 4.00 N + 16.00  (R^2 = 1.000)
 *  double[]  array of length N  = 8.00 N + 16.00  (R^2 = 1.000)
 *  array of N null references   = 4.00 N + 16.00  (R^2 = 1.000)
 *  Date                         = 24
 *  Date[] array of length N     = 28.00 N + 16.00  (R^2 = 1.000)
 *  N-by-N int[][] array         = 4.00 N^2 + 20.00 N + 16.00   (R^2 = 1.000)
 *  M-by-N int[][] array         = 16.00 + 20.00 M + 0.00 N + 4.00 MN bytes (R^2 = 1.000)
 *  N-by-N double[][] array      = 8.00 N^2 + 20.00 N + 16.00   (R^2 = 1.000)
 *  M-by-N double[][] array      = 16.00 + 20.00 M + 0.00 N + 8.00 MN bytes (R^2 = 1.000)
 *
 ******************************************************************************/

import com.javamex.classmexer.MemoryUtil;
import edu.princeton.cs.algs4.*;

public class MemoryOfArrays {

    // boolean array
    public static void booleanArray() {
        int[] sizes = {
            64, 128, 192, 256, 320, 384, 448, 512, 576, 640, 704, 768, 832, 896, 960, 1024
        };
        int M = sizes.length;

        double[] x = new double[M];
        double[] memory = new double[M];

        for (int i = 0; i < M; i++) {
            int N = sizes[i];
            boolean[] a = new boolean[N];
            x[i] = a.length;
            memory[i] = MemoryUtil.deepMemoryUsageOf(a);
        }

        LinearRegression regression = new LinearRegression(x, memory);
        StdOut.println("boolean[] array of length N  = " + regression);
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
            int N = sizes[i];
            char[] a = new char[N];
            x[i] = a.length;
            memory[i] = MemoryUtil.deepMemoryUsageOf(a);
        }

        LinearRegression regression = new LinearRegression(x, memory);
        StdOut.println("char[]    array of length N  = " + regression);
    }

    // integer array
    public static void intArray() {
        int[] sizes = {
            64, 128, 192, 256, 320, 384, 448, 512, 576, 640, 704, 768, 832, 896, 960, 1024
        };
        int M = sizes.length;

        double[] x = new double[M];
        double[] memory = new double[M];

        for (int i = 0; i < M; i++) {
            int N = sizes[i];
            int[] a = new int[N];
            x[i] = a.length;
            memory[i] = MemoryUtil.deepMemoryUsageOf(a);
        }

        LinearRegression regression = new LinearRegression(x, memory);
        StdOut.println("int[]     array of length N  = " + regression);
    }

    // double array
    public static void doubleArray() {
        int[] sizes = {
            64, 128, 192, 256, 320, 384, 448, 512, 576, 640, 704, 768, 832, 896, 960, 1024
        };
        int M = sizes.length;

        double[] x = new double[M];
        double[] memory = new double[M];

        for (int i = 0; i < M; i++) {
            int N = sizes[i];
            double[] a = new double[N];
            x[i] = a.length;
            memory[i] = MemoryUtil.deepMemoryUsageOf(a);
        }

        LinearRegression regression = new LinearRegression(x, memory);
        StdOut.println("double[]  array of length N  = " + regression);
    }

    // Integer array of null
    public static void nullArray() {
        int[] sizes = {
            64, 128, 192, 256, 320, 384, 448, 512, 576, 640, 704, 768, 832, 896, 960, 1024
        };
        int M = sizes.length;

        double[] x = new double[M];
        double[] memory = new double[M];

        for (int i = 0; i < M; i++) {
            int N = sizes[i];
            Date[] a = new Date[N];
            x[i] = a.length;
            memory[i] = MemoryUtil.deepMemoryUsageOf(a);
        }

        LinearRegression regression = new LinearRegression(x, memory);
        StdOut.println("array of N null references   = " + regression);
    }

    // Date array
    public static void dateArray() {
        int[] sizes = {
            64, 128, 192, 256, 320, 384, 448, 512, 576, 640, 704, 768, 832, 896, 960, 1024
        };
        int M = sizes.length;

        double[] x = new double[M];
        double[] memory = new double[M];

        for (int i = 0; i < M; i++) {
            int N = sizes[i];
            Date[] a = new Date[N];
            for (int j = 0; j < N; j++) {
                int month = 1 + StdRandom.uniform(12);
                int day   = 1 + StdRandom.uniform(28);
                int year  = 1900 + StdRandom.uniform(100);
                a[j] = new Date(month, day, year);
            }
            x[i] = a.length;
            memory[i] = MemoryUtil.deepMemoryUsageOf(a);
        }

        Date date = new Date(12, 31, 1999);
        StdOut.println("Date                         = " + MemoryUtil.deepMemoryUsageOf(date));
        LinearRegression regression = new LinearRegression(x, memory);
        StdOut.println("Date[] array of length N     = " + regression);
    }


    // int[][] array
    public static void int2dArray() {
        int[] sizes1 = {
            64, 128, 192, 256, 320, 384, 448, 512, 576,
            640, 704, 768, 832, 896, 960, 1024
        };

        int[] sizes2 = {
            64, 64, 64, 384, 384, 384, 96, 96, 96,
            16, 8, 128, 32, 16, 24, 24
        };

        double[][] x = new double[sizes1.length][4];
        double[] memory = new double[sizes1.length];

        for (int i = 0; i < sizes1.length; i++) {
            int M = sizes1[i];
            int N = sizes2[i];
            int[][] a = new int[M][N];
            x[i][0] = 1.0;
            x[i][1] = M;
            x[i][2] = N;
            x[i][3] = M*N;
            memory[i] = MemoryUtil.deepMemoryUsageOf(a);
        }

        MultipleLinearRegression regression = new MultipleLinearRegression(x, memory);
        StdOut.print("M-by-N int[][] array         = ");
        StdOut.printf("%.2f + %.2f M + %.2f N + %.2f MN bytes (R^2 = %.3f)\n",
                      regression.beta(0), regression.beta(1), regression.beta(2), regression.beta(3), regression.R2());
    }

    // double[][] array
    public static void double2dArray() {
        int[] sizes1 = {
            64, 128, 192, 256, 320, 384, 448, 512, 576, 640, 704, 768, 832, 896, 960, 1024
        };

        int[] sizes2 = {
            64, 64, 64, 384, 384, 384, 96, 96, 96, 16, 8, 128, 32, 16, 24, 24
        };

        double[][] x = new double[sizes1.length][4];
        double[] memory = new double[sizes1.length];

        for (int i = 0; i < sizes1.length; i++) {
            int M = sizes1[i];
            int N = sizes2[i];
            double[][] a = new double[M][N];
            x[i][0] = 1.0;
            x[i][1] = M;
            x[i][2] = N;
            x[i][3] = M*N;
            memory[i] = MemoryUtil.deepMemoryUsageOf(a);
        }

        MultipleLinearRegression regression = new MultipleLinearRegression(x, memory);
        StdOut.print("M-by-N double[][] array      = ");
        StdOut.printf("%.2f + %.2f M + %.2f N + %.2f MN bytes (R^2 = %.3f)\n",
                      regression.beta(0), regression.beta(1), regression.beta(2), regression.beta(3), regression.R2());
    }

    // integer N-by-N array
    public static void int2dSquareArray() {
        int[] sizes = {
            64, 128, 192, 256, 320, 384, 448, 512, 576, 640, 704, 768, 832, 896, 960, 1024
        };
        int M = sizes.length;

        double[] x = new double[M];
        double[] memory = new double[M];

        for (int i = 0; i < M; i++) {
            int N = sizes[i];
            int[][] a = new int[N][N];
            x[i] = a.length;
            memory[i] = MemoryUtil.deepMemoryUsageOf(a);
        }

        PolynomialRegression regression = new PolynomialRegression(x, memory, 2);
        StdOut.println("N-by-N int[][] array         = " + regression);
    }

    // double N-by-N array
    public static void double2dSquareArray() {
        int[] sizes = {
            64, 128, 192, 256, 320, 384, 448, 512, 576, 640, 704, 768, 832, 896, 960, 1024
        };
        int M = sizes.length;

        double[] x = new double[M];
        double[] memory = new double[M];

        for (int i = 0; i < M; i++) {
            int N = sizes[i];
            double[][] a = new double[N][N];
            x[i] = a.length;
            memory[i] = MemoryUtil.deepMemoryUsageOf(a);
        }

        PolynomialRegression regression = new PolynomialRegression(x, memory, 2);
        StdOut.println("N-by-N double[][] array      = " + regression);
    }

    public static void main(String[] args) {
        booleanArray();
        charArray();
        intArray();
        doubleArray();
        nullArray();
        dateArray();
        int2dSquareArray();
        int2dArray();
        double2dSquareArray();
        double2dArray();
    }
}
