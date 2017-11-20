public class FindMinimumInRotatedArray {

    public static int findMinimumIndex(int[] b) {
        int n = b.length;
        if (n <= 1) return 0;   // aray of length 0 or 1
        if (b[0] < b[n-1]) return 0; // already sorted

        // invariant b[lo] > b[hi]
        int lo = 0;
        int hi = n-1;
        while (true) {
            if (hi == lo+1) return hi;
            int mid = lo + (hi - lo) / 2;
            if      (b[mid] < b[hi]) hi = mid; // min index is in (lo...mid]
            else if (b[mid] > b[hi]) lo = mid; // min index is in (mid, hi]
        }
    }
    
    // rotate the array k steps to the right
    public static int[] rotate(int[] a, int k) {
        int n = a.length;
        if (k < 0 || k >= n) throw new IllegalArgumentException("illegal k");
        int[] b = new int[n];
        for (int i = 0; i < n; i++) {
            b[(i + k) % n] = a[i]; // move i-th to (i+k)%n
        }
        return b;
    }  

    // generate a sorted array of length n containing 0, 1, 2, ..., n-1
    public static int[] sortedArray(int n) {
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = i;
        }
        return a;
    }

    public static void printArray(int[] a) {
        int i;
        for (i = 0; i < a.length - 1; i++) {
            System.out.print(a[i] + " ");
        }
        System.out.println(a[i]); // print the last element
    }

    public static void main(String[] args) {
        int[] a = sortedArray(Integer.parseInt(args[0]));
        int k = Integer.parseInt(args[1]);
        printArray(a);
        System.out.println("rotate " + k);
        int[] b = rotate(a, k);
        printArray(b);
        System.out.println("minimum index = " + findMinimumIndex(b));
    }
}
