/*******************************************************************************
 *  A randomized queue is similar to a stack or queue, except that the item
 *  removed is chosen uniformly at random from items in the data structure.
 *
 * Iterator.  Each iterator must return the items in uniformly random order.
 * The order of two or more iterators to the same randomized queue must be
 * mutually independent; each iterator must maintain its own random order.
 *
 * Corner cases.  Throw the specified exception for the following corner cases:
 *   - Throw a java.lang.IllegalArgumentException if the client calls enqueue()
 * with a null argument.
 *   - Throw a java.util.NoSuchElementException if the client calls either
 * sample() or dequeue() when the randomized queue is empty.
 *   - Throw a java.util.NoSuchElementException if the client calls the
 * next() method in the iterator when there are no more items to return.
 *   - Throw a java.lang.UnsupportedOperationException if the client calls the
 * remove() method in the iterator.
 *
 * Performance requirements.  Your randomized queue implementation must support
 * each randomized queue operation (besides creating an iterator) in constant
 * amortized time.  That is, any sequence of m randomized queue operations
 * (starting from an empty queue) must take at most cm steps in the worst case,
 * for some constant c.  A randomized queue containing n items must use at most
 * 48n + 192 bytes of memory.  Additionally, your iterator implementation must
 * support operations next() and hasNext() in constant worst-case time; and
 * construction in linear time; you may (and will need to) use a linear amount
 * of extra memory per iterator.

 ******************************************************************************/
import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdOut;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] q;           // queue elements
    private int n; // number of elements on queue, index of next available slot

    /**
     * construct an empty randomized queue
     */
    public RandomizedQueue() {
        q = (Item[]) new Object[2];
        n = 0;
    }

    /**
     * is the randomized queue empty?
     */
    public boolean isEmpty() {
        return n == 0;
    }

    /**
     * return the number of items on the randomized queue
     */
    public int size() {
        return n;
    }

    private void resize(int capacity) {
        assert capacity >= n;
        Item[] temp = (Item[]) new Object[capacity];
        for (int i = 0; i < n; i++) {
            temp[i] = q[i % q.length];
        }
        q = temp;
    }

    /**
     * add the item
     */
    public void enqueue(Item item) {
        if (item == null)
            throw new IllegalArgumentException("null item is not allowed");

        if (n == q.length) resize(2 * q.length); // double size if necessary
        q[n++] = item;    // add item, last never go out of the array bounds
    }

    /**
     * remove and return a random item
     */
    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException("Queue underflow");
        int randIndex = getValidRandomIndex(); // [0, n)
        Item item = q[randIndex];             // get the item in slot randIndex
        q[randIndex] = q[n-1]; // copy the tail item to slot randIndex
        q[n-1] = null;         // remove the tail item, to avoid loitering
        n--;
        // shrink size of array if necessary
        if (n > 0 && n == q.length/4) resize(q.length/2);
        return item;
    }

    /**
     * return a random item (but do not remove it)
     */
    public Item sample() {
        if (isEmpty()) throw new NoSuchElementException("Queue underflow");
        return q[getValidRandomIndex()];
    }

    // generate a random index in the range [0, n)
    private int getValidRandomIndex() {
        return StdRandom.uniform(n);
    }

    /**
     * return an independent iterator over items in random order
     */
    public Iterator<Item> iterator() {
        return new ArrayIterator();
    }

    private class ArrayIterator implements Iterator<Item> {
        private int i = 0;
        private final int[] indexPermutation; // random index permutation

        public ArrayIterator() {
            indexPermutation = StdRandom.permutation(n);
        }

        public boolean hasNext() {
            return i < n;
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = q[indexPermutation[i]];
            i++;
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    private static <Item> void debugPrintQueue(RandomizedQueue<Item> rq) {
        StdOut.println("Print the queue using the Iterator:");
        for (Item item : rq) {
            StdOut.print(item + " ");
        }
        StdOut.println();
    }

    /**
     * unit testing (optional)
     */
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        RandomizedQueue<Integer> rq = new RandomizedQueue<Integer>();

        // generate the queue of length n
        int i;
        for (i = 0; i < n; i++) {
            rq.enqueue(i);
        }

        debugPrintQueue(rq);

        // peek some samples (randomly, according to the API specification)
        StdOut.println("Peek 3 samples randomly:");
        for (i = 0; i < 3; i++) {
            StdOut.print(" " + rq.sample());
        }
        StdOut.println();

        debugPrintQueue(rq);

        // dequeue some times
        StdOut.println("Dequeue 3 times:");
        for (i = 0; i < 3; i++) {
            StdOut.print(" " + rq.dequeue());
        }
        StdOut.println();

        debugPrintQueue(rq);

        // dequeue until empty
        StdOut.println("Dequeue until the queue is empty:");
        i = 0;
        int cnt = rq.size();    // test size();
        while (!rq.isEmpty()) { // test isEmpty()
            StdOut.print(" " + rq.dequeue());
            i++;
        }
        StdOut.println();
        if (i == cnt && rq.size() == 0) {
            StdOut.println("RandomizedQueue.size() works correctly");
        }
    }
}
