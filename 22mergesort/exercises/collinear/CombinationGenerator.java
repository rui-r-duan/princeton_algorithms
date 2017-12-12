import java.util.NoSuchElementException;

public class CombinationGenerator {
    private final int n;

    // array of length c that holds the c selected elements
    // for example:
    // C(5, 4) choose 4 items out of 5 items
    //--------------------
    //         i  0 1 2 3
    // init a[i]  0 1 2 3
    //            ...
    //      a[i]  0 1 3 4
    // next a[i]  0 2 3 4
    // next a[i]  1 2 3 4
    //--------------------
    //
    // a[0] is treated as the highest digit, and a[c-1] is treated as the
    // lowest digit a[i].
    //
    // The next combination calculation is similar to integer addition in which
    // the lowest digit increament one, if it overflows, the carry is added to
    // the higher digit. This process ends when one digit does not overflow
    // after adding the carry or when the highest digit a[0] overflows.
    private int a[];
    private boolean hasNext;

    /**
     * Choose c elements from n elements
     * @param n total number of elements
     * @param c the number of elements that is selected
     * @throws IllegalArgumentException if n < c
     */
    public CombinationGenerator(int n, int c) {
        if (n < c) {
            throw new IllegalArgumentException("n cannot be less than c");
        }
        this.a = new int[c];
        for (int i = 0; i < c; i++) {
            this.a[i] = i;
        }
        this.n = n;
        hasNext = true;
    }

    /**
     * If there is more combinations, return true, otherwise, return false.
     */
    public boolean hasNext() {
        return hasNext;
    }

    /**
     * Get the next combination
     * @return int array of the combination of length c, e.g. [2,4,5,8]
     * @throws NoSuchElementException if hasNext is false
     */
    public int[] next() {
        if (!hasNext) {
            throw new NoSuchElementException("no more combination");
        }

        int[] ret = a.clone();  // a calculated copy of combination for return

        // calculate the next combination for the next call of next()
        boolean carry = true; // the initial carry will be added on a[c-1]
        int c = a.length;
        int i;
        for (i = c-1; i >= 0; --i) {
            // add carry
            if (carry) {
                a[i]++;
            }

            if (digitOverflow(i)) {
                carry = true;
                // In this case, the highest digit a[0] overflows after
                // adding the carry, which means the whole overflows,
                // which indicates there is no more combination.
                if (i == 0) {
                    break;
                }
                // Otherwise, go on the loop
            }
            else {
                // In this case, one digit a[i] (where i >= 0) does not
                // overflow after adding the carry, it should terminate the
                // loop with i stopping at this digit.
                carry = false;
                break;
            }
        }
        // The above loop terminates only by a 'break' with i >= 0 and with
        // carry being either true or false.
        if (carry) {
            // overflow for the whole, has no next
            hasNext = false;
        }
        else {
            // In this case, the carry addition is complete, the values of the
            // digits that are after the terminating digit a[i] should be
            // re-calculated.
            ++i;
            while (i <= c-1) {
                a[i] = a[i-1] + 1;
                ++i;
            }
        }

        // return the currently calculated copy of the combination
        return ret;
    }

    // if digit i is overflow
    private boolean digitOverflow(int i) {
        return (a[i] > n-a.length+i); // n-c+i
    }

    private static void printIntArray(int[] a) {
        for (int i = 0; i < a.length; i++) {
            if (i != a.length - 1) {
                System.out.print(a[i] + " ");
            }
            else {
                System.out.println(a[i]);
            }
        }
    }

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int c = Integer.parseInt(args[1]);
        int[] a;

        System.out.println("hasNext() then next()");
        CombinationGenerator g = new CombinationGenerator(n, c);
        int count = 0;
        while (g.hasNext()) {
            a = g.next();
            printIntArray(a);
            count++;
        }
        System.out.println("total = " + count);

        System.out.println("only use next(), should throw an exception at the end");
        g = new CombinationGenerator(n, c);
        while (true) {
            a = g.next();
            printIntArray(a);
        }
    }
}
