public class Node {
    private String navn;
    private Node next;

    public Node(String navn){

        this.navn = navn;

    }

    public String getNavn() {
        return navn;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }
}
