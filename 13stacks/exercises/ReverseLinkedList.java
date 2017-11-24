/*******************************************************************************
 * Write a function that takes the first {@code Node} in a linked List as
 * argument and (destructively) reverses the list, returning the first ode in
 * the result.
 *
 * @author Ruan Duan
 *
 ******************************************************************************/
public class ReverseLinkedList<Item> {
    /**
     * Reverse the linked list destructively.
     */
    public static <Item> void reverse(LinkedList<Item> list) {
        list.setHead(reverseIter(list.head()));
    }

    /**
     * Reverse the linked list destructively.
     */
    public static <Item> void reverse2(LinkedList<Item> list) {
        list.setHead(reverseRecur(list.head()));
    }

    /*
     * Iterative solution: To accomplish this task, we maintain references to three
     * consecutive nodes in the linked list, {@code reverse}, {@code first}, and
     * {@code second}.  At each iteration, we extract the node {@code first} from
     * the original linked list, and insert it at the beginning of the reversed
     * list.  We maintain the invariant that {@code first} is the first node of
     * what's left of the original list, {@code second} is the second node of
     * what's left of the original list, and {@code reverse} is the first node of
     * the resulting reversed list.
     */
    private static <Item> Node<Item> reverseIter(Node<Item> x) {
        // before:                       reverse=null, first -> second ->...
        // break link and reverse first: reverse=null <- first, second ->...
        // move on: reverse = first, first = second, second = first.next
        // after:   null <- reverse=first, new_first=old_second -> new_second ->...
        Node<Item> first = x;
        Node<Item> reverse = null;
        while (first != null) {
            Node<Item> second = first.next;
            first.next = reverse;
            reverse = first;
            first = second;
        }
        return reverse;
    }

    /*
     * Recursive solution: Assuming the linked list has n nodes, we recursively
     * reverse the last n-1 nodes, and then carefully append the first node to
     * the end.
     */
    private static <Item> Node<Item> reverseRecur(Node<Item> first) {
        if (first == null) return null;
        if (first.next == null) return first;
        Node<Item> second = first.next;
        Node<Item> rest = reverseRecur(second); // the tail of 'rest' is 'second'
        second.next = first;
        first.next = null;
        return rest;
    }

    public static void main(String[] args) {
        String[] sa = { "a", "b", "c" };
        LinkedList<String> strList = new LinkedList<String>(sa);
        reverse(strList);
        System.out.println(strList);
        reverse2(strList);
        System.out.println(strList);
    }
}
