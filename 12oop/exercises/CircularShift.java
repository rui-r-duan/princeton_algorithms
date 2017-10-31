public class CircularShift {
    static boolean isCircularShift(String s, String t) {
        return (s.length() == t.length()) && (s.concat(s).indexOf(t) >= 0);
    }
    
    public static void main(String[] args) {
        String s = args[0];
        String t = args[1];
        if (isCircularShift(s, t)) {
            StdOut.println("\"" + s + "\" is a circular shift of \"" + t + "\"");
        }
        else {
            StdOut.println("\"" + s + "\" is NOT a circular shift of \"" + t + "\"");
        }
    }
}