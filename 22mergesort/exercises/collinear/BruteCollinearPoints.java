import edu.princeton.cs.algs4.ResizingArrayBag;
import edu.princeton.cs.algs4.MergeBU;

public class BruteCollinearPoints {
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
     * Finds all line segments containing 4 points.
     *
     * Corner cases. Throw a java.lang.IllegalArgumentException if the argument
     * to the constructor is null, if any point in the array is null, or if the
     * argument to the constructor contains a repeated point.
     *
     * For simplicity, we will not supply any input to BruteCollinearPoints
     * that has 5 or more collinear points.
     *
     * Assuming the input has at least 4 points.
     */
    public BruteCollinearPoints(Point[] points) {
        if (points == null)
            throw new IllegalArgumentException();

        for (int i = 0; i < points.length; i++) {
            if (points[i] == null)
                throw new IllegalArgumentException();
        }

        ResizingArrayBag<Pair> bag = new ResizingArrayBag<Pair>();
        
        for (int i = 0; i < points.length; i++) {
            for (int j = i+1; j < points.length; j++) {
                for (int k = j+1; k < points.length; k++) {
                    for (int m = k+1; m < points.length; m++) {                   
                        double slope1 = points[i].slopeTo(points[j]);
                        double slope2 = points[i].slopeTo(points[k]);
                        double slope3 = points[i].slopeTo(points[m]);

                        // check if any of them is a repeated point
                        if (Double.compare(slope1, Double.NEGATIVE_INFINITY) == 0
                            || Double.compare(slope2, Double.NEGATIVE_INFINITY) == 0
                            || Double.compare(slope3, Double.NEGATIVE_INFINITY) == 0) {
                            throw new IllegalArgumentException();
                        }

                        if (Double.compare(slope1, slope2) == 0
                            && Double.compare(slope1, slope3) == 0) {

                            // find the two endpoints of the segment
                            int min = i;
                            if (less(points[j], points[min])) {
                                min = j;
                            }
                            if (less(points[k], points[min])) {
                                min = k;
                            }
                            if (less(points[m], points[min])) {
                                min = m;
                            }

                            int max = m;
                            if (less(points[max], points[i])) {
                                max = i;
                            }
                            if (less(points[max], points[j])) {
                                max = j;
                            }
                            if (less(points[max], points[k])) {
                                max = k;
                            }

                            bag.add(new Pair(points[min], points[max]));
                        }
                    }
                }
            }
        }
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
        return segments;
    }
}
