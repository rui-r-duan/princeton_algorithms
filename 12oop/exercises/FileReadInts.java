/*******************************************************************************
 *
 * File input. Develop a static version of the readAllInts() method from class
 * In (which we use for various test clients, such as binary search on page 47)
 * that is based on the split() method in String.
 *
 * @author Ryan Duan
 *
 ******************************************************************************/

public class FileReadInts {
    public static int[] readAllInts(String filename) {
        In in = new In(filename);
        String input = in.readAll();
        String[] words = input.split("\\s+"); // delimit by one or more whitespace
        int[] ints = new int[words.length];
        for (int i = 0; i < words.length; i++)
            ints[i] = Integer.parseInt(words[i]);
        return ints;
    }

    public static void main(String[] args) {
        String filename = args[0];
        int[] ints = readAllInts(filename);
        for (int i = 0; i < ints.length; i++) {
            StdOut.print(ints[i] + (i == ints.length - 1 ? "\n" : " "));
        }
    }
}
