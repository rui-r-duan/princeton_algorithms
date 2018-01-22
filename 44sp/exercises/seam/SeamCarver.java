import java.awt.Color;
import edu.princeton.cs.algs4.Picture;
import edu.princeton.cs.algs4.StdOut;

public class SeamCarver {
    private Picture pic;

    /**
     * create a seam carver object based on the given picture
     */
    public SeamCarver(Picture picture) {
        pic = new Picture(picture);
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

    /**
     * energy of pixel at column x and row y
     */
    public double energy(int x, int y) {
        Color c1 = pic.get(x+1, y);
        int r1 = c1.getRed();
        int g1 = c1.getGreen();
        int b1 = c1.getBlue();

        Color c2 = pic.get(x-1, y);
        int r2 = c2.getRed();
        int g2 = c2.getGreen();
        int b2 = c2.getBlue();

        Color c3 = pic.get(x, y+1);
        int r3 = c3.getRed();
        int g3 = c3.getGreen();
        int b3 = c3.getBlue();

        Color c4 = pic.get(x, y-1);
        int r4 = c4.getRed();
        int g4 = c4.getGreen();
        int b4 = c4.getBlue();

        double rxsq = Math.pow(r2 - r1, 2);
        double gxsq = Math.pow(g2 - g1, 2);
        double bxsq = Math.pow(b2 - b1, 2);
        double dxsq = rxsq + gxsq + bxsq;

        double rysq = Math.pow(r4 - r3, 2);
        double gysq = Math.pow(g4 - g3, 2);
        double bysq = Math.pow(b4 - b3, 2);
        double dysq = rysq + gysq + bysq;

        return Math.sqrt(dxsq + dysq);
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
        return null;
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
        Picture pic = new Picture(args[0]);
        SeamCarver seam = new SeamCarver(pic);
        StdOut.println("width=" + seam.width());
        StdOut.println("height=" + seam.height());
        StdOut.printf("energy(1,2)=%.2f\n", seam.energy(1, 2));
        StdOut.printf("energy(1,1)=%.2f\n", seam.energy(1, 1));
    }
}
