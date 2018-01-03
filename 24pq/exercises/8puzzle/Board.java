import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.ResizingArrayQueue;

public class Board {
    private final int n;        // dimension
    private final int[][] tiles;
    private int rowBlank = -1; // blank block's row index
    private int colBlank = -1; // blank block's column index

    // construct a board from an n-by-n array of blocks
    // (where blocks[i][j] = block in row i, column j)
    public Board(int[][] blocks) {
        this.n = blocks.length;

        // store a deep defence copy of the external mutable object "blocks"
        this.tiles = blocks.clone();
        for (int i = 0; i < blocks.length; i++) {
            this.tiles[i] = blocks[i].clone();
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (blocks[i][j] == 0) {
                    this.rowBlank = i;
                    this.colBlank = j;
                    break;
                }
            }
        }
        if (this.rowBlank == -1)
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
                if (!(i == n-1 && j == n-1) && (tiles[i][j] != i*n + j + 1))
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
                int v = tiles[i][j];    // current block value
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
        int k = 1;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i == n-1 && j == n-1) {
                    k = 0;
                    if (tiles[i][j] != k)
                        return false;
                }
                else {
                    if (tiles[i][j] != k)
                        return false;
                }
                k++;
            }
        }
        return true;
    }

    // a board that is obtained by exchanging any pair of blocks
    public Board twin() {
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
                        if (tiles[i][j] != 0) {
                            i2 = i;
                            j2 = j;
                            secondSelected = true;
                        }
                    }
                }
                else {          // select the first
                    if (tiles[i][j] != 0) {
                        i1 = i;
                        j1 = j;
                        firstSelected = true;
                    }
                    // else: continue checking next position
                }
            }
        }
        assert firstSelected && secondSelected;

        Board board = new Board(tiles);
        board.swapBlocks(i1, j1, i2, j2);
        return board;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (this == y) return true;
        if (y == null) return false;
        if (this.getClass() != y.getClass()) return false;
        Board that = (Board) y;
        if (this.n != that.n)
            return false;
        if (!java.util.Arrays.deepEquals(this.tiles, that.tiles))
            return false;
        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        int[] nextPos = null;
        ResizingArrayQueue<Board> queue = new ResizingArrayQueue<Board>();
        for (int cnt = 0; cnt < 4; cnt++) { // check four directions
            if (cnt == 0) {
                nextPos = getLeft(rowBlank, colBlank);
                if (nextPos == null)
                    continue;
            }
            if (cnt == 1) {
                nextPos = getRight(rowBlank, colBlank);
                if (nextPos == null)
                    continue;
            }
            if (cnt == 2) {
                nextPos = getUp(rowBlank, colBlank);
                if (nextPos == null)
                    continue;
            }
            if (cnt == 3) {
                nextPos = getDown(rowBlank, colBlank);
                if (nextPos == null)
                    continue;
            }

            if (nextPos != null) {
                Board board = new Board(tiles);
                board.moveBlankTo(nextPos[0], nextPos[1]);
                queue.enqueue(board);
            }
        }

        return queue;
    }

    // swap tile (i1, j1) and tile (i2, j2)
    // note: one of them may be a blank tile, but this method does not update
    // the blank tile position! It is only used for the block movement methods.
    private void swap(int i1, int j1, int i2, int j2) {
        int t = tiles[i2][j2];
        tiles[i2][j2] = tiles[i1][j1];
        tiles[i1][j1] = t;
    }

    // swap non-blank blocks
    private void swapBlocks(int i1, int j1, int i2, int j2) {
        swap(i1, j1, i2, j2);
    }

    private void moveBlankTo(int row, int col) {
        swap(rowBlank, colBlank, row, col);
        rowBlank = row;
        colBlank = col;
    }

    // get the coordination of the left element of the current "pos"
    private int[] getLeft(int row, int col) {
        if (row == 0)
            return null;
        else
            return new int[] { row - 1, col };
    }
    private int[] getUp(int row, int col) {
        if (col == 0)
            return null;
        else
            return new int[] { row, col - 1 };
    }
    private int[] getDown(int row, int col) {
        if (col == n - 1)
            return null;
        else
            return new int[] { row, col + 1 };
    }
    private int[] getRight(int row, int col) {
        if (row == n - 1)
            return null;
        else
            return new int[] { row + 1, col };
    }

    // string representation of this board
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(String.format("%d\n", n));
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++) {
                s.append(String.format("%2d", tiles[i][j]));
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
        StdOut.println("blank position: " + initial.rowBlank + " " + initial.colBlank);
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
