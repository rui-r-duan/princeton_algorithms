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
     * @throws IllegalArgumentException if x or y is outside its prescribed range
     *         or if the argument {@code picture} is null
     */
    public SeamCarver(Picture picture) {
        if (picture == null) throw new IllegalArgumentException("null argument");
        pic = new Picture(picture);
        update();
    }

    /*
     * @throws IllegalArgumentException if x or y is outside its prescribed range
     */
    private void update() {
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
        // prevent the client to mutate the internal picture
        return new Picture(pic);
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
     * @throws IllegalArgumentException if x or y is outside its prescribed range
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
     * @throws IllegalArgumentException if the argument {@code seam} is null
     */
    public void removeHorizontalSeam(int[] seam) {
        if (seam == null)
            throw new IllegalArgumentException("null argument");

        checkSeamValid(seam, HORIZONTAL);

        if (height() == 1)
            throw new IllegalArgumentException("height=1,cannot remove more");

        Picture np = new Picture(width(), height() - 1);
        for (int x = 0; x < width(); x++) {
            int y = 0;
            for (int y0 = 0; y0 < height(); y0++) {
                if (seam[x] != y0)
                    np.setRGB(x, y++, pic.getRGB(x, y0));
            }
        }
        pic = np;
        update();
    }

    /**
     * remove vertical seam from current picture
     * @throws IllegalArgumentException if the argument {@code seam} is null
     */
    public void removeVerticalSeam(int[] seam) {
        if (seam == null)
            throw new IllegalArgumentException("null argument");

        checkSeamValid(seam, VERTICAL);

        if (width() == 1)
            throw new IllegalArgumentException("width=1,cannot remove more");

        Picture np = new Picture(width() - 1, height());
        for (int y = 0; y < height(); y++) {
            int x = 0;
            for (int x0 = 0; x0 < width(); x0++) {
                if (seam[y] != x0)
                    np.setRGB(x++, y, pic.getRGB(x0, y));
            }
        }
        pic = np;
        update();
    }

    /*
     * @pre: seam != null
     */
    private void checkSeamValid(int[] seam, boolean direction) {
        assert seam != null;
        // check the length
        if (direction == VERTICAL) {
            if (seam.length != height())
                throw new IllegalArgumentException("invalid seam: bad length: " + seam.length);
        }
        else {
            if (seam.length != width())
                throw new IllegalArgumentException("invalid seam: bad length: " + seam.length);
        }

        // check whether every element is in the prescribed range
        if (direction == VERTICAL) {
            for (int i = 0; i < seam.length; i++)
                if (!xValid(seam[i]))
                    throw new IllegalArgumentException("invalid seam: bad range: " + seam[i]);
        }
        else {
            for (int i = 0; i < seam.length; i++)
                if (!yValid(seam[i]))
                    throw new IllegalArgumentException("invalid seam: bad range: " + seam[i]);
        }

        // check if two adjacent entries differ by more than 1
        for (int i = 0; i < seam.length - 1; i++) {
            if (Math.abs(seam[i+1] - seam[i]) > 1)
                throw new IllegalArgumentException("invalid seam: bad adjacent: " + seam[i] + ", " + seam[i+1]);
        }
    }

    private boolean xValid(int x) {
        return x >= 0 && x < width();
    }

    private boolean yValid(int y) {
        return y >= 0 && y < height();
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
        int[] seam = sc.findVerticalSeam();
        StdOut.println("Done.");
        StdOut.println("Vertial seam:");
        for (int i = 0; i < seam.length; i++) {
            StdOut.print(seam[i]);
            if (i == seam.length - 1)
                StdOut.println();
            else
                StdOut.print(" ");
        }

        StdOut.println();
        StdOut.println("Finding a horizontal seam...");
        seam = sc.findHorizontalSeam();
        StdOut.println("Done.");
        StdOut.println("Horizontal seam:");
        for (int i = 0; i < seam.length; i++) {
            StdOut.print(seam[i]);
            if (i == seam.length - 1)
                StdOut.println();
            else
                StdOut.print(" ");
        }

        StdOut.println();
        StdOut.println("Check invalid seam");
        try {
            int[] badSeam1 = new int[0]; // bad length
            sc.removeHorizontalSeam(badSeam1);
        }
        catch (IllegalArgumentException e) {
            StdOut.println(e);
        }

        try {
            int[] badSeam2 = new int[seam.length];
            System.arraycopy(badSeam2, 0, seam, 0, seam.length);
            badSeam2[0] = sc.height(); // bad range
            sc.removeHorizontalSeam(badSeam2);
        }
        catch (IllegalArgumentException e) {
            StdOut.println(e);
        }

        try {
            int[] badSeam3 = new int[seam.length];
            System.arraycopy(badSeam3, 0, seam, 0, seam.length);
            badSeam3[0] = badSeam3[1] + 2; // bad adjacent
            sc.removeHorizontalSeam(badSeam3);
        }
        catch (IllegalArgumentException e) {
            StdOut.println(e);
        }
    }
}
