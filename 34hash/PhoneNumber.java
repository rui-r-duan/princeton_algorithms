/******************************************************************************
 *  Compilation:  javac PhoneNumber.java
 *  Execution:    java PhoneNumber
 *  Dependencies:
 *
 *  Immutable data type for US phone numbers.
 *
 ******************************************************************************/

import java.util.HashSet;
import edu.princeton.cs.algs4.StdOut;

/**
 *  The {@code PhoneNumber} class is an immutable data type to encapsulate a
 *  U.S. phone number with an area @code (3 digits), exchange (3 digits),
 *  and extension (4 digits).
 *  <p>
 *  For additional documentation,
 *  see <a href="https://algs4.cs.princeton.edu/12oop">Section 1.2</a> of
 *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *
 *  @author Robert Sedgewick
 *  @author Kevin Wayne
 */
public final class PhoneNumber {
    private final int area;   // area @code (3 digits)
    private final int exch;   // exchange  (3 digits)
    private final int ext;    // extension (4 digits)

    /**
     * Initializes a new phone number.
     *
     * @param  area the area @code (3 digits)
     * @param  exch the exchange (3 digits)
     * @param  ext  the extension (4 digits)
     */
    public PhoneNumber(int area, int exch, int ext) {
        this.area = area;
        this.exch = exch;
        this.ext  = ext;
    }

    /**
     * Compares this phone number to the specified phone number.
     *
     * @param  other the other phone number
     * @return {@code true} if this phone number equals {@code other};
     *         {@code false} otherwise
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) return true;
        if (other == null) return false;
        if (other.getClass() != this.getClass()) return false;
        PhoneNumber that = (PhoneNumber) other;
        return (this.area == that.area) && (this.exch == that.exch) && (this.ext == that.ext);
    }

    /**
     * Returns a string representation of this phone number.
     *
     * @return a string representation of this phone number
     */
    @Override
    public String toString() {
        // 0 for padding with digits with leading 0s
        return String.format("(%03d) %03d-%04d", area, exch, ext);
    }

    /**
     * Returns an integer hash code for this phone number.
     *
     * @return an integer hash code for this phone number
     */
    @Override
    public int hashCode() {
        return 31 * (area + 31 * exch) + ext;
    }

    /**
     * Unit tests the {@code PhoneNumber} data type.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        PhoneNumber a = new PhoneNumber(609, 258, 4455);
        PhoneNumber b = new PhoneNumber(609, 876, 5309);
        PhoneNumber c = new PhoneNumber(609, 555, 5309);
        PhoneNumber d = new PhoneNumber(215, 876, 5309);
        PhoneNumber e = new PhoneNumber(609, 876, 5309);
        StdOut.println("a = " + a);
        StdOut.println("b = " + b);
        StdOut.println("c = " + c);
        StdOut.println("d = " + d);
        StdOut.println("e = " + e);

        HashSet<PhoneNumber> set = new HashSet<PhoneNumber>();
        set.add(a);
        set.add(b);
        set.add(c);
        StdOut.println("Added a, b, and c");
        StdOut.println("contains a:  " + set.contains(a));
        StdOut.println("contains b:  " + set.contains(b));
        StdOut.println("contains c:  " + set.contains(c));
        StdOut.println("contains d:  " + set.contains(d));
        StdOut.println("contains e:  " + set.contains(e));
        StdOut.println("b == e:      " + (b == e));
        StdOut.println("b.equals(e): " + (b.equals(e)));
    }
}
