public class Node {
    private int id;
    private Node next;

    public Node(int id){

        this.id = id;

    }

    public int getId() {
        return id;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }
}
