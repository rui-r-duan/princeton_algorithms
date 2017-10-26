public class ArrayTranspose {
    public static int[][] transposeArray(int[][] a) {
        assert a.length > 0 && a[0].length > 0;
        int m = a.length; // m rows
        int n = a[0].length;
        int[][] b = new int[n][m];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                b[j][i] = a[i][j];
            }
        }
        return b;
    }
    static void printArray(int[][] a) {
        int m = a.length;
        if (m == 0) return;
        
        int n = a[0].length;
        if (n == 0) return;
        
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(a[i][j] + ((j == n-1) ? "\n" : " "));
            }
        }
    }
    public static void main(String[] args) {
        int[][] a = { {1, 2, 3}, {4, 5, 6} };
        printArray(a);
        int[][] b = transposeArray(a);
        printArray(b);
    }
}