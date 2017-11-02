/*******************************************************************************
 * LongMath provides overflow checking when it performs the following arithmetic
 * operations:
 *     plus, subtract, multiply
 *
 * Note that division cannot cause overflow.
 *
 * Reference:
 * https://github.com/google/guava/blob/master/guava/src/com/google/common/math/LongMath.java
 *
 * @author Ryan Duan
 ******************************************************************************/

// (a ^ b) < 0 means a and b have different signs
// (a ^ b) >= 0 means a and b have the same sign

public final class LongMath {

    /**
     * @throws ArithmeticException if overflow
     */
    public static long checkedAdd(long a, long b) {
        long result = a + b;
        // if a and b have different signs, their sum cannot overflow;
        // or
        // if a and result have the same sign, it means result does not overflow
        checkNoOverflow((a ^ b) < 0 | (a ^ result) >= 0);
        return result;
    }

    /**
     * @throws ArithmeticException if overflow
     */
    public static long checkedSubtract(long a, long b) {
        long result = a - b;
        // if a and b have the same sign, their difference cannot overflow,
        // because their difference goes towards 0 instead of going towards
        // Long.MAX_VALUE or Long.MIN_VALUE.
        // e.g. (-3) - (-1000) = -3 + 1000 = 997 (closer to 0 than -1000)
        // e.g. 3 - 9999 = -9996 > (closer to 0 than 9999)
        //
        // or
        //
        // if a and result have the same sign, it means result does not overflow
        checkNoOverflow((a ^ b) >= 0 | (a ^ result) >= 0);
        return result;
    }

    /**
     * @throws ArithmeticException if overflow
     */
    public static long checkedMultiply(long a, long b) {
        long result = a * b;
        checkNoOverflow(a == 0 || result / a == b);
        return result;
    }

    /**
     * @throws ArithmeticException if condition is not met
     */
    static void checkNoOverflow(boolean condition) {
        if (!condition) {
            throw new ArithmeticException("overflow");
        }
    }

    public static void main(String[] args) {
        try {
            LongMath.checkedAdd(1, Long.MAX_VALUE);
        }
        catch (ArithmeticException e) {
            StdOut.println(e);
        }

        try {
            LongMath.checkedSubtract(Long.MIN_VALUE, 1);
        }
        catch (ArithmeticException e) {
            StdOut.println(e);
        }

        try {
            LongMath.checkedMultiply(Long.MAX_VALUE/2, 3);
        }
        catch (ArithmeticException e) {
            StdOut.println(e);
        }

        try {
            LongMath.checkedMultiply(Long.MIN_VALUE/2, 3);
        }
        catch (ArithmeticException e) {
            StdOut.println(e);
        }
    }
}
