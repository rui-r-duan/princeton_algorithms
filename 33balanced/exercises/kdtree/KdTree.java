import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.StdOut;

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
        root = put(root, p, null, 0);
    }

    private int keyCompare(Point2D a, Point2D b, int level) {
        if (level % 2 == 0) {
            return Double.compare(a.x(), b.x());
        }
        else {
            return Double.compare(a.y(), b.y());
        }
    }
    
    private Node put(Node node, Point2D p, RectHV val, int level) {
        if (node == null) {
            n++;
            return new Node(p, null, null, null);
        }

        int cmp = keyCompare(node.p, p, level);

        if (cmp < 0)
            node.lb = put(node.lb, p, val, level + 1);
        else if (cmp > 0)
            node.rt = put(node.rt, p, val, level + 1);
        else
            node.rect = val;

        return node;
    }

    /**
     * does the set contain point p?
     */
    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException("called contains() with a null Point2D");
        return get(root, p, 0) != null;
    }

    private Point2D get(Node node, Point2D p, int level) {
        if (node == null)
            return null;

        int cmp = keyCompare(node.p, p, level);
        if (cmp < 0)
            return get(node.lb, p, level + 1);
        else if (cmp > 0)
            return get(node.rt, p, level + 1);
        else
            return node.p;
    }

    /**
     * draw all points to standard draw
     */
    public void draw() {
    }

    /**
     * all points that are inside the rectangle (or on the boundary)
     */
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException("called range() with a null RecHV");
        Bag<Point2D> bag = new Bag<Point2D>();
        return bag;
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
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            StdOut.println("insert " + p);
            kdtree.insert(p);
            if (kdtree.contains(p)) {
                StdOut.println("yes");
            }
        }
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        StdDraw.enableDoubleBuffering();
        kdtree.draw();
        StdDraw.show();
    }
}
