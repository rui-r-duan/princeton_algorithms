import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.Stopwatch;

public class PercolationStats {
    private Percolation p;
    private double mean;
    private double stddev;
    private double confidenceLo;
    private double confidenceHi;

    /**
     * perform trials independent experiments on an n-by-n grid
     */
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("illegal N or TRIALS. Must > 0");
        }
        double totalSites = n * n;
        double[] x = new double[trials]; // ratio=(#open)/total for each trial
        for (int i = 0; i < trials; i++) {
            p = new Percolation(n);
            while (!p.percolates()) {
                int r = StdRandom.uniform(1, n+1);
                int c = StdRandom.uniform(1, n+1);
                p.open(r, c);
            }
            x[i] = (double)p.numberOfOpenSites() / totalSites;
        }
        mean = StdStats.mean(x);
        stddev = StdStats.stddev(x);
        double term = 1.96 * stddev / Math.sqrt(trials);
        confidenceLo = mean - term;
        confidenceHi = mean + term;
    }

    /**
     * sample mean of percolation threshold
     */
    public double mean() {
        return mean;
    }

    /**
     * sample standard deviation of percolation threshold
     */
    public double stddev() {
        return stddev;
    }

    /**
     * low endpoint of 95% confidence interval
     */
    public double confidenceLo() {
        return confidenceLo;
    }

    /**
     * high endpoint of 95% confidence interval
     */
    public double confidenceHi() {
        return confidenceHi;
    }

    /**
     * test client
     */
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        Stopwatch stopwatch = new Stopwatch();
        PercolationStats stats = new PercolationStats(n, trials);
        double elapsedTime = stopwatch.elapsedTime();
        StdOut.printf("%-23s = %.16f\n", "mean", stats.mean());
        StdOut.printf("%-23s = %.18f\n", "stddev", stats.stddev());
        StdOut.printf("%-23s = [%.16f, %.16f]\n", "95% confidence interval",
                      stats.confidenceLo(), stats.confidenceHi());
        StdOut.printf("elapsedTime = %f\n", elapsedTime);
    }
}
