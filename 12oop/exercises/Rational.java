/*******************************************************************************
 *
 * Immutable data type for rational numbers.
 *
 * It is better to raise an Exception than using an assertion to check
 * overflow.
 *
 * Reference: Google's Guava Library
 * https://github.com/google/guava/blob/master/guava/src/com/google/common/math/MathPreconditions.java
 * https://github.com/google/guava/blob/master/guava/src/com/google/common/math/LongMath.java
 *
 * In C#, we can use "checked" keyword on a code block to check the arithmetic
 * overflow.
 *
 ******************************************************************************/

public class Rational {
    private long n;             // numerator (it can > 0 or < 0 or == 0)
    private long d;             // denominator (> 0)

    /**
     * Make an instance of class Rational.
     *
     * @param numerator it can be any integer
     * @param denominator it can be any integer but 0 will cause the constructor
     *        to raise {@code IllegalArgumentException}, because 0 is an invalid
     *        denominator
     * @throws IellegalArgumentException when {@code denominator} is zero
     */
    public Rational(int numerator, int denominator) {
        this((long)numerator, (long)denominator);
    }

    private Rational(long numerator, long denominator) {
        if (denominator == 0) {
            throw new IllegalArgumentException("denominator cannot be zero");
        }
        boolean isNegative = ((numerator ^ denominator) < 0);
        n = Math.abs(numerator);
        d = Math.abs(denominator);
        long gcd = gcd(n, d);
        n /= gcd;
        d /= gcd;
        // put the negative information in the numerator
        if (isNegative) {
            n = -n;
        }
    }

    public Rational plus(Rational that) {
        long nn = LongMath.checkedAdd(LongMath.checkedMultiply( n, that.d ),
                                      LongMath.checkedMultiply( that.n, d ));
        long dd = LongMath.checkedMultiply(d, that.d);
        return new Rational(nn, dd);
    }

    public Rational minus(Rational that) {
        long nn = LongMath.checkedSubtract(LongMath.checkedMultiply( n, that.d ),
                                           LongMath.checkedMultiply( that.n, d ));
        long dd = LongMath.checkedMultiply(d, that.d);
        return new Rational(nn, dd);
    }

    public Rational times(Rational that) {
        long nn = LongMath.checkedMultiply(n, that.n);
        long dd = LongMath.checkedMultiply(d, that.d);
        return new Rational(nn, dd);
    }

    public Rational dividedBy(Rational that) {
        long nn = LongMath.checkedMultiply(n, that.d);
        long dd = LongMath.checkedMultiply(d, that.n);
        return new Rational(nn, dd);
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) return true;
        if (that == null) return false;
        if (this.getClass() != that.getClass()) return false;
        Rational t = (Rational)that;
        return (n == t.n) && (d == t.d);
    }

    @Override
    public String toString() {
        if (d == 1)
            return n + "";
        else
            return n + "/" + d;
    }

    /**
     * Euclid's algorithm to compute the Greates Common Divisor of
     * two nonnegative integers p and q.
     *
     * If {@code p == 0} && {@code q == 0}, then return 0.
     */
    public static long gcd(long p, long q) {
        // if (q == 0) return p;
        // long r = p % q;
        // return gcd(q, r);
        if (p == 0) {
            return q;
        }
        else if (q == 0) {
            return p;
        }
        long r;
        while (q != 0) {
            r = p % q;
            p = q;
            q = r;
        }
        return p;
    }

    public static void main(String[] args) {
        Rational r0 = new Rational(0, 127); // 0
        Rational r1 = new Rational(3, 6);   // 1/2
        Rational r2 = new Rational(-3, 6);  // -1/2
        Rational r3 = new Rational(2, -6);  // -1/3
        Rational r4 = new Rational(1, 2);   // 1/2
        Rational r5 = new Rational(10, 24); // 5/12
        Rational r6 = new Rational(1, -12); // -1/12
        StdOut.println(r0);
        StdOut.println(r1);
        StdOut.println(r2);
        StdOut.println(r3);
        // StdOut.println(new Rational(0, 0));

        StdOut.println(r1 + ".plus(" + r2 + ")=" + r1.plus(r2));
        StdOut.println(r1 + ".minus(" + r3 + ")=" + r1.minus(r3));
        StdOut.println(r1 + ".times(" + r3 + ")=" + r2.times(r3));
        StdOut.println(r3 + ".times(" + r2 + ")=" + r3.dividedBy(r2));
        StdOut.println(r1 + ".plus(" + r4 + ")=" + r1.plus(r4));
        StdOut.println(r5 + ".plus(" + r6 + ")=" + r5.plus(r6)); // 1/3

        StdOut.println(r1 + ".equals(" + r4 + ")=" + r1.equals(r4));
        StdOut.println(r2 + ".equals(" + r0 + ".minus(" + r1 + "))="
                       + r2.equals(r0.minus(r1)));
    }
}
