import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdOut;

public class GenTestData {
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        StdOut.println(n);
        for (int i = 0; i < n; i++) {
            int x = StdRandom.uniform(0, 32768/10);
            int y = StdRandom.uniform(0, 32768/10);
            StdOut.printf("%6d %6d\n", x * 10, y * 10);
        }
    }
}
