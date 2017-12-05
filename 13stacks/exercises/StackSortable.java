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
