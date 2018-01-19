/*******************************************************************************
 * sample test
 *
 * In tinyDG,
 * shorted path from 11 to 0:   11->4->2->0
 * shorted path from 6 to 0:    6->0
 * shorted path from 3 to 0:    3->2->0
 *
 * BreadthFirstDirectedPaths finds the shortest path from [11,3,6] to 0: 6->0
 *
 * $ java-algs4 TestBreadthFirstDirectedPaths tinyDG.txt 11 3 6
 * [11,3,6] to 0 (1):  6->0
 * [11,3,6] to 1 (2):  6->0->1
 * [11,3,6] to 2 (1):  3->2
 * [11,3,6] to 3 (0):  3
 * [11,3,6] to 4 (1):  11->4
 * [11,3,6] to 5 (1):  3->5
 * [11,3,6] to 6 (0):  6
 * [11,3,6] to 7 (-):  not connected
 * [11,3,6] to 8 (1):  6->8
 * [11,3,6] to 9 (1):  6->9
 * [11,3,6] to 10 (2):  6->9->10
 * [11,3,6] to 11 (0):  11
 * [11,3,6] to 12 (1):  11->12
 * 
 ******************************************************************************/
import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.ResizingArrayBag;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import java.util.Iterator;

public class TestBreadthFirstDirectedPaths {
    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph G = new Digraph(in);

        ResizingArrayBag<Integer> bag = new ResizingArrayBag<Integer>();
        for (int i = 1; i < args.length; i++) {
            bag.add(Integer.parseInt(args[i]));
        }

        BreadthFirstDirectedPaths bfs = new BreadthFirstDirectedPaths(G, bag);

        for (int v = 0; v < G.V(); v++) {
            if (bfs.hasPathTo(v)) {
                StdOut.printf("%s to %d (%d):  ", iterableToString(bag),
                              v, bfs.distTo(v));
                boolean first = true;
                for (int x : bfs.pathTo(v)) {
                    if (first) {
                        StdOut.print(x);
                        first = false;
                    }
                    else
                        StdOut.print("->" + x);
                }
                StdOut.println();
            }
            else {
                StdOut.printf("%s to %d (-):  not connected\n",
                              iterableToString(bag), v);
            }
        }
    }

    private static String iterableToString(Iterable<Integer> list) {
        Iterator<Integer> itr = list.iterator();
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        boolean first = true;
        while (itr.hasNext()) {
            if (!first) {
                sb.append(",");
            }
            else {
                first = false;
            }
            sb.append(itr.next());
        }
        sb.append("]");
        return sb.toString();
    }
}
