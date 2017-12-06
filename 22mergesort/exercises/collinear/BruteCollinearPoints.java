public class BruteCollinearPoints {
    private int n;        // number of segments
    private final LineSegment[] segments;
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

        LineSegment[] tmp = new LineSegment[points.length / 4];
        n = 0;
        
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

                            tmp[n++] = new LineSegment(points[min], points[max]);
                        }
                    }
                }
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
