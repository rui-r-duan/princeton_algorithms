/******************************************************************************
 *  Compilation:  javac StackOfIntegers.java
 *  Execution:    java StackOfIntegers
 *  Dependencies: StdIn.java StdOut.java
 *
 *  A stack of Integer values (using a linked list with a static
 *  nested class).
 * 
 ******************************************************************************/

import java.util.NoSuchElementException;

public class StackOfIntegers {
    private Node first;        // top of stack
    private int N;             // number of items on stack

    // linked list node helper data type
    private static class Node {
        private Integer item;
        private Node next;
    }

    // create an empty stack
    public StackOfIntegers() {
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
    public void push(Integer item) {
        Node oldfirst = first;
        first = new Node();
        first.item = item;
        first.next = oldfirst;
        this.N++;
    }

    // delete and return the most recently added item
    public Integer pop() {
        if (isEmpty()) throw new NoSuchElementException("Stack underflow");
        int item = first.item;         // save item to return
        first = first.next;            // delete first node
        N--;
        return item;                   // return the saved item
    }


    // a test client
    public static void main(String[] args) {
        StackOfIntegers stack = new StackOfIntegers();
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
