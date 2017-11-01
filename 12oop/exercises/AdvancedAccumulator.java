/******************************************************************************
 *  Compilation:  javac AdvancedAccumulator.java
 *  Execution:    java AdvancedAccumulator < input.txt
 *  Dependencies: StdOut.java StdIn.java
 *
 *  Mutable data type that calculates the mean, sample standard
 *  deviation, and sample variance of a stream of real numbers
 *  use a stable, one-pass algorithm.
 *
 *  This implementation is less susceptible to floating-point roundoff error
 *  than the straightforward implementation based on saving the sum of the
 *  squares of the numbers.
 *
 *  exercise 1.2.18
 *
 ******************************************************************************/

public class AdvancedAccumulator {
    private int n = 0;          // number of data values
    private double sum = 0.0;   // sample variance * (n-1)
    private double mu = 0.0;    // sample mean

    public AdvancedAccumulator() {
    }

    public void addDataValue(double x) {
        n++;
        double delta = x - mu;
        mu  += delta / n;
        sum += (double) (n - 1) / n * delta * delta;
    }

    public double mean() {
        return mu;
    }

    public double var() {
        if (n <= 1) return Double.NaN;
        return sum / (n - 1);
    }

    public double stddev() {
        return Math.sqrt(this.var());
    }

    public int count() {
        return n;
    }

    public static void main(String[] args) {
        AdvancedAccumulator stats = new AdvancedAccumulator();
        while (!StdIn.isEmpty()) {
            double x = StdIn.readDouble();
            stats.addDataValue(x);
        }

        StdOut.printf("n      = %d\n",   stats.count());
        StdOut.printf("mean   = %.5f\n", stats.mean());
        StdOut.printf("stddev = %.5f\n", stats.stddev());
        StdOut.printf("var    = %.5f\n", stats.var());
    }
}
