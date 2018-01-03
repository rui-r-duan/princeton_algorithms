import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.ResizingArrayStack;

public class Solver {
    private ResizingArrayStack<Board> solution;
    private final boolean isSolvable;

    private class SearchNode implements Comparable<SearchNode> {
        final Board board;
        int moves;
        SearchNode pre;         // predecessor search node
        int priority;
        // final String str;       // for debug

        public SearchNode(Board bd, int moves, SearchNode pre) {
            this.board = bd;
            this.moves = moves;
            this.pre = pre;
            this.priority = board.manhattan() + moves;

            // StringBuilder sb = new StringBuilder(board.toString());
            // sb.append(String.format("moves=%2d\n", moves));
            // sb.append(String.format("priority=%2d\n", priority));
            // str = sb.toString();
        }

        @Override
        public int compareTo(SearchNode that) {
            return this.priority - that.priority;
        }

        // @Override
        // public String toString() {
        //     return str;
        // }
    }

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) throw new IllegalArgumentException("null Board");

        solution = null;

        SearchNode nodeInit = new SearchNode(initial, 0, null);
        MinPQ<SearchNode> pqInit = new MinPQ<SearchNode>();
        pqInit.insert(nodeInit);

        SearchNode nodeTwin = new SearchNode(initial.twin(), 0, null);
        MinPQ<SearchNode> pqTwin = new MinPQ<SearchNode>();
        pqTwin.insert(nodeTwin);

        // If the initial board is solvable, 1;
        // if the twin board is solvable, 2.
        int indicator = 0;      // initial value is 0
        SearchNode minInit = null;
        SearchNode minTwin = null;
        while (indicator == 0) {
            // debug
            // for (SearchNode node : pqInit) {
            //     StdOut.println(node);
            // }
            // StdOut.println("debug round done, pq size = " + pqInit.size());

            minInit = pqInit.delMin();
            for (Board b : minInit.board.neighbors()) {
                SearchNode node = new SearchNode(b, minInit.moves + 1, minInit);
                if (minInit.pre == null || !b.equals(minInit.pre.board))
                    pqInit.insert(node);
            }
            if (minInit.board.isGoal()) {
                indicator = 1;
                break;
            }

            minTwin = pqTwin.delMin();
            for (Board b : minTwin.board.neighbors()) {
                SearchNode node = new SearchNode(b, minTwin.moves + 1, minTwin);
                if (minInit.pre == null || !b.equals(minTwin.pre.board))
                    pqTwin.insert(node);
            }
            if (minTwin.board.isGoal()) {
                indicator = 2;
                break;
            }
        }

        isSolvable = (indicator == 1);
        if (isSolvable) {
            solution = new ResizingArrayStack<Board>();
            SearchNode node = minInit;
            while (node != null) {
                solution.push(node.board);
                node = node.pre;
            }
        }
    }

    // is the initial board solvable?
    public boolean isSolvable() {
        return isSolvable;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (isSolvable)
            return solution.size() - 1;
        else
            return -1;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        return solution;
    }

    // solve a slider puzzle
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}
