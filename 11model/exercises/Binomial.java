/******************************************************************************
 *  Compilation:  javac Binomial.java
 *  Execution:    java Binomial N k p
 *  Dependencies: StdOut.java
 *
 *  Reads in N, k, and p as command-line arguments and prints out
 *  (N choose k) p^k (1-p)^N-k.
 *
 *  % java Binomial 5 2 .25
 *  0.263671875
 *  0.263671875
 *
 *  % java Binomial 5 3 .25
 *  0.087890625
 *  0.087890625
 *
 *  % java Binomial 5 0 .25
 *  0.2373046875
 *  0.2373046875
 *
 *  % java Binomial 5 5 .25
 *  9.765625E-4
 *  9.765625E-4
 *
 ******************************************************************************/

public class Binomial { 

    static int recursiveCallCount = 0;
    
    // slow
    public static double binomial1(int N, int k, double p) {
        recursiveCallCount++;
        // System.out.println(N + " " + k);
        if (N == 0 && k == 0) return 1.0;
        if (N < 0 || k < 0) return 0.0;
        // We can add the following base case:
        if (N < k) return 0.0;
        return (1.0 - p) *binomial1(N-1, k, p) + p*binomial1(N-1, k-1, p);
    }

    // memoization
    public static double binomial2(int N, int k, double p) {
        double[][] b = new double[N+1][k+1];

        // base cases
        // for (int i = 0; i <= N; i++)
        // i can start from 1 to avoid calculating b[0][0] twice
        for (int i = 1; i <= N; i++)
            b[i][0] = Math.pow(1.0 - p, i);
        b[0][0] = 1.0;

        // recursive formula
        for (int i = 1; i <= N; i++) {
            // for (int j = 1; j <= Math.min(i, k); j++) {
            // Use j <= k is safe, because when i > k, b[i][k] = 0
            for (int j = 1; j <= k; j++) {
                b[i][j] = p * b[i-1][j-1] + (1.0 - p) *b[i-1][j];
            }
        }
        return b[N][k];
    }

    public static void main(String[] args) { 
        int N = Integer.parseInt(args[0]);
        int k = Integer.parseInt(args[1]);
        double p = Double.parseDouble(args[2]);
        Binomial.recursiveCallCount = 0;
        StdOut.println(binomial1(N, k, p));
        StdOut.println("There were " + Binomial.recursiveCallCount +
                       " recursive calls in binomial1().");
        StdOut.println(binomial2(N, k, p));
    }

}