/******************************************************************************
 *  Compilation:  javac Interval1D.java
 *  Execution:    java Interval1D
 *
 *  Interval data type with integer coordinates.
 *
 ******************************************************************************/


public class Interval1D implements Comparable<Interval1D> {
    public final int min;  // min endpoint
    public final int max;  // max endpoint

    // precondition: min <= max
    public Interval1D(int min, int max) {
        if (min <= max) {
            this.min = min;
            this.max = max;
        }
        else throw new RuntimeException("Illegal interval");
    }

    // does this interval intersect that one?
    public boolean intersects(Interval1D that) {
        if (that.max < this.min) return false;
        if (this.max < that.min) return false;
        return true;
    }

    // does this interval a intersect b?
    public boolean contains(int x) {
        return (min <= x) && (x <= max);
    }

    public int compareTo(Interval1D that) {
        if      (this.min < that.min) return -1;
        else if (this.min > that.min) return +1;
        else if (this.max < that.max) return -1;
        else if (this.max > that.max) return +1;
        else                          return  0;
    }

    public String toString() {
        return "[" + min + ", " + max + "]";
    }




    // test client
    public static void main(String[] args) {
        Interval1D a = new Interval1D(15, 20);
        Interval1D b = new Interval1D(25, 30);
        Interval1D c = new Interval1D(10, 40);
        Interval1D d = new Interval1D(40, 50);

        StdOut.println("a = " + a);
        StdOut.println("b = " + b);
        StdOut.println("c = " + c);
        StdOut.println("d = " + d);

        StdOut.println("b intersects a = " + b.intersects(a));
        StdOut.println("a intersects b = " + a.intersects(b));
        StdOut.println("a intersects c = " + a.intersects(c));
        StdOut.println("a intersects d = " + a.intersects(d));
        StdOut.println("b intersects c = " + b.intersects(c));
        StdOut.println("b intersects d = " + b.intersects(d));
        StdOut.println("c intersects d = " + c.intersects(d));

    }

}
