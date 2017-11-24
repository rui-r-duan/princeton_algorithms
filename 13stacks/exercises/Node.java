public class Node<Item> {
    Item item;
    Node<Item> next;

    public Node(Item item, Node<Item> next) {
        this.item = item;
        this.next = next;
    }
}
