/*******************************************************************************
 * Deque (pronounced "deck") is a double-ended queue.  It is a generalization
 * of a stack and a queue that supports adding and removing items from either
 * the front or the back of the data structure.
 *
 * This implementation support each deque operation (including construction) in
 * constant worst-case time.
 * A deque containing n items must use at most (48 n + 192) bytes of memory and
 * use space proportional to the number of items currently in the deque.
 * Additionally, the iterator implementation must support each operation
 * (including construction) in constant worst-case time.
 *
 * Corner cases.  Throw the specified exception for the following corner cases:
 *   - Throw a java.lang.IllegalArgumentException if the client calls either
 * addFirst() or addLast() with a null argument.  
 *
 *   - Throw a java.util.NoSuchElementException if the client calls either
 * removeFirst() or removeLast when the deque is empty.

 *   - Throw a java.util.NoSuchElementException if the client calls the next()
 * method in the iterator when there are no more items to return.
 *
 *   Throw a java.lang.UnsupportedOperationException if the client calls the
 * remove() method in the iterator.
 *
 * Execution Example:
 * prefixes:
 *  + addFirst
 *  - removeFirst
 *  * addLast
 *  / removeLast
 *
 * % java Deque
 * % +a *b +c / +d - - -
    (1 left on queue): a
    (2 left on queue): a b
    (3 left on queue): c a b
    b (2 left on queue): c a
    (3 left on queue): d c a
    d (2 left on queue): c a
    c (1 left on queue): a
    a (0 left on queue):
 *
 ******************************************************************************/
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private int n;              // number of elements on queue
    private Node first;         // left end of queue
    private Node last;          // right end of queue

    // helper linked list class
    private class Node {
        Item item;
        Node next;
        Node prev;
    }

    /**
     * construct an empty deque
     */
    public Deque() {
        first = null;
        last = null;
        assert check();
    }

    /**
     * is the deque empty?
     */
    public boolean isEmpty() {
        return n == 0;
    }

    /**
     * return the number of items on the deque
     */
    public int size() {
        return n;
    }

    /**
     * add the item to the front
     */
    public void addFirst(Item item) {
        if (item == null) throw new IllegalArgumentException("null item is not allowed");
        Node oldFirst = first;
        first = new Node();
        first.item = item;
        first.next = oldFirst;
        first.prev = null;
        if (isEmpty()) last = first;
        else           oldFirst.prev = first;
        n++;
        assert check();
    }

    /**
     * add the item to the end
     */
    public void addLast(Item item) {
        if (item == null) throw new IllegalArgumentException("null item is not allowed");
        Node oldLast = last;
        last = new Node();
        last.item = item;
        last.next = null;
        last.prev = oldLast;
        if (isEmpty()) first = last;
        else           oldLast.next = last;
        n++;
        assert check();
    }

    /**
     * remove and return the item from the front
     */
    public Item removeFirst() {
        if (isEmpty()) throw new NoSuchElementException("Deque underflow");
        Item item = first.item;
        first = first.next;
        n--;
        if (isEmpty()) last = null; // to avoid loitering
        else           first.prev = null;
        assert check();
        return item;
    }

    /**
     * remove and return the item from the end
     */
    public Item removeLast() {
        if (isEmpty()) throw new NoSuchElementException("Deque underflow");
        Item item = last.item;
        last = last.prev;
        n--;
        if (isEmpty()) first = null; // to avoid loitering
        else           last.next = null;
        assert check();
        return item;
    }

    private boolean check() {
        if (n < 0) {
            return false;
        }
        else if (n == 0) {
            if (first != null) return false;
            if (last  != null) return false;
        }
        else if (n == 1) {
            if (first == null || last == null) return false;
            if (first != last)                 return false;
            if (first.next != null)            return false;
            if (first.prev != null)            return false;
        }
        else {
            if (first == null || last == null) return false;
            if (first == last)      return false;
            if (first.next == null) return false;
            if (first.prev != null) return false;
            if (last.prev == null)  return false;
            if (last.next != null)  return false;

            // check internal consistency of instance variable n
            int numberOfNodes = 0;
            for (Node x = first; x != null && numberOfNodes <= n; x = x.next) {
                numberOfNodes++;
            }
            if (numberOfNodes != n) return false;

            numberOfNodes = 0;
            for (Node x = last; x != null && numberOfNodes <= n; x = x.prev) {
                numberOfNodes++;
            }
            if (numberOfNodes != n) return false;

            // check internal consistency of instance variable last
            Node lastNode = first;
            while (lastNode.next != null) {
                lastNode = lastNode.next;
            }
            if (last != lastNode) return false;

            // check internal consistency of instance variable first
            Node firstNode = last;
            while (firstNode.prev != null) {
                firstNode = firstNode.prev;
            }
            if (first != firstNode) return false;
        }

        return true;
    }
    
    /**
     * return an iterator over items in order from front to end
     */
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }
        
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
    
    
    /**
     * unit testing (optional)
     */
    public static void main(String[] args) {
        Deque<String> deque = new Deque<String>();
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            if (item.startsWith("+")) {
                deque.addFirst(item.substring(1));
            }
            else if (item.equals("-")) {
                StdOut.print(deque.removeFirst() + " ");
            }
            else if (item.startsWith("*")) {
                deque.addLast(item.substring(1));
            }
            else if (item.equals("/")) {
                StdOut.print(deque.removeLast() + " ");
            }
            StdOut.print("(" + deque.size() + " left on queue): ");
            for (String s : deque) {
                StdOut.printf("%s ", s);
            }
            StdOut.println();
        }
    }
}
