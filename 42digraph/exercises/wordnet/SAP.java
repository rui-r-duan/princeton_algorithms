/*******************************************************************************
 * Compilation:  javac SAP.java
 * Execution:    java SAP vn wn
 * Dependencies: Digraph.java BreadthFirstDirectedPaths.java
 *
 * Shortest Ancestral Path
 *
 * $ java SAP digraph1.txt
 * 3 1
 * 7 4 9
 * 2
 * length([9,4,7],[2])=3
 * ancestor([9,4,7],[2])=0
 *
 ******************************************************************************/
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Bag;
import java.util.Iterator;

/**
 * The {@code SAP} class can find a shortest ancestral path between two sets of
 * vertices in a given Digraph. This class is immutable.
 * <p>
 * An ancestral path between two vertices v and w in a digraph is a directed
 * path from v to a common ancestor x, together with a directed path from w to
 * the same ancestor x. A shortest ancestral path (SAP) is an ancestral path of
 * minimum total length.
 * <p>
 * The methods {@code length()} return the length of the SAP between two
 * vertices or two sets of vertices.
 * <p>
 * The methods {@code ancestor()} return a common ancestor that participates in
 * SAP between two vertices or two sets of vertices.
 *
 * @author Ryan Duan
 */
public class SAP {
    private final Digraph G;

    /**
     * constructor takes a digraph (not necessarily a DAG)
     * @param G a digraph
     * @throws IllegalArgumentException if G is null
     */
    public SAP(Digraph G) {
        if (G == null)
            throw new IllegalArgumentException("argument cannot be null");
        this.G = new Digraph(G);
    }

    /**
     * length of shortest ancestral path between v and w; -1 if no such path
     * @param v vertex id (an integer)
     * @param w vertex id (an integer)
     * @throws IllegalArgumentException if either {@code v} or {@code w} is out
     * of the vertex value range of the given digraph
     */
    public int length(int v, int w) {
        BreadthFirstDirectedPaths bfs = new BreadthFirstDirectedPaths(G, v);
        int[] distFromV = distFrom(bfs);
        BreadthFirstDirectedPaths bfs2 = new BreadthFirstDirectedPaths(G, w);
        int[] distFromW = distFrom(bfs2);
        int[] result = calcAncestor(distFromV, distFromW);
        return result[0];       // min
    }

    /**
     * a common ancestor of v and w that participates in a shortest ancestral
     * path; -1 if no such path
     * @param v vertex ID (an integer)
     * @param w vertex ID (an integer)
     * @throws IllegalArgumentException if either {@code v} or {@code w} is out
     * of the vertex value range of the given digraph
     */
    public int ancestor(int v, int w) {
        BreadthFirstDirectedPaths bfs = new BreadthFirstDirectedPaths(G, v);
        int[] distFromV = distFrom(bfs);
        BreadthFirstDirectedPaths bfs2 = new BreadthFirstDirectedPaths(G, w);
        int[] distFromW = distFrom(bfs2);
        int[] result = calcAncestor(distFromV, distFromW);
        return result[1];       // ancestor
    }

    /**
     * length of shortest ancestral path between any vertex in v and any vertex
     * in w; -1 if no such path
     * @param v a list of vertex IDs
     * @param w a list of vertex IDs
     * @throws IllegalArgumentException if any vertex in {@code v} or {@code w}
     * is out of the vertex value range of the given digraph
     */
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        BreadthFirstDirectedPaths bfs = new BreadthFirstDirectedPaths(G, v);
        int[] distFromV = distFrom(bfs);
        BreadthFirstDirectedPaths bfs2 = new BreadthFirstDirectedPaths(G, w);
        int[] distFromW = distFrom(bfs2);
        int[] result = calcAncestor(distFromV, distFromW);
        return result[0];       // min
    }

    /**
     * a common ancestor that participates in shortest ancestral path; -1 if no
     * such path
     * @param v a list of vertex IDs
     * @param w a list of vertex IDs
     * @throws IllegalArgumentException if any vertex in {@code v} or {@code w}
     * is out of the vertex value range of the given digraph
     */
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        BreadthFirstDirectedPaths bfs = new BreadthFirstDirectedPaths(G, v);
        int[] distFromV = distFrom(bfs);
        BreadthFirstDirectedPaths bfs2 = new BreadthFirstDirectedPaths(G, w);
        int[] distFromW = distFrom(bfs2);
        int[] result = calcAncestor(distFromV, distFromW);
        return result[1];       // ancestor
    }

    // Query BreadthFirstDirectedPaths the distances from vertex v or vertices
    // v to each vertex.
    // @pre bfs != null
    private int[] distFrom(BreadthFirstDirectedPaths bfs) {
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
     * do unit testing of this class<p>
     * input format:<p>
     * <pre>v_num w_num
     * v1 v2 ... vn
     * w1 w2 ... wn</pre>
     * usage example:<p>
     * $java SAP digraph1.txt<p>
     * 3 1<p>
     * 7 4 9<p>
     * 2<p>
     * length([9,4,7],[2])=3<p>
     * ancestor([9,4,7],[2])=0
     * @param args args[0] digraph_file
     */
    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph G = new Digraph(in);

        int vn = StdIn.readInt();
        int wn = StdIn.readInt();
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

    // for debug printing
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
