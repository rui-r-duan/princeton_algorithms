import edu.princeton.cs.algs4.Counter;

/*
 * % java-algs4 BinarySearchCnt <key> <n>
 * % <n integers>
 * output:
 * <key> is found at <index>. <cnt> elements were searched.
 * 
 * % java-algs4 BinarySearchCnt <key> <n>
 * % <n integers>
 * output:
 * <key> is NOT found. <cnt> elements were searched.
 */

public class BinarySearchCnt {

    /**
     * This class should not be instantiated.
     */
    private BinarySearchCnt() { }

    /**
     * Returns the index of the specified key in the specified array.
     *
     * @param  a the array of integers, must be sorted in ascending order
     * @param  key the search key
     * @return index of key in array {@code a} if present; {@code -1} otherwise
     */
    public static int indexOf(int[] a, int key, Counter counter) {
        int lo = 0;
        int hi = a.length - 1;
        while (lo <= hi) {
            // Key is in a[lo..hi] or not present.
            counter.increment();
            int mid = lo + (hi - lo) / 2;
            if      (key < a[mid]) hi = mid - 1;
            else if (key > a[mid]) lo = mid + 1;
            else return mid;
        }
        return -1;
    }

    public static void main(String[] args) {
        int key = Integer.parseInt(args[0]);
        int n = Integer.parseInt(args[1]);
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = StdIn.readInt();
        }
        
        Counter cntr = new Counter("searchedElements");
        int index = BinarySearchCnt.indexOf(a, key, cntr);
        if (index != -1) {
            StdOut.println(key + " is found at " + index + ". " +
                           cntr.tally() + " elements were searched.");
        }
        else {
            StdOut.println(key + " is NOT found. " +
                           cntr.tally() + " elements were searched.");
        }
    }
}
