import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.In;

public class SAP {
    private final Digraph G;

    /**
     * constructor takes a digraph (not necessarily a DAG)
     */
    public SAP(Digraph G) {
        this.G = new Digraph(G);
        // StdOut.print(this.G);
    }

    /**
     * length of shortest ancestral path between v and w; -1 if no such path
     */
    public int length(int v, int w) {
        BreadthFirstDirectedPaths bfs = new BreadthFirstDirectedPaths(G, v);
        int[] distFromV = new int[G.V()];
        for (int i = 0; i < G.V(); i++) {
            if (bfs.hasPathTo(i)) {
                distFromV[i] = bfs.distTo(i);
            }
            else
                distFromV[i] = -1;
        }
        BreadthFirstDirectedPaths bfs2 = new BreadthFirstDirectedPaths(G, w);
        int[] distFromW = new int[G.V()];
        for (int i = 0; i < G.V(); i++) {
            if (bfs2.hasPathTo(i)) {
                distFromW[i] = bfs2.distTo(i);
            }
            else
                distFromW[i] = -1;
        }

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
            return -1;
        else
            return min;
    }

    /**
     * a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
     */
    public int ancestor(int v, int w) {
        BreadthFirstDirectedPaths bfs = new BreadthFirstDirectedPaths(G, v);
        int[] distFromV = new int[G.V()];
        for (int i = 0; i < G.V(); i++) {
            if (bfs.hasPathTo(i)) {
                distFromV[i] = bfs.distTo(i);
            }
            else
                distFromV[i] = -1;
        }
        BreadthFirstDirectedPaths bfs2 = new BreadthFirstDirectedPaths(G, w);
        int[] distFromW = new int[G.V()];
        for (int i = 0; i < G.V(); i++) {
            if (bfs2.hasPathTo(i)) {
                distFromW[i] = bfs2.distTo(i);
            }
            else
                distFromW[i] = -1;
        }

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

        return ancestor;
    }

    /**
     * length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
     */
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        return -1;
    }

    /**
     * a common ancestor that participates in shortest ancestral path; -1 if no such path
     */
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        return -1;
    }

    /**
     * do unit testing of this class
     */
    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph G = new Digraph(in);

        int v = Integer.parseInt(args[1]);
        int w = Integer.parseInt(args[2]);
        
        SAP sap = new SAP(G);
        StdOut.printf("length(%d,%d)=%d\n", v, w, sap.length(v, w));
        StdOut.printf("ancestor(%d,%d)=%d\n", v, w, sap.ancestor(v, w));
    }
}
