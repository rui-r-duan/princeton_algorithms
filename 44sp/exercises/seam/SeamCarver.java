import java.awt.Color;
import edu.princeton.cs.algs4.Picture;
import edu.princeton.cs.algs4.StdOut;

public class SeamCarver {
    private Picture pic;
    private int width;
    private int height;
    private double[] energyArray;
    private double[] distTo;
    private int[] edgeTo;

    /**
     * create a seam carver object based on the given picture
     */
    public SeamCarver(Picture picture) {
        pic = new Picture(picture);
        width = pic.width();
        height = pic.height();
        final int n = width * height;
        energyArray = new double[n];
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                computeEnergy(col, row);
            }
        }
        distTo = new double[n];
        edgeTo = new int[n];
        for (int i = 0; i < n; i++)
            distTo[i] = Double.POSITIVE_INFINITY;
        for (int i = 0; i < width; i++)
            distTo[i] = energyArray[i];
        for (int i = 0; i < n; i++)
            edgeTo[i] = -1;
        // debug
        // for (int i = 0; i < n; i++) {
        //     StdOut.printf("(%2d,%2d) ", index2D(i)[0], index2D(i)[1]);
        // }
        // StdOut.println();
    }

    // column x, row y
    private int index(int x, int y) {
        return y * width + x;
    }

    private int[] index2D(int i) {
        int y = i / width;      // row
        int x = i - y * width;  // column
        return new int[] { x, y };
    }

    /**
     * current picture
     */
    public Picture picture() {
        return pic;
    }

    /**
     * width of current picture
     */
    public int width() {
        return width;
    }

    /**
     * height of current picture
     */
    public int height() {
        return height;
    }

    // column x, row y
    private void computeEnergy(int x, int y) {
        if (x == 0 || x == width - 1
            || y == 0 || y == height - 1) {
            energyArray[index(x, y)] = 1000.0;
            return;
        }
        int[] xx = new int[] { x+1, x-1, x, x }; // right,left,down,up
        int[] yy = new int[] { y, y, y+1, y-1 }; // right,left,down,up
        final int N = xx.length;     // neighbors: right,left,down,up
        Color[] c = new Color[N];
        int[] r = new int[N];
        int[] g = new int[N];
        int[] b = new int[N];
        for (int i = 0; i < N; i++) {
            c[i] = pic.get(xx[i], yy[i]);
            r[i] = c[i].getRed();
            g[i] = c[i].getGreen();
            b[i] = c[i].getBlue();
        }

        double rxsq = Math.pow(r[1] - r[0], 2);
        double gxsq = Math.pow(g[1] - g[0], 2);
        double bxsq = Math.pow(b[1] - b[0], 2);
        double dxsq = rxsq + gxsq + bxsq;

        double rysq = Math.pow(r[3] - r[2], 2);
        double gysq = Math.pow(g[3] - g[2], 2);
        double bysq = Math.pow(b[3] - b[2], 2);
        double dysq = rysq + gysq + bysq;

        energyArray[index(x, y)] = Math.sqrt(dxsq + dysq);
    }

    /**
     * energy of pixel at column x and row y
     */
    public double energy(int x, int y) {
        return energyArray[index(x, y)];
    }

    // from upper left to bottom right, traverse in the diagonal lines in the
    // direction from upper right to bottom left
    // e.g.
    // (0,0) (1,0) (2,0)
    // (0,1) (1,1) (2,1)
    // should be traversed in this topological order:
    // (0,0) (1,0) (0,1) (2,0) (1,1) (2,1)
    private int[][] topologicalOrder() {
        final int p = width + height - 2;
        final int n = width * height;
        int[][] order = new int[n][2];
        int j = 0;
        for (int i = 0; i <= p; i++) {
            for (int x = i; x >= 0; x--) {
                int y = i - x;
                if (isValidIndex2D(x, y))
                    order[j++] = new int[] { x, y };
            }
        }
        return order;
    }

    private boolean isValidIndex2D(int x, int y) {
        return x >= 0 && x < width && y >= 0 && y < height;
    }

    private void printOrder(int[][] order) {
        for (int i = 0; i < order.length; i++) {
            StdOut.printf("(%2d,%2d)", order[i][0], order[i][1]);
            if ((i+1) % width == 0)
                StdOut.println();
            else
                StdOut.print(" ");
        }
    }

    private int[] getChildren(int x, int y) {
        if (y == height - 1)
            return new int[0];
        else
            if (x == 0)
                return new int[] { index(x, y+1), index(x+1, y+1) };
            else if (x == width - 1)
                return new int[] { index(x-1, y+1), index(x, y+1) };
            else
                return new int[] { index(x-1, y+1), index(x, y+1), index(x+1, y+1) };
    }

    private void relax(int x, int y) {
        int v = index(x, y);
        int[] w = getChildren(x, y);
        for (int i = 0; i < w.length; i++) {
            // StdOut.printf("distTo[v=%d]=%.2f, distTo[w=%d]=%.2f, energy[w=%d]=%.2f\n",
                          // v, distTo[v], w[i], distTo[w[i]], w[i], energyArray[w[i]]);
            if (distTo[w[i]] > distTo[v] + energyArray[w[i]]) {
                distTo[w[i]] = distTo[v] + energyArray[w[i]];
                edgeTo[w[i]] = v;
            }
        }
    }

    /**
     * sequence of indices for horizontal seam
     */
    public int[] findHorizontalSeam() {
        return null;
    }

    /**
     * sequence of indices for vertical seam
     */
    public int[] findVerticalSeam() {
        int[][] order = topologicalOrder();
        StdOut.println("topological order:");
        printOrder(order);

        for (int i = 0; i < order.length; i++) {
            relax(order[i][0], order[i][1]);
        }

        int[] resultIndex1D = new int[height];

        // find the smallest distance in the bottom line
        double min = distTo[distTo.length - 1];
        int minIndex = distTo.length - 1;
        int i;
        for (i = distTo.length - 2; i > distTo.length - (width - 1); i--) {
            if (distTo[i] < min) {
                min = distTo[i];
                minIndex = i;
            }
        }

        // trace back the shortest path
        int j = 0;
        for (int v = minIndex; v != -1; v = edgeTo[v]) {
            resultIndex1D[j++] = v;
        }

        // convert the 1D index to 2D index and only push them into the result
        // array in reverse order, only push the x coordinates
        int[] resultX = new int[resultIndex1D.length];
        for (i = 0, j = resultIndex1D.length - 1; j >= 0; j--) {
            resultX[i++] = index2D(resultIndex1D[j])[0]; // list of x coordinates
        }
        return resultX;
    }

    // for debug
    public double distTo(int x, int y) {
        return distTo[index(x, y)];
    }
    public int edgeTo(int x, int y) {
        return edgeTo[index(x, y)];
    }

    /**
     * remove horizontal seam from current picture
     */
    public void removeHorizontalSeam(int[] seam) {
    }

    /**
     * remove vertical seam from current picture
     */
    public void removeVerticalSeam(int[] seam) {
    }

    public static void main(String[] args) {
        Picture picture = new Picture(args[0]);
        StdOut.printf("image is %d pixels wide by %d pixels high.\n", picture.width(), picture.height());
        
        SeamCarver sc = new SeamCarver(picture);
        
        StdOut.printf("Printing energy calculated for each pixel.\n");        
        for (int row = 0; row < sc.height(); row++) {
            for (int col = 0; col < sc.width(); col++)
                StdOut.printf("%9.2f ", sc.energy(col, row));
            StdOut.println();
        }

        StdOut.print("findVerticalSeam...");
        int[] path = sc.findVerticalSeam();
        StdOut.println("done.");
        StdOut.println("distTo=");
        for (int row = 0; row < sc.height(); row++) {
            for (int col = 0; col < sc.width(); col++)
                StdOut.printf("%9.2f ", sc.distTo(col, row));
            StdOut.println();
        }
        StdOut.println("edgeTo=");
        for (int row = 0; row < sc.height(); row++) {
            for (int col = 0; col < sc.width(); col++)
                StdOut.printf("%9d ", sc.edgeTo(col, row));
            StdOut.println();
        }
        StdOut.println("path:");
        for (int i = 0; i < path.length; i++) {
            StdOut.print(path[i]);
            if (i == path.length - 1)
                StdOut.println();
            else
                StdOut.print(" ");
        }
    }
}
