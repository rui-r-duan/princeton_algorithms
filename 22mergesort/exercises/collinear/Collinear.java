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
        StdDraw.setXscale(-1000, 32768);
        StdDraw.setYscale(-1000, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        LineSegment[] segs = null;
        if (args.length == 2) {
            if (args[1].equals("fast")) {
                StdOut.println("\nfast");
                FastCollinearPoints collinear = new FastCollinearPoints(points);
                segs = collinear.segments();
            }
            else if (args[1].equals("brute")) {
                StdOut.println("\nbrute");
                BruteCollinearPoints collinear = new BruteCollinearPoints(points);
                segs = collinear.segments();
            }
        }
        else {
            StdOut.println("\nfast");
            FastCollinearPoints collinear = new FastCollinearPoints(points);
            segs = collinear.segments();
        }

        for (LineSegment segment : segs) {
            StdOut.println(segment);
            segment.draw();
            StdDraw.show();
            // StdDraw.pause(200);
        }
        StdOut.println("number of segments: " + segs.length);
    }
}
