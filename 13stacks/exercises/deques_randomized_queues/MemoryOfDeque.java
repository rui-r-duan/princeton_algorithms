import com.javamex.classmexer.MemoryUtil;
import edu.princeton.cs.algs4.LinearRegression;

public class MemoryOfDeque {
    public static void dequeOfStrings() {
        int[] sizes = {
            64, 128, 192, 256, 320, 384, 448, 512, 576, 640, 704, 768, 832, 896,
            960, 1024
        };
        int m = sizes.length;

        double[] x = new double[m];
        double[] memory = new double[m];

        for (int i = 0; i < m; i++) {
            int n = sizes[i];
            Deque<String> deque = new Deque<String>();
            for (int j = 0; j < n; j++)
                deque.addLast(Integer.toString(StdRandom.uniform(100000)));
            x[i] = deque.size();
            memory[i] = MemoryUtil.deepMemoryUsageOf(deque);
        }

        LinearRegression regression = new LinearRegression(x, memory);
        StdOut.println("Deque<String> of size n = " + regression);
    }

    public static void main(String[] args) {
        dequeOfStrings();
    }
}
