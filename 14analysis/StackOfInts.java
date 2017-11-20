/******************************************************************************
 *  Compilation:  javac StackOfInts.java
 *  Execution:    java StackOfInts
 *  Dependencies: StdIn.java StdOut.java
 *
 *  A stack of int values.
 * 
 ******************************************************************************/

import java.util.NoSuchElementException;

public class StackOfInts {
    private Node first;        // top of stack
    private int N;             // number of items on stack

    // linked list node helper data type
    private static class Node {
        private int  item;
        private Node next;
    }

    // create an empty stack
    public StackOfInts() {
        first = null;
        N = 0;
    }

    // is the stack empty?
    public boolean isEmpty() {
        return first == null;
    }

    // number of items on stack
    public int size() {
        return N;
    }

    // add an item to the stack
    public void push(int item) {
        Node oldfirst = first;
        first = new Node();
        first.item = item;
        first.next = oldfirst;
        N++;
    }

    // delete and return the most recently added item
    public int pop() {
        if (isEmpty()) throw new NoSuchElementException("Stack underflow");
        int item = first.item;         // save item to return
        first = first.next;            // delete first node
        N--;
        return item;                   // return the saved item
    }

    // return the most recently added item
    public int peek() {
        if (isEmpty()) throw new NoSuchElementException("Stack underflow");
        return first.item;
    }


    // a test client
    public static void main(String[] args) {
        StackOfInts stack = new StackOfInts();
        stack.push(1);
        stack.push(2);
        StdOut.println(stack.pop());
        stack.push(3);
        stack.push(4);
        while (!stack.isEmpty()) {
            int a = stack.pop();
            StdOut.println(a);
        }
    }
}
