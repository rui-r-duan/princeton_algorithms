import java.awt.Color;
import edu.princeton.cs.algs4.Picture;
import edu.princeton.cs.algs4.StdOut;

public class SeamCarver {
    private Picture pic;
    private SeamFinder seamV;
    private SeamFinder seamH;

    private static final boolean VERTICAL = true;
    private static final boolean HORIZONTAL = false;
    /**
     * create a seam carver object based on the given picture
     */
    public SeamCarver(Picture picture) {
        pic = new Picture(picture);
        int width = pic.width();
        int height = pic.height();
        final int n = width * height;
        Double[][] energyArray = new Double[height][width]; // row major order
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                computeEnergy(x, y, energyArray);
            }
        }
        seamV = new SeamFinder(energyArray, VERTICAL);
        seamH = new SeamFinder(energyArray, HORIZONTAL);
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
        return pic.width();
    }

    /**
     * height of current picture
     */
    public int height() {
        return pic.height();
    }

    // column x, row y
    private void computeEnergy(int x, int y, Double[][] energyArray) {
        if (x == 0 || x == pic.width() - 1
            || y == 0 || y == pic.height() - 1) {
            energyArray[y][x] = 1000.0;
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

        energyArray[y][x] = Math.sqrt(dxsq + dysq);
    }

    /**
     * energy of pixel at column x and row y
     */
    public double energy(int x, int y) {
        return seamV.energy(x, y);
    }

    /**
     * sequence of indices for horizontal seam
     */
    public int[] findHorizontalSeam() {
        return seamH.findVerticalSeam();
    }

    /**
     * sequence of indices for vertical seam
     */
    public int[] findVerticalSeam() {
        return seamV.findVerticalSeam();
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

        StdOut.println("Finding a vertical seam...");
        int[] path = sc.findVerticalSeam();
        StdOut.println("Done.");
        StdOut.println("Vertial seam:");
        for (int i = 0; i < path.length; i++) {
            StdOut.print(path[i]);
            if (i == path.length - 1)
                StdOut.println();
            else
                StdOut.print(" ");
        }

        StdOut.println();
        StdOut.println("Finding a horizontal seam...");
        path = sc.findHorizontalSeam();
        StdOut.println("Done.");
        StdOut.println("Horizontal seam:");
        for (int i = 0; i < path.length; i++) {
            StdOut.print(path[i]);
            if (i == path.length - 1)
                StdOut.println();
            else
                StdOut.print(" ");
        }
    }
}
