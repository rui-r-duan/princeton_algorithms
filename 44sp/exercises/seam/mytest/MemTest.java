/******************************************************************************
 *  Compilation:  javac MemTest.java
 *  Execution:    java -cp ".:stdlib.jar:algs4.jar:classmexer.jar:Jama-1.0.3.jar" \
                  -XX:-UseCompressedOops -javaagent:"classmexer.jar" MemTest
 *  Dependencies: SeamCarver.java PolynomialRegression.class SCUtility.class
 *
 *  Show the memory usage of a SeamCarver instance after removing 2 horizontal
 *  and 2 vertical seams from an n-by-n image.
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.Picture;
import edu.princeton.cs.algs4.StdOut;
import com.javamex.classmexer.MemoryUtil;

public class MemTest {
    public static void double2dSquareArray() {
        int[] sizes = {
            64, 128, 192, 256, 320, 384, 448, 512, 576, 640, 704, 768, 832, 896, 960, 1024
        };
        int M = sizes.length;

        double[] x = new double[M];
        double[] memory = new double[M];

        for (int i = 0; i < M; i++) {
            int N = sizes[i];
            Double[][] a = new Double[N][N];
            for (int j = 0; j < N; j++) {
                a[j] = new Double[N];
                for (int k = 0; k < N; k++) {
                    a[j][k] = new Double(Math.random());
                }
            }
            x[i] = a.length;
            memory[i] = MemoryUtil.deepMemoryUsageOf(a);
        }

        PolynomialRegression regression = new PolynomialRegression(x, memory, 2);
        StdOut.println("N-by-N double[][] array      = " + regression);
    }

    public static void seamCarverNbyN() {
        int[] n = {
            16, 32, 64, 128, 256, 512
        };
        final int M = n.length;

        double[] x = new double[M];
        double[] memory = new double[M];
        
        for (int i = 0; i < M; i++) {
            Picture inputImg = SCUtility.randomPicture(n[i], n[i]);
            final int removeColumns = 8;
            final int removeRows = 8;

            SeamCarver sc = new SeamCarver(inputImg);

            for (int j = 0; j < removeRows; j++) {
                int[] horizontalSeam = sc.findHorizontalSeam();
                sc.removeHorizontalSeam(horizontalSeam);
            }

            for (int j = 0; j < removeColumns; j++) {
                int[] verticalSeam = sc.findVerticalSeam();
                sc.removeVerticalSeam(verticalSeam);
            }

            x[i] = n[i];
            memory[i] = MemoryUtil.deepMemoryUsageOf(sc);
        }
        PolynomialRegression regression = new PolynomialRegression(x, memory, 2);
        StdOut.println("SeamCarver for n-by-n picture = " + regression);
    }

    public static void main(String[] args) {
        seamCarverNbyN();
        double2dSquareArray();
    }
}
