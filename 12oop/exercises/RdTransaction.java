/******************************************************************************
 * Immutable transaction data type
 *****************************************************************************/

public class RdTransaction implements Comparable<RdTransaction> {
    private final String who;
    private final SmartDate when;
    private final double amount;

    public RdTransaction(String who, SmartDate when, double amount) {
        if (Double.isNaN(amount) || Double.isInfinite(amount))
            throw new IllegalArgumentException("Amount cannot be NaN or infinite");
        this.who = who;
        this.when = when;     // alias
        this.amount = amount; // alias
    }

    public RdTransaction(String transaction) {
        String[] a = transaction.split("\\s+");
        who = a[0];
        when = new SmartDate(a[1]);
        amount = Double.parseDouble(a[2]);
        if (Double.isNaN(amount) || Double.isInfinite(amount))
            throw new IllegalArgumentException("Amount cannot be NaN or infinite");
    }

    public String who() {
        return who;
    }

    public SmartDate when() {
        return when;
    }

    public double amount() {
        return amount;
    }

    @Override
    public String toString() {
        // "%-10s" indicates left-alighed
        return String.format("%-10s %10s %10.2f", who, when, amount);
    }

    /**
     * Compares two transactions by amount.
     *
     * @param  that the other transaction
     * @return a negative integer if this is less than that;
     *         a positive integer if this is greater than that;
     *         zero if this is equal than that
     */
    @Override
    public int compareTo(RdTransaction that) {
        // The following "minus" solution does not work correctly for
        //    (-0.0, 0.0), (!NaN, NaN) ==> should return -1 (less than)
        //    (0.0, -0.0), (NaN, !NaN) ==> should return +1 (greater than)
        // return (int)(this.amount - that.amount);
        return Double.compare(this.amount, that.amount);
    }

    @Override
    public boolean equals(Object x) {
        if (this == x) return true;
        if (x == null) return false;
        if (this.getClass() != x.getClass()) return false;
        RdTransaction that = (RdTransaction)x;
        return (this.amount == that.amount) && (this.who.equals(that.who))
            && (this.when.equals(that.when));
    }

    public static void main(String[] args) {
        RdTransaction[] a = new RdTransaction[5];
        a[0] = new RdTransaction("Turing   6/17/1990  644.08");
        a[1] = new RdTransaction("Tarjan   3/26/2002 4121.85");
        a[2] = new RdTransaction("Knuth    6/14/1999  288.34");
        a[3] = new RdTransaction("Dijkstra 8/22/2007 362678.40");
        a[4] = new RdTransaction("Turing   6/17/1990  644.08");
        for (RdTransaction t : a) {
            StdOut.println(t);
        }

        // test equality
        StdOut.println("a[0]:[" + a[0] + "] and a[1]:[" + a[1] + "] are "
                       + (a[0].equals(a[1]) ? "equaled" : "different"));
        StdOut.println("a[0]:[" + a[0] + "] and a[4]:[" + a[4] + "] are "
                       + (a[0].equals(a[4]) ? "equaled" : "different"));
    }
}
