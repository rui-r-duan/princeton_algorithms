public class RotateArray {

    // rotate the array k steps to the right
    public static int[] rotate_1(int[] a, int k) {
        int n = a.length;
        if (k < 0 || k >= n) throw new IllegalArgumentException("illegal k");
        int[] b = new int[n];
        for (int i = 0; i < n; i++) {
            b[i] = a[(i - k + n) % n]; // move (n-k)th to 0-th, (n-k+i)th to i-th
        }
        return b;
    }

    // rotate the array k steps to the right
    public static int[] rotate_2(int[] a, int k) {
        int n = a.length;
        if (k < 0 || k >= n) throw new IllegalArgumentException("illegal k");
        int[] b = new int[n];
        for (int i = 0; i < n; i++) {
            b[(i + k) % n] = a[i]; // move i-th to (i+k)%n
        }
        return b;
    }

    // rotate the array k steps to the left
    public static int[] rotate_3(int[] a, int k) {
        int n = a.length;
        if (k < 0 || k >= n) throw new IllegalArgumentException("illegal k");
        int[] b = new int[n];
        for (int i = 0; i < n; i++) {
            b[i] = a[(i + k) % n];
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
        printArray(rotate_1(a, k));
        printArray(rotate_2(a, k));
        printArray(rotate_3(a, k));
    }
}
