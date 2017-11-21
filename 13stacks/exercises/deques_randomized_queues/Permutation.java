/*******************************************************************************
 Write a client program Permutation.java that takes an integer k as a
 command-line argument; reads in a sequence of strings from standard input
 using StdIn.readString(); and prints exactly k of them, uniformly at
 random. Print each item from the sequence at most once.

  % more distinct.txt                        % more duplicates.txt
  A B C D E F G H I                          AA BB BB BB BB BB CC CC

  % java Permutation 3 < distinct.txt        % java Permutation 8 < duplicates.txt
  C                                          BB
  G                                          AA
  A                                          BB
                                             CC
  % java Permutation 3 < distinct.txt        BB
  E                                          BB
  F                                          CC
  G                                          BB



 Your program must implement the following API:

 public class Permutation {
    public static void main(String[] args)
 }

 Command-line input.  You may assume that 0 <= k <= n, where n is the number of
 string on standard input.

 Performance requirements.  The running time of Permutation must be linear in
 the size of the input. You may use only a constant amount of memory plus
 either one Deque or RandomizedQueue object of maximum size at most n. (For an
 extra challenge, use only one Deque or RandomizedQueue object of maximum size
 at most k.)
 ******************************************************************************/

import java.util.Iterator;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> rq = new RandomizedQueue<String>();
        while (!StdIn.isEmpty()) {
            String s = StdIn.readString();
            rq.enqueue(s);
        }
        Iterator<String> itr = rq.iterator();
        while (k > 0) {
            StdOut.println(itr.next());
            k--;
        }
    }
}
