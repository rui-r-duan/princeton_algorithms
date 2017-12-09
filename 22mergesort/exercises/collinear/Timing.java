import edu.princeton.cs.algs4.Stopwatch;

public class Timing {
    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        StdOut.println(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // print and draw the line segments
        LineSegment[] segs = null;
        Stopwatch watch = null;
        double time = 0.0;
        if (args.length == 2) {
            if (args[1].equals("fast")) {
                watch = new Stopwatch();
                FastCollinearPoints collinear = new FastCollinearPoints(points);
                time = watch.elapsedTime();
                segs = collinear.segments();
            }
            else if (args[1].equals("brute")) {
                watch = new Stopwatch();
                BruteCollinearPoints collinear = new BruteCollinearPoints(points);
                time = watch.elapsedTime();
                segs = collinear.segments();
            }
        }
        else {
            watch = new Stopwatch();
            FastCollinearPoints collinear = new FastCollinearPoints(points);
            time = watch.elapsedTime();
            segs = collinear.segments();
        }

        StdOut.println("number of segments: " + segs.length);
        StdOut.println("time = " + time + " seconds.");
    }
}
