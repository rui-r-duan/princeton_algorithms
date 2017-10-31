public class SimpleAccumulator {
    private double sum;
    private int n;
    
    public void addDataValue(double value) {
        n++;
        sum += value;
    }
    
    public double mean() {
        return sum / n;
    }
    
    public String toString() {
        String s = "Mean (" + n + " values): ";
        return s + String.format("%7.5f", mean());
    }
    
    public static void main(String[] args) {
        int trials = Integer.parseInt(args[0]);
        SimpleAccumulator accumulator = new SimpleAccumulator();
        for (int t = 0; t < trials; t++) {
            accumulator.addDataValue(StdRandom.uniform(0.0, 1.0));
        }
        StdOut.println(accumulator);
    }
}