import edu.princeton.cs.algs4.StdDraw;

public class Collinear {
    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        LineSegment[] segs = null;
        if (args.length == 2) {
            if (args[1].equals("fast")) {
                StdOut.println("fast");
                FastCollinearPoints collinear = new FastCollinearPoints(points);
                segs = collinear.segments();
            }
            else if (args[1].equals("brute")) {
                StdOut.println("brute");
                BruteCollinearPoints collinear = new BruteCollinearPoints(points);
                segs = collinear.segments();
            }
        }
        else {
            StdOut.println("fast");
            FastCollinearPoints collinear = new FastCollinearPoints(points);
            segs = collinear.segments();
        }

        for (LineSegment segment : segs) {
            StdOut.println(segment);
            segment.draw();
            StdDraw.pause(300);
        }
        StdDraw.show();
    }
}
