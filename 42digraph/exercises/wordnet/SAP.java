import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Bag;
import java.util.Iterator;

public class SAP {
    private final Digraph G;

    /**
     * constructor takes a digraph (not necessarily a DAG)
     */
    public SAP(Digraph G) {
        this.G = new Digraph(G);
    }

    /**
     * length of shortest ancestral path between v and w; -1 if no such path
     */
    public int length(int v, int w) {
        BreadthFirstDirectedPaths bfs = new BreadthFirstDirectedPaths(G, v);
        int[] distFromV = distFrom(v, bfs);
        BreadthFirstDirectedPaths bfs2 = new BreadthFirstDirectedPaths(G, w);
        int[] distFromW = distFrom(w, bfs2);
        int[] result = calcAncestor(distFromV, distFromW);
        return result[0];       // min
    }

    /**
     * a common ancestor of v and w that participates in a shortest ancestral
     * path; -1 if no such path
     */
    public int ancestor(int v, int w) {
        BreadthFirstDirectedPaths bfs = new BreadthFirstDirectedPaths(G, v);
        int[] distFromV = distFrom(v, bfs);
        BreadthFirstDirectedPaths bfs2 = new BreadthFirstDirectedPaths(G, w);
        int[] distFromW = distFrom(w, bfs2);
        int[] result = calcAncestor(distFromV, distFromW);
        return result[1];       // ancestor
    }

    /**
     * length of shortest ancestral path between any vertex in v and any vertex
     * in w; -1 if no such path
     */
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        BreadthFirstDirectedPaths bfs = new BreadthFirstDirectedPaths(G, v);
        int[] distFromV = distFrom(v, bfs);
        BreadthFirstDirectedPaths bfs2 = new BreadthFirstDirectedPaths(G, w);
        int[] distFromW = distFrom(w, bfs2);
        int[] result = calcAncestor(distFromV, distFromW);
        return result[0];       // min
    }

    /**
     * a common ancestor that participates in shortest ancestral path; -1 if no
     * such path
     */
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        BreadthFirstDirectedPaths bfs = new BreadthFirstDirectedPaths(G, v);
        int[] distFromV = distFrom(v, bfs);
        BreadthFirstDirectedPaths bfs2 = new BreadthFirstDirectedPaths(G, w);
        int[] distFromW = distFrom(w, bfs2);
        int[] result = calcAncestor(distFromV, distFromW);
        return result[1];       // ancestor

    }

    // Query BreadthFirstDirectedPaths the distances from vertex v to each
    // vertex.
    // @pre bfs != null
    // @pre bfs is created by the constructor
    //      BreadthFirstDirectedPaths(Digraph, int)
    private int[] distFrom(int v, BreadthFirstDirectedPaths bfs) {
        assert bfs != null;
        int[] dist = new int[G.V()];
        for (int i = 0; i < G.V(); i++) {
            if (bfs.hasPathTo(i))
                dist[i] = bfs.distTo(i);
            else
                dist[i] = -1;
        }
        return dist;
    }

    // Query BreadthFirstDirectedPaths the distances from vertex v to each
    // vertex.
    // @pre bfs != null
    // @pre bfs is created by the constructor
    //      BreadthFirstDirectedPaths(Digraph, Interable<Integer>)
    private int[] distFrom(Iterable<Integer> v, BreadthFirstDirectedPaths bfs) {
        assert bfs != null;
        int[] dist = new int[G.V()];
        for (int i = 0; i < G.V(); i++) {
            if (bfs.hasPathTo(i))
                dist[i] = bfs.distTo(i);
            else
                dist[i] = -1;
        }
        return dist;
    }


    // @pre distFromV != null
    // @pre distFromW != null
    // @return int[] int[0] is min distance, int[1] is ancestor
    //               If no common ancestor, min=-1, ancestor=-1.
    private int[] calcAncestor(int[] distFromV, int[] distFromW) {
        assert distFromV != null;
        assert distFromW != null;
        int min = Integer.MAX_VALUE;
        int ancestor = -1;
        for (int i = 0; i < G.V(); i++) {
            if (distFromV[i] != -1 && distFromW[i] != -1) {
                // both v and w have a path to i
                int localMin = distFromV[i] + distFromW[i];
                if (localMin < min) {
                    min = localMin;
                    ancestor = i;
                }
            }
        }
        if (ancestor == -1)
            min = -1;
        return new int[] { min, ancestor };
    }

    /**
     * do unit testing of this class
     * usage example:
     * $java SAP digraph1.txt vn=3 wn=1
     * 7 4 9
     * 2
     * length([9,4,7],[2])=3
     * ancestor([9,4,7],[2])=0
     */
    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph G = new Digraph(in);

        int vn = Integer.parseInt(args[1]);
        int wn = Integer.parseInt(args[2]);
        Bag<Integer> v = new Bag<Integer>();
        for (int i = 0; i < vn; i++) {
            v.add(StdIn.readInt());
        }
        Bag<Integer> w = new Bag<Integer>();
        for (int i = 0; i < wn; i++) {
            w.add(StdIn.readInt());
        }

        SAP sap = new SAP(G);
        StdOut.printf("length(%s,%s)=%d\n", iterableToString(v),
                      iterableToString(w), sap.length(v, w));
        StdOut.printf("ancestor(%s,%s)=%d\n", iterableToString(v),
                      iterableToString(w), sap.ancestor(v, w));
    }

    private static String iterableToString(Iterable<Integer> list) {
        Iterator<Integer> itr = list.iterator();
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        boolean first = true;
        while (itr.hasNext()) {
            if (first) {
                first = false;
            }
            else {
                sb.append(",");
            }
            sb.append(itr.next());
        }
        sb.append("}");
        return sb.toString();
    }
}
