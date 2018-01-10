/********************************************************************************
 * KdTree implements a 2d-tree to store a set of Point2D.
 *
 * Note that in our notation, "left bottom" is where the origin (0,0) is in the
 * x-y plane.
 *
 * ASSUMPTIONS
 * All x- or y-coordinates of points inserted into the KdTree will be between 0
 * and 1.
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

    private static class Nearest {
        private Node node;
        private double min;
        public Nearest(Node node, double min) {
            this.node = node;
            this.min = min;
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
    private int keyComparePhase1(Point2D a, Point2D b, boolean isVertical) {
        if (isVertical) {
            return Double.compare(a.x(), b.x());
        }
        else {
            return Double.compare(a.y(), b.y());
        }
    }

    private int keyComparePhase2(Point2D a, Point2D b, boolean isVertical,
                                 int cmpResultPhase1) {
        if (cmpResultPhase1 < 0 || cmpResultPhase1 > 0)
            return cmpResultPhase1;
        else {
            if (isVertical)
                return Double.compare(a.y(), b.y());
            else
                return Double.compare(a.x(), b.y());
        }
    }

    // @param parentIsVertical the division orientation in the parent level
    private RectHV leftBranchRect(Node node, RectHV rect, Point2D p,
                                  boolean parentIsVertical) {
        if (node != null && node.rect != null) {
            return node.rect;
        }
        if (parentIsVertical) {
            return new RectHV(rect.xmin(), rect.ymin(), p.x(), rect.ymax());
        }
        else {
            return new RectHV(rect.xmin(), rect.ymin(), rect.xmax(), p.y());
        }
    }

    // @param parentIsVertical the division orientation in the parent level
    private RectHV rightBranchRect(Node node, RectHV rect, Point2D p,
                                   boolean parentIsVertical) {
        if (node != null && node.rect != null) {
            return node.rect;
        }
        if (parentIsVertical) {
            return new RectHV(p.x(), rect.ymin(), rect.xmax(), rect.ymax());
        }
        else {
            return new RectHV(rect.xmin(), p.y(), rect.xmax(), rect.ymax());
        }
    }

    // @pre p != null
    // @param isVertical the division orientation in the next lower level
    private Node put(Node node, Point2D p, RectHV val, boolean isVertical) {
        assert p != null;
        if (node == null) {
            n++;
            // StdOut.println(val);
            return new Node(p, val, null, null);
        }

        int cmp = keyComparePhase1(p, node.p, isVertical);

        if (cmp < 0) {
            // StdOut.print(val + " --> ");
            node.lb = put(node.lb, p,
                          leftBranchRect(node.lb, val, node.p, isVertical),
                          !isVertical); // change the division orientation in the next level
        }
        else if (cmp > 0) {
            // StdOut.print(val + " --> " );
            node.rt = put(node.rt, p,
                          rightBranchRect(node.rt, val, node.p, isVertical),
                          !isVertical); // change the division orientation in the next level
        }
        else {
            int cmp2 = keyComparePhase2(p, node.p, isVertical, cmp);
            if (cmp2 == 0) {
                // now p and node.p are equal
                node.rect = val;
            }
            else {
                // go to the right branch of node
                // StdOut.print(val + " --> " );
                node.rt = put(node.rt, p,
                              rightBranchRect(node.rt, val, node.p, isVertical),
                              !isVertical); // change the division orientation in the next level
            }
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

    // @pre key != null
    // @param isVertical the division orientation in the next lower level
    private Point2D get(Node node, Point2D key, boolean isVertical) {
        assert key != null;

        if (node == null)
            return null;

        int cmp = keyComparePhase1(key, node.p, isVertical);
        if (cmp < 0)
            return get(node.lb, key, !isVertical);
        else if (cmp > 0)
            return get(node.rt, key, !isVertical);
        else {
            int cmp2 = keyComparePhase2(key, node.p, isVertical, cmp);
            if (cmp2 == 0)
                return node.p;
            else
                return get(node.rt, key, !isVertical);
        }
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
            StdDraw.line(node.p.x(), node.rect.ymin(),
                         node.p.x(), node.rect.ymax());
        }
        else {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.setPenRadius();
            StdDraw.line(node.rect.xmin(), node.p.y(),
                         node.rect.xmax(), node.p.y());
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
    private void collectPointsInRect(Node node, RectHV rect,
                                     Bag<Point2D> pointSet) {
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
        Nearest champion = new Nearest(null, Double.POSITIVE_INFINITY);
        if (n == 0) {
            assert root == null;
            return null;
        }
        champion = nearestRecurr(root, true, p, champion);
        assert champion != null;
        assert champion.node != null;
        return champion.node.p;
    }

    private Nearest nearestRecurr(Node node, boolean isVertical,
                                  Point2D p,
                                  Nearest champion) {
        if (node == null)
            return champion;

        if (shouldPrune(node, p, champion.min))
            return champion;

        // process the current node
        double d = node.p.distanceSquaredTo(p);
        // java.awt.Color prevColor = StdDraw.getPenColor();
        // double prevRadius = StdDraw.getPenRadius();
        // StdDraw.setPenColor(StdDraw.BLACK);
        // StdDraw.setPenRadius();
        // StdDraw.line(p.x(), p.y(), node.p.x(), node.p.y());
        // StdDraw.setPenColor(prevColor);
        // StdDraw.setPenRadius(prevRadius);
        if (Double.compare(d, champion.min) < 0) {
            champion.node = node;
            champion.min = d;
        }

        // process the subtrees
        boolean shouldGoLeftFirst = true;
        if (isVertical) {
            // if the query point is to the left of the node point, go to the
            // left child branch fist, otherwise, go to the right child branch
            // first
            shouldGoLeftFirst = (Double.compare(p.x(), node.p.x()) < 0);
        }
        else {
            // if the query point is below the node point, go to the left child
            // branch first, otherwise, go to the right child branch first
            shouldGoLeftFirst = (Double.compare(p.y(), node.p.y()) < 0);
        }

        Node t1 = null;
        Node t2 = null;
        if (shouldGoLeftFirst) {
            t1 = node.lb;
            t2 = node.rt;
        }
        else {
            t1 = node.rt;
            t2 = node.lb;
        }
        champion = nearestRecurr(t1, !isVertical, p, champion);
        champion = nearestRecurr(t2, !isVertical, p, champion);
        return champion;
    }

    // @pre node != null
    private boolean shouldPrune(Node node, Point2D p, double minSoFar) {
        assert node != null : node;
        assert node.rect != null;

        double d = node.rect.distanceSquaredTo(p);
        return d > minSoFar;
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

        // test duplicate insertions
        StdOut.println("size = " + kdtree.size());
        StdOut.println("Test duplicate insertions");
        Point2D p = new Point2D(0.5, 0.5);
        StdOut.println("insert " + p);
        kdtree.insert(p);
        StdOut.println("insert " + p);
        kdtree.insert(p);
        StdOut.println("contains " + p + "? " + (kdtree.contains(p) ? "yes" : "no"));

        StdOut.println("isEmpty? " + kdtree.isEmpty());
        StdOut.println("size = " + kdtree.size());
        StdDraw.enableDoubleBuffering();
        StdDraw.setScale(-0.05, 1.05);
        kdtree.draw();
        StdDraw.show();
    }
}
