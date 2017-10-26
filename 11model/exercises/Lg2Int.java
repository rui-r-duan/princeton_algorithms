public class Lg2Int {
    // @return the largest int not larger than the base-2 logarithm of N
    public static int lg(int n) {
        if (n < 0) {
            throw new RuntimeException("n must be and integer that >= 1");
        }
        else if (n == 0) {
            return 1;
        }
        else {
            int prod = 1;
            int i = 0;
            while (prod <= n) {
                prod *= 2;
                i++;
            }
            return i - 1;
        }
    }
    
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        System.out.println(lg(n));
    }
}