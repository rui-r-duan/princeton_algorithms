import edu.princeton.cs.algs4.Topological;
import edu.princeton.cs.algs4.EdgeWeightedDigraph;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;

public class TestTopological {
    public static void main(String[] args) {
        In stream = new In(args[0]);
        EdgeWeightedDigraph G = new EdgeWeightedDigraph(stream);
        Topological topological = new Topological(G);
        for (int v : topological.order()) {
            StdOut.println(v);
        }
    }
}
