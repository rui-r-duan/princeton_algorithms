/********************************************************************************
 * KdTree implements a 2d-tree to store a set of Point2D.
 *
 * Note that in our notation, "left bottom" is where the origin (0,0) is in the
 * x-y plane.
 *******************************************************************************/
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdDraw;

public class KdTree {
    private Node root;
    private int n;              // number of nodes in this tree

    private static class Node {
        private Point2D p;      // the point
        private RectHV rect;    // the axis-aligned rectangle corresponding to this node
        private Node lb;        // the left/bottom subtree
        private Node rt;        // the right/top subtree

        public Node(Point2D key, RectHV val, Node lb, Node rt) {
            this.p = key;
            this.rect = val;
            this.lb = lb;
            this.rt = rt;
        }
    }

    /**
     * construct an empty set of points
     */
    public KdTree() {
        root = null;
        n = 0;
    }

    /**
     * is the set empty?
     */
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * number of points in the set
     */
    public int size() {
        return n;
    }

    /**
     * add the point to the set (if it is not already in the set)
     */
    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException("called insert() with a null Point2D");
        RectHV rect = new RectHV(0.0, 0.0, 1.0, 1.0);
        root = put(root, p, rect, true);
    }

    // @param isVertical the division orientation in the current level
    private int keyCompare(Point2D a, Point2D b, boolean isVertical) {
        if (isVertical) {
            return Double.compare(a.x(), b.x());
        }
        else {
            return Double.compare(a.y(), b.y());
        }
    }

    // @param isVertical the division orientation in the current level
    private RectHV leftBranchRect(RectHV rect, Point2D p, boolean isVertical) {
        if (isVertical) {
            return new RectHV(rect.xmin(), rect.ymin(), p.x(), rect.ymax());
        }
        else {
            return new RectHV(rect.xmin(), rect.ymin(), rect.xmax(), p.y());
        }
    }

    // @param isVertical the division orientation in the current level
    private RectHV rightBranchRect(RectHV rect, Point2D p, boolean isVertical) {
        if (isVertical) {
            return new RectHV(p.x(), rect.ymin(), rect.xmax(), rect.ymax());
        }
        else {
            return new RectHV(rect.xmin(), p.y(), rect.xmax(), rect.ymax());
        }
    }

    // @param isVertical the division orientation in the next lower level
    private Node put(Node node, Point2D p, RectHV val, boolean isVertical) {
        if (node == null) {
            n++;
            // StdOut.println(val);
            return new Node(p, val, null, null);
        }

        int cmp = keyCompare(p, node.p, isVertical);

        if (cmp < 0) {
            // StdOut.print(val + " --> ");
            node.lb = put(node.lb, p, leftBranchRect(val, node.p, isVertical),
                          !isVertical); // change the division orientation in the next level
        }
        else /* if (cmp >= 0) */ {
            // StdOut.print(val + " --> " );
            node.rt = put(node.rt, p, rightBranchRect(val, node.p, isVertical),
                          !isVertical); // change the division orientation in the next level
        }

        return node;
    }

    /**
     * does the set contain point p?
     */
    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException("called contains() with a null Point2D");
        return p.equals(get(root, p, true));
    }

    // @param isVertical the division orientation in the next lower level
    private Point2D get(Node node, Point2D key, boolean isVertical) {
        if (node == null)
            return null;

        if (key.equals(node.p))
            return node.p;

        int cmp = keyCompare(key, node.p, isVertical);
        if (cmp < 0)
            return get(node.lb, key, !isVertical);
        else // if (cmp >= 0)
            return get(node.rt, key, !isVertical);
    }

    /**
     * draw all points to standard draw
     */
    public void draw() {
        root.rect.draw();
        drawSubTree(root, true);
    }

    private void drawSubTree(Node node, boolean isVertical) {
        if (node == null) return;

        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        StdDraw.point(node.p.x(), node.p.y());

        if (isVertical) {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.setPenRadius();
            StdDraw.line(node.p.x(), node.rect.ymin(), node.p.x(), node.rect.ymax());
        }
        else {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.setPenRadius();
            StdDraw.line(node.rect.xmin(), node.p.y(), node.rect.xmax(), node.p.y());
        }

        drawSubTree(node.lb, !isVertical);
        drawSubTree(node.rt, !isVertical);
    }


    /**
     * all points that are inside the rectangle (or on the boundary)
     */
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException("called range() with a null RecHV");
        Bag<Point2D> bag = new Bag<Point2D>();
        collectPointsInRect(root, rect, bag);
        return bag;
    }

    // @param node the subtree to check
    // @param rect the query rectangle
    // @param pointSet the accumulator that collects the points that are in the query rectangle
    private void collectPointsInRect(Node node, RectHV rect, Bag<Point2D> pointSet) {
        if (node == null)
            return;
        if (!rect.intersects(node.rect))
            return;

        if (rect.contains(node.p)) {
            pointSet.add(node.p);
        }

        collectPointsInRect(node.lb, rect, pointSet);
        collectPointsInRect(node.rt, rect, pointSet);
    }

    /**
     * a nearest neighbor in the set to point p; null if the set is empty
     */
    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException("called nearest() with a null Point2D");
        return null;
    }

    /**
     * unit testing of the methods (optional)
     */
    public static void main(String[] args) {
        String filename = args[0];
        In in = new In(filename);
        KdTree kdtree = new KdTree();
        StdOut.println("size = " + kdtree.size());
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            StdOut.println("insert " + p);
            kdtree.insert(p);
            StdOut.println("contains " + p + "? " + (kdtree.contains(p) ? "yes" : "no"));
        }
        StdOut.println("isEmpty? " + kdtree.isEmpty());
        StdOut.println("size = " + kdtree.size());
        StdDraw.enableDoubleBuffering();
        StdDraw.setScale(-0.05, 1.05);
        kdtree.draw();
        StdDraw.show();
    }
}
