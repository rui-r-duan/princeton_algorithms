import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Board {
    private final int n;        // dimension
    private final int[][] blocks;
    private Coord xyEmpty;      // empty block's position

    private class Coord {
        int x;
        int y;
        public Coord(int x, int y) {
            this.x = x;
            this.y = y;
        }
        public boolean equals(Object y) {
            if (this == y) return true;
            if (y == null) return false;
            if (this.getClass() != y.getClass()) return false;
            Coord that = (Coord) y;
            if (this.x != that.x) return false;
            if (this.y != that.y) return false;
            return true;
        }
    }

    // construct a board from an n-by-n array of blocks
    // (where blocks[i][j] = block in row i, column j)
    public Board(int[][] blocks) {
        this.n = blocks.length;
        this.blocks = blocks;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (blocks[i][j] == 0) {
                    this.xyEmpty = new Coord(i, j);
                    break;
                }
            }
        }
        if (this.xyEmpty == null)
            throw new IllegalArgumentException("invalid Board blocks input");
    }

    // board dimension n
    public int dimension() {
        return n;
    }

    // number of blocks out of place
    public int hamming() {
        int s = 0;
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                if (!(i == n-1 && j == n-1) && (blocks[i][j] != i*n + j + 1))
                    s++;
        return s;
    }

    // Manhattan distance of two "points", each board position is a point
    private int manhattanDistance(int i1, int j1, int i2, int j2) {
        return Math.abs(i2 - i1) + Math.abs(j2 - j1);
    }

    // sum of Manhattan distances between blocks and goal
    public int manhattan() {
        int s = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int v = blocks[i][j];    // current block value
                if (v != 0) {
                    int gi = (v - 1) / n; // goal position index i
                    int gj = v - 1 - gi * n; // goal position index j

                    // Add Manhattan distance between the current block position
                    // and its goal position.
                    s += manhattanDistance(i, j, gi, gj);
                }
            }
        }
        return s;
    }

    // is this board the goal board?
    public boolean isGoal() {
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                if (i == n-1 && j == n-1) {
                    if (blocks[i][j] != 0)
                        return false;
                }
                else {
                    if (blocks[i][j] != i*n + j + 1)
                        return false;
                }
        return true;
    }

    // Array "a"'s elements are changed, and the changed "a" is returned.
    private int[][] swap(int[][] a, int i1, int j1, int i2, int j2) {
        int t = a[i2][j2];
        a[i2][j2] = a[i1][j1];
        a[i1][j1] = t;
        return a;
    }

    // a board that is obtained by exchanging any pair of blocks
    public Board twin() {
        int[][] nb = blocks.clone();
        for (int i = 0; i < nb.length; i++) {
            nb[i] = blocks[i].clone();
        }

        boolean firstSelected = false;
        boolean secondSelected = false;
        int i1 = 0, j1 = 0, i2 = 0, j2 = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (firstSelected) {
                    // then select the second
                    if (secondSelected) {
                        break;
                    }
                    else {
                        if (nb[i][j] != 0) {
                            i2 = i;
                            j2 = j;
                            secondSelected = true;
                        }
                    }
                }
                else {          // select the first
                    if (nb[i][j] != 0) {
                        i1 = i;
                        j1 = j;
                        firstSelected = true;
                    }
                    else
                        ;       // continue checking next position
                }
            }
        }
        assert firstSelected && secondSelected;

        nb = swap(nb, i1, j1, i2, j2);

        return new Board(nb);
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (this == y) return true;
        if (y == null) return false;
        if (this.getClass() != y.getClass()) return false;
        Board that = (Board) y;
        if (this.n != that.n)
            return false;
        if (!java.util.Arrays.deepEquals(this.blocks, that.blocks))
            return false;
        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        return new NeighborIterable();
    }

    private class NeighborIterable implements Iterable<Board> {
        public Iterator<Board> iterator() {
            return new NeighborIterator();
        }
    }

    private class NeighborIterator implements Iterator<Board> {
        private int cnt;
        private Coord nextPos;

        @Override
        public boolean hasNext() {
            if (cnt == 0) {
                nextPos = getLeft(xyEmpty);
                if (nextPos != null)
                    return true;
                else
                    cnt++;
            }
            if (cnt == 1) {
                nextPos = getRight(xyEmpty);
                if (nextPos != null)
                    return true;
                else
                    cnt++;
            }
            if (cnt == 2) {
                nextPos = getUp(xyEmpty);
                if (nextPos != null)
                    return true;
                else
                    cnt++;
            }
            if (cnt == 3) {
                nextPos = getDown(xyEmpty);
                if (nextPos != null)
                    return true;
                else
                    cnt++;
            }
            return cnt < 4;
        }

        @Override
        public Board next() {
            if (!hasNext()) throw new NoSuchElementException();

            assert nextPos != null;
            int[][] nb = blocks.clone();
            for (int j = 0; j < nb.length; j++) {
                nb[j] = blocks[j].clone();
            }
            swap(nb, xyEmpty.x, xyEmpty.y, nextPos.x, nextPos.y);
            Board board = new Board(nb);
            cnt++;
            return board;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

        // get the coordination of the left element of the current "pos"
        private Coord getLeft(Coord pos) {
            if (pos.x == 0)
                return null;
            else
                return new Coord(pos.x - 1, pos.y);
        }
        private Coord getUp(Coord pos) {
            if (pos.y == 0)
                return null;
            else
                return new Coord(pos.x, pos.y - 1);
        }
        private Coord getDown(Coord pos) {
            if (pos.y == n - 1)
                return null;
            else
                return new Coord(pos.x, pos.y + 1);
        }
        private Coord getRight(Coord pos) {
            if (pos.x == n - 1)
                return null;
            else
                return new Coord(pos.x + 1, pos.y);
        }
    }

    // string representation of this board
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++) {
                s.append(String.format("%2d", blocks[i][j]));
                if (j == n-1)
                    s.append("\n");
                else
                    s.append(" ");
            }
        return s.toString();
    }

    // unit tests (not graded)
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();

        Board initial = new Board(blocks);
        StdOut.println(initial.dimension());
        StdOut.println(initial);
        StdOut.println(initial.isGoal());
        StdOut.println("hamming: " + initial.hamming());
        StdOut.println("manhattan: " + initial.manhattan());
        StdOut.println("twin:");
        Board twin = initial.twin();
        StdOut.println(twin);
        Board b2 = new Board(blocks);
        StdOut.println(b2.equals(twin));
        StdOut.println(b2.equals(initial));
        StdOut.println("empty position: " + initial.xyEmpty.x + " " + initial.xyEmpty.y);
        StdOut.println();
        StdOut.println("Iterable test 1: for loop");
        for (Board b : initial.neighbors()) {
            StdOut.println(b);
        }
        StdOut.println("Iterable test 2");
        Iterable<Board> it = initial.neighbors();
        Iterator<Board> itr = it.iterator();
        try {
            StdOut.println(itr.next());
            StdOut.println(itr.next());
            StdOut.println(itr.next());
            StdOut.println(itr.next());
        }
        catch (NoSuchElementException e) {
            StdOut.println(e);
        }
    }
}
