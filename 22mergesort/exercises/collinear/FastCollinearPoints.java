import java.util.Arrays;
import edu.princeton.cs.algs4.ResizingArrayBag;
import edu.princeton.cs.algs4.MergeBU;
// import edu.princeton.cs.algs4.StdOut;

public class FastCollinearPoints {
    private int n;        // number of segments
    private final LineSegment[] segments;

    private class Pair implements Comparable<Pair> {
        Point p;
        Point q;
        Pair(Point p, Point q) {
            this.p = p;
            this.q = q;
        }
        @Override
        public int compareTo(Pair that) {
            if (this.p.compareTo(that.p) < 0) return -1;
            else if (this.p.compareTo(that.p) > 0) return +1;
            else {
                if (this.q.compareTo(that.q) < 0) return -1;
                else if (this.q.compareTo(that.q) > 0) return +1;
                else return 0;
            }
        }
    }

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

        ResizingArrayBag<Pair> bag = new ResizingArrayBag<Pair>();
        n = 0;

        if (points.length <= 3) {
            segments = new LineSegment[0];
            return;
        }
        for (int i = 0; i < points.length - 1; i++) {
            Point[] pointsCopy = new Point[points.length];
            System.arraycopy(points, 0, pointsCopy, 0, points.length);

            // swap pointsCopy[i] and pointsCopy[0]
            if (i > 0) {
                Point t = pointsCopy[i];
                pointsCopy[i] = pointsCopy[0];
                pointsCopy[0] = t;
            }

            Point p = pointsCopy[0];
            Arrays.sort(pointsCopy, 1, points.length, p.slopeOrder());

            for (int j = 1; j < pointsCopy.length; ) {
                double slope = p.slopeTo(pointsCopy[j]);
                // check if any of them has a repeated item same as p
                if (Double.compare(slope, Double.NEGATIVE_INFINITY) == 0) {
                    // StdOut.println(p + " and " + pointsCopy[j] + " are the same!");
                    throw new IllegalArgumentException();
                }

                // for debugging
                // StdOut.printf("i\tp[i]\t\tp[j]\t\tslope\n");
                // StdOut.printf("%d\t%s\t%s\t%f\n", i, p, pointsCopy[j], p.slopeTo(pointsCopy[j]));

                int k = j+1;
                while (k < pointsCopy.length
                       && Double.compare(p.slopeTo(pointsCopy[k]), slope) == 0) {
                    // for debugging
                    // StdOut.printf("%d\t%s\t%s\t%f\n", i, p, pointsCopy[k], p.slopeTo(pointsCopy[k]));
                    k++;
                }
                // Now pointsCopy[0], pointsCopy[j] to pointsCopy[k] are collinear.

                // if k - j < 3, then the collinear points are less than 4
                // StdOut.printf("k-j=%d, k=%d, j=%d\n", k-j, k, j);
                if (k - j < 3) {
                    // StdOut.println("\tcontinue...next j\n");
                    j = k;
                    continue;
                }

                // Now we have at least 4 points collinear,
                // then find the two endpoints among them.
                // Their indices are: 0, j, k: [j+1, k)
                // Put their indices in an array
                int[] indice = new int[k - j + 1];
                indice[0] = 0;
                for (int w = 1; w < k-j+1; w++) {
                    indice[w] = j + (w-1);
                }
                // for (int w = 0; w < k-j+1; w++) {
                //     StdOut.print(indice[w] + " ");
                // }
                // StdOut.println();
                int min = indice[0];
                for (int x = 0; x < k-j+1; x++) {
                    if (less(pointsCopy[indice[x]], pointsCopy[min])) {
                        min = indice[x];
                    }
                }
                int max = indice[k - j];
                for (int x = 0; x < k-j+1; x++) {
                    if (less(pointsCopy[max], pointsCopy[indice[x]])) {
                        max = indice[x];
                    }
                }

                // StdOut.println("\t" + (++n) + ", " + pointsCopy[min] + " -> " + pointsCopy[max]);
                bag.add(new Pair(pointsCopy[min], pointsCopy[max]));

                j = k;
                // StdOut.printf("next j=%d\n", j);
            }
        }
        // remove the duplicates in the bag
        n = bag.size();
        if (n == 0) {
            segments = new LineSegment[0];
            return;
        }
        
        Pair[] set = new Pair[n];
        int i = 0;
        for (Pair pair : bag) {
            set[i++] = pair;
        }
        MergeBU.sort(set);
        Pair[] set2 = new Pair[n];
        set2[0] = set[0];

        i = 0;              // index for set
        int j = 0;          // index for set2
        while (i < set.length) {
            if (set[i].compareTo(set2[j]) != 0) {
                ++j;
                set2[j] = set[i];
                i++;
            }
            else {
                i++;
            }
        }

        // create LineSegment[] according to set2
        n = j + 1;
        // StdOut.println(" n = " + n);
        segments = new LineSegment[n];
        for (i = 0; i < n; i++) {
            segments[i] = new LineSegment(set2[i].p, set2[i].q);
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
        LineSegment[] dst = new LineSegment[n];
        System.arraycopy(segments, 0, dst, 0, n);
        return dst;
    }
}
