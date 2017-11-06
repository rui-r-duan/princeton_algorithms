import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int n;
    // n by n grid. blocked site: 0, open site: 1, full site: 2
    private int[][] grid;
    private WeightedQuickUnionUF wquf; // weighted quick union find algorithm
    private int numberOfOpenSites;
    private int top;            // index of the virtual site 'top' in WQUF
    private int bottom;         // index of the virtual site 'bottom' in WQUF
    

    /**
     * create n-by-n grid, with all sites blocked
     */
    public Percolation(int n) {
        this.n = n;
        grid = new int[n][n];

        // With two virtual nodes: top and bottom,
        // to reduce the number of percolates() query.
        //
        // indice 0 to n-1 in WQUF   <==> grid[0][0] to grid[n-1][n-1]
        // index (i*n+j) in WQUF <==> grid[i][j]
        // index (n*n+2-2) in WQUF         <==> virtual site 'top'
        // index (n*n+2-1) in WQUF       <==> virtual site 'bottom'
        wquf = new WeightedQuickUnionUF(n * n + 2);
        top = n * n;
        bottom = top + 1;

        numberOfOpenSites = 0;
    }

    /**
     * open site (row, col) if it is not open already
     */
    public void open(int row, int col) {
        if (!siteExists(row, col)) {
            throw new IllegalArgumentException("illegal index");
        }
        grid[row-1][col-1] = 1;
        numberOfOpenSites++;
        connectNeighbors(row, col);
    }

    /**
     * is site (row, col) open?
     */
    public boolean isOpen(int row, int col) {
        if (!siteExists(row, col)) {
            throw new IllegalArgumentException("illegal index");
        }
        return grid[row-1][col-1] == 1;
    }

    /**
     * is site (row, col) full?
     */
    public boolean isFull(int row, int col) {
        if (!siteExists(row, col)) {
            throw new IllegalArgumentException("illegal index");
        }
        int currentSite = getUFIndex(new int[] { row, col });
        if (isOpen(row, col)
                && wquf.connected(top, currentSite)) {
            grid[row-1][col-1] = 2;
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * number of open sites
     */
    public int numberOfOpenSites() {
        return numberOfOpenSites;
    }

    /**
     * does the system percolate?
     */
    public boolean percolates() {
        return wquf.connected(top, bottom);
    }

    // @pre: the current site (row,col) must be open
    private void connectNeighbors(int row, int col) {
        assert isOpen(row, col);
        int[] currentSite = new int[] { row, col };
        if (row == 1) {
            wquf.union(getUFIndex(currentSite), top);
        }
        if (row == n) {
            wquf.union(getUFIndex(currentSite), bottom);
        }
        int[] upIndex = new int[] { row-1, col };
        int[] downIndex = new int[] { row+1, col };
        int[] leftIndex = new int[] { row, col-1 };
        int[] rightIndex = new int[] { row, col+1 };
        int[][] neighbors = { upIndex, downIndex, leftIndex, rightIndex };

        for (int[] s : neighbors) {
            if (siteExists(s[0], s[1]) && isOpen(s[0], s[1])) {
                connect(currentSite, s);
            }
        }
    }
    
    private boolean siteExists(int r, int c) {
        return (r > 0) && (r <= n) && (c > 0) && (c <= n);
    }
    
    private void connect(int[] s, int[] t) {
        StdOut.println("connect [" + s[0] + "," + s[1] + "] and ["
                           + t[0] + "," + t[1] + "]");
        int si = getUFIndex(s);
        int ti = getUFIndex(t);
        StdOut.println("union(" + si + "," + ti + ")");
        wquf.union(si, ti);
    }
    
    private int getUFIndex(int[] siteIndex) {
        int r = siteIndex[0] - 1;
        int c = siteIndex[1] - 1;
        return r * n + c;
    }
    
    // blocked site: '*'
    // open site:    'o'
    // full site:    '+'
    private void print() {
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                if (isFull(i, j)) {
                    StdOut.print("+ ");
                }
                else if (isOpen(i, j)) {
                    StdOut.print("o ");
                }
                else {
                    StdOut.print("* ");
                }
            }
            StdOut.print("\n");
        }
    }
    
    /**
     * test client
     */
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        StdOut.println("Grid One:");
        Percolation percolation = new Percolation(n);
        for (int i = 1; i <= n; i++) {
            percolation.open(i, i);
        }
        percolation.print();
        StdOut.println(percolation.percolates());
        StdOut.println("numberOfOpenSites=" + percolation.numberOfOpenSites());
        
        StdOut.println();
        StdOut.println("Grid Two:");
        percolation = new Percolation(n);
        for (int i = 1; i < n; i++) {
            percolation.open(i, 2);
        }
        percolation.open(1, 1);
        percolation.open(5, 5);
        percolation.print();
        StdOut.println(percolation.percolates());
        StdOut.println("numberOfOpenSites=" + percolation.numberOfOpenSites());
    }
}
