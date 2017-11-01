/******************************************************************************
 * Immutable transaction data type
 *****************************************************************************/

public class RdTransaction implements Comparable<RdTransaction> {
    private final String who;
    private final SmartDate when;
    private final double amount;
    
    public RdTransaction(String who, SmartDate when, double amount) {
        this.who = who;
        this.when = when;     // alias
        this.amount = amount; // alias
    }
    
    public RdTransaction(String transaction) {
        String[] a = transaction.split(" ");
        who = a[0];
        when = new SmartDate(a[1]);
        amount = Double.parseDouble(a[2]);
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
        return "[" + who + ", " + when + ", " + amount + "]";
    }
    
    /**
     * Compares two transactions by amount.
     * 
     * @param  that the other transaction
     * @return 
     */
    @Override
    public int compareTo(RdTransaction that) {
        return (int)(this.amount - that.amount);
    }

    public static void main(String[] args) {
        RdTransaction[] a = new RdTransaction[4];
        a[0] = new RdTransaction("Turing 6/17/1990 644.08");
        a[1] = new RdTransaction("Tarjan 3/26/2002 4121.85");
        a[2] = new RdTransaction("Knuth 6/14/1999 288.34");
        a[3] = new RdTransaction("Dijkstra 8/22/2007 2678.40");
        for (RdTransaction t : a) {
            StdOut.println(t);
        }
    }
}