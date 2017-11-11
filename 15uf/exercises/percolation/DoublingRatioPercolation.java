/******************************************************************************
 *  Compilation:  javac DoublingRatioPercolation.java
 *  Execution:    java DoublingRatioPercolation
 *  Dependencies: Percolation.java PercolationStats.java Stopwatch.java
 *                StdRandom.java StdOut.java
 *
 *
 *  % java DoublingRatioPercolation
 *
 ******************************************************************************/

public class DoublingRatioPercolation {

    // This class should not be instantiated.
    private DoublingRatioPercolation() { }

    public static double timeTrial(int n, int trials) {
        Stopwatch timer = new Stopwatch();
        PercolationStats stats = new PercolationStats(n, trials);
        return timer.elapsedTime();
    }

    public static void main(String[] args) {
        int n = 5;
        int trials = 100;
        double prev = timeTrial(n, trials);
        StdOut.printf("%7s %7s %7s %5s %5s\n", "n", "trials", "time", 
                      "ratio", "log2");
        StdOut.printf("%7d %7d %7.1f %5s %5s\n", n, trials, prev, 
                      "-", "-");
        for (n = 10; n < 700; n += n) {
            double time = timeTrial(n, trials);
            double ratio = time / prev;
            StdOut.printf("%7d %7d %7.1f %5.1f %5.1f\n", n, trials, time, 
                          ratio, Math.log(ratio) / Math.log(2));
            prev = time;
        }
        StdOut.println();
        n = 20;
        trials = 100;
        prev = timeTrial(n, trials);
        StdOut.printf("%7s %7s %7s %5s %5s\n", "n", "trials", "time", 
                      "ratio", "log2");
        StdOut.printf("%7d %7d %7.1f %5s %5s\n", n, trials, prev, 
                      "-", "-");
        for (trials = 200; trials < 100000; trials += trials) {
            double time = timeTrial(n, trials);
            double ratio = time / prev;
            StdOut.printf("%7d %7d %7.1f %5.1f %5.1f\n", n, trials, time, 
                          ratio, Math.log(ratio) / Math.log(2));
            prev = time;
        }
    }
}
