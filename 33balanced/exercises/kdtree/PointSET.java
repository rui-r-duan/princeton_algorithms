import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Bag;

public class PointSET {
    SET<Point2D> set;

    /**
     * construct an empty set of points
     */
    public PointSET() {
        set = new SET<Point2D>();
    }

    /**
     * is the set empty?
     */
    public boolean isEmpty() {
        return set.isEmpty();
    }

    /**
     * number of points in the set
     */
    public int size() {
        return set.size();
    }

    /**
     * add the point to the set (if it is not already in the set)
     */
    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException("called insert() with a null Point2D");
        set.add(p);
    }

    /**
     * does the set contain point p?
     */
    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException("called contains() with a null Point2D");
        return set.contains(p);
    }

    /**
     * draw all points to standard draw
     */
    public void draw() {
        for (Point2D p : set) {
            StdDraw.point(p.x(), p.y());
        }
    }

    /**
     * all points that are inside the rectangle (or on the boundary)
     */
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException("called range() with a null RecHV");
        Bag<Point2D> bag = new Bag<Point2D>();
        for (Point2D p : set) {
            if (rect.contains(p)) {
                bag.add(p);
            }
        }
        return bag;
    }

    /**
     * a nearest neighbor in the set to point p; null if the set is empty
     */
    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException("called nearest() with a null Point2D");
        if (set.isEmpty())
            return null;
        double min = Double.POSITIVE_INFINITY;
        Point2D nearestPoint = null;
        for (Point2D e : set) {
            double d = p.distanceSquaredTo(e);
            if (d < min) {
                min = d;
                nearestPoint = e;
            }
        }
        return nearestPoint;
    }

    /**
     * unit testing of the methods (optional)
     */
    public static void main(String[] args) {
        String filename = args[0];
        In in = new In(filename);
        PointSET brute = new PointSET();
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            brute.insert(p);
        }
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        StdDraw.enableDoubleBuffering();
        brute.draw();
        StdDraw.show();
    }
}
