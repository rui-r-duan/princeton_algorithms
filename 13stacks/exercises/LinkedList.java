/*******************************************************************************
 * LinkedList<Item> class provides methods to construct a singly linked
 * list using Node<Item> structure.  Once a list is constructed, it can be
 * accessed by {@code head()}, {@code rest()} and {@code size()}.
 *
 * @author Ryan Duan
 ******************************************************************************/

import java.util.Iterator;
import java.util.NoSuchElementException;

public class LinkedList<Item> implements Iterable<Item> {
    private Node<Item> first;
    private int n;
    private boolean invalidSize;

    //----------------------------------------------------------------
    // Accessors
    //----------------------------------------------------------------
    public Node<Item> head() {
        return first;
    }

    public Node<Item> rest() {
        return first.next;
    }

    public int size() {
        if (invalidSize) {
            updateSize();
        }
        return n;
    }

    //----------------------------------------------------------------
    // Setter
    //----------------------------------------------------------------
    public void setHead(Node<Item> newHead) {
        first = newHead;
        invalidSize = true;
    }
    
    //----------------------------------------------------------------
    // Constructors
    //----------------------------------------------------------------
    public LinkedList() {
        first = null;
        n = 0;
        invalidSize = false;
    }

    public LinkedList(Item[] a) {
        invalidSize = false;
        n = a.length;
        Node<Item> newNode;
        for (int i = n-1; i >= 0; i--) {
            newNode = new Node<Item>(a[i], first);
            first = newNode;
        }
    }

    public LinkedList(Node<Item> nodeList) {
        first = nodeList;
        updateSize();
        invalidSize = false;
    }

    public void pushFront(Item item) {
        Node<Item> newNode = new Node<Item>(item, first);
        first = newNode;
        n++;
    }

    //----------------------------------------------------------------
    // Utilities
    //----------------------------------------------------------------
    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (Item item : this) {
            s.append(item);
            s.append(' ');
        }
        return s.toString();
    }

    private void updateSize() {
        n = 0;
        Node<Item> p = first;
        while (p != null) {
            n++;
            p = p.next;
        }
    }

    //----------------------------------------------------------------
    // Iterator
    //----------------------------------------------------------------
    @Override
    public Iterator<Item> iterator() {
        return new ListIterator(first);
    }

    private class ListIterator implements Iterator<Item> {
        private Node<Item> current;

        public ListIterator(Node<Item> first) {
            current = first;
        }

        public boolean hasNext() {
            return current != null;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    //----------------------------------------------------------------
    // Simple test
    //----------------------------------------------------------------
    public static void main(String[] args) {
        Integer[] ia = { 1, 3, 5, 7, 9 };
        LinkedList<Integer> intList = new LinkedList<Integer>(ia);
        System.out.println(intList);

        String[] sa = { "a", "b", "c" };
        LinkedList<String> strList = new LinkedList<String>(sa);
        System.out.println(strList);

        LinkedList<Double> doubleList = new LinkedList<Double>();
        System.out.println("empty list: " + doubleList);

        doubleList.pushFront(Math.PI);
        doubleList.pushFront(Math.E);
        System.out.println(doubleList);
        System.out.println("doubleList size = " + doubleList.size());
        for (Double f : doubleList) {
            System.out.println(f);
        }
    }
}
