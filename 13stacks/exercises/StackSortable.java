/*******************************************************************************
 * This program answers the question: given a permutation of integers 0 to n-1,
 * can a stack sort it in ascending order?
 *
 * Pattern 231 cannot be sorted by a single stack, according to Donald Knuth.
 * for a[i], a[j], a[k], where i < j < k,
 * if a[k] < a[i] < a[j], then it is pattern 231.
 *
 * This program can also sort the given permutation. If it is sortable, the
 * returned result is sorted, if it is not sortable, the returned result is not
 * sorted.
 *
 * Sample execution:
 * $ java-algs4 StackSortable < stack_permutable_in
 * (4 3 2 1 0 9 8 7 6 5) can be sorted by a stack? true
 * Trying to sort it ...:
 * (0 1 2 3 4 5 6 7 8 9)
 *
 * (4 6 8 7 5 3 2 9 0 1) can be sorted by a stack? false
 * Trying to sort it ...:
 * (4 6 2 3 5 7 8 0 1 9)
 *
 * (2 5 6 7 4 8 9 3 1 0) can be sorted by a stack? false
 * Trying to sort it ...:
 * (2 5 6 4 7 8 0 1 3 9)
 *
 * (1 2 3 4 5 6 9 8 7 0) can be sorted by a stack? false
 * Trying to sort it ...:
 * (1 2 3 4 5 6 0 7 8 9)
 *
 * (0 4 6 5 3 8 1 7 2 9) can be sorted by a stack? false
 * Trying to sort it ...:
 * (0 4 3 5 6 1 2 7 8 9)
 *
 * (1 4 7 9 8 6 5 3 0 2) can be sorted by a stack? false
 * Trying to sort it ...:
 * (1 4 7 0 2 3 5 6 8 9)
 *
 * (2 1 4 3 6 5 8 7 9 0) can be sorted by a stack? false
 * Trying to sort it ...:
 * (1 2 3 4 5 6 7 8 0 9)
 *
 * (2 0 1) can be sorted by a stack? true
 * Trying to sort it ...:
 * (0 1 2)
 *
 * (1 2 0) can be sorted by a stack? false
 * Trying to sort it ...:
 * (1 0 2)
 *
 * @author Ryan Duan <duanpanda@gmail.com>
 ******************************************************************************/
import edu.princeton.cs.algs4.Stack;

public class StackSortable {
    /**
     * Can a stack sort the given permutation sequence?
     *  runtime complexity: O(n^3), NP hard
     *  pattern 231 cannot be sorted by a single stack
     */
    public static boolean isSortable(int[] a) {
        for (int i = 0; i < a.length - 2; i++)
            for (int j = i+1; j < a.length - 1; j++)
                for (int k = j+1; k < a.length; k++)
                    if (a[k] < a[i] && a[i] < a[j])
                        return false;
        return true;
    }

    /**
     * Sort the given permutation sequence.
     * @return a new allocated sorted int array
     */
    public static int[] sort(int[] target) {
        if (target.length == 0)
            return null;

        int[] result = new int[target.length];
        int j = 0;              // index for result[]
        Stack<Integer> stack = new Stack<Integer>();

        for (int i = 0; i < target.length; i++) {
            while (!stack.isEmpty() && target[i] > stack.peek()) {
                result[j++] = stack.pop();
            }
            stack.push(target[i]);
        }
        while (!stack.isEmpty()) {
            result[j++] = stack.pop();
        }

        return result;
    }

    private static void printIntList(int[] a) {
        StdOut.print("(");
        int i;
        for (i = 0; i < a.length - 1; i++) {
            StdOut.print(a[i] + " ");
        }
        StdOut.print(a[i] + ")");
    }

    public static void main(String[] args) {
        while (!StdIn.isEmpty()) {
            int n = StdIn.readInt();
            int[] a = new int[n];
            for (int i = 0; i < n; i++) {
                a[i] = StdIn.readInt();
            }

            printIntList(a);
            boolean isSortable = isSortable(a);
            StdOut.println(" can be sorted by a stack? " + isSortable);

            StdOut.println("Trying to sort it ...:");
            int[] b = sort(a);
            printIntList(b);
            StdOut.print("\n\n");
        }
    }
}
