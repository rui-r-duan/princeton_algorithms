import java.util.Arrays;
// import edu.princeton.cs.algs4.StdDraw;

public class FastCollinearPoints {
    private int n;        // number of segments
    private final LineSegment[] segments;

    /**
     * finds all line segments containing 4 or more points
     */
    public FastCollinearPoints(Point[] points) {
        if (points == null)
            throw new IllegalArgumentException();

        for (int i = 0; i < points.length; i++) {
            if (points[i] == null)
                throw new IllegalArgumentException();
        }

        LineSegment[] tmp = new LineSegment[points.length];
        n = 0;

        if (points.length <= 3) {
            segments = new LineSegment[0];
            return;
        }
        for (int i = 0; i < points.length - 1; i++) {
            Arrays.sort(points, i+1, points.length, points[i].slopeOrder());
            // StdDraw.clear();
            // for (int s = i+1; s < points.length; s++) {
            //     points[i].drawTo(points[s]);
            //     StdDraw.show();
            //     StdDraw.pause(80);
            // }

            Point p = points[i];
            for (int j = i+1; j < points.length; ) {
                double slope = p.slopeTo(points[j]);
                // for debugging
                StdOut.printf("i\tp[i]\t\tp[j]\t\tslope\n");
                StdOut.printf("%d\t%s\t%s\t%f\n", i, p, points[j], p.slopeTo(points[j]));

                int k = j+1;
                while (k < points.length
                       && Double.compare(p.slopeTo(points[k]), slope) == 0) {
                    // for debugging
                    StdOut.printf("%d\t%s\t%s\t%f\n", i, p, points[k], p.slopeTo(points[k]));
                    k++;
                }
                // Now points[i], points[j] to points[k] are collinear.

                // if k - j < 3, then the collinear points are less than 4
                StdOut.printf("k-j=%d, k=%d, j=%d\n", k-j, k, j);
                if (k - j < 3) {
                    StdOut.println("\tcontinue...next j\n");
                    j = k;
                    continue;
                }

                // Now we have at least 4 points collinear,
                // then find the two endpoints among them.
                // Their indices are: i, j, k: [j+1, k)
                // Put their indices in an array
                int[] indice = new int[k - j + 1];
                indice[0] = i;
                for (int w = 1; w < k-j+1; w++) {
                    indice[w] = j + (w-1);
                }
                for (int w = 0; w < k-j+1; w++) {
                    StdOut.print(indice[w] + " ");
                }
                StdOut.println();
                int min = indice[0];
                for (int x = 0; x < k-j+1; x++) {
                    if (less(points[indice[x]], points[min])) {
                        min = indice[x];
                    }
                }
                int max = indice[k - j];
                for (int x = 0; x < k-j+1; x++) {
                    if (less(points[max], points[indice[x]])) {
                        max = indice[x];
                    }
                }

                StdOut.println("\t" + n + ", " + points[min] + " -> " + points[max]);
                tmp[n++] = new LineSegment(points[min], points[max]);

                j = k;
                StdOut.printf("next j=%d\n", j);
            }
        }
        segments = new LineSegment[n];
        for (int i = 0; i < n; i++) {
            segments[i] = tmp[i];
        }
    }

    private boolean less(Point a, Point b) {
        return a.compareTo(b) < 0;
    }

    /**
     * the number of line segments
     */
    public int numberOfSegments() {
        return n;
    }

    /**
     * the line segments
     */
    public LineSegment[] segments() {
        return segments;
    }
}
