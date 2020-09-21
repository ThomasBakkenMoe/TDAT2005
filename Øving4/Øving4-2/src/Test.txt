public class CircularList {

    private Node previouslySelected;
    private Node current;
    private Node head;
    private int length = 0;

    public CircularList(int length){

        for (int i = 0; i < length; i++) {
            addNode(i + 1);
        }

        current = head;

    }

    public void addNode(int id){

        Node newNode = new Node(id);

        //Hvis lista er tom
        if(head == null){
            head = newNode;
            current = newNode;
            length++;
        }else {
            current.setNext(newNode);
            current = newNode;
            current.setNext(head);
            length++;
        }
    }

    public void removeCurrentNode(){
        if (current == head){
            head = current.getNext();
        }
        current = current.getNext();
        previouslySelected.setNext(current);
        length--;
    }

    public void next(){
        previouslySelected = current;
        current = current.getNext();
    }

    public Node getCurrent(){
        return current;
    }

    public int getLength(){
        return length;
    }


    public void display(){

        Node displayCurrent = head;

        do {

            System.out.println(displayCurrent.getId());

            displayCurrent = displayCurrent.getNext();

        }while (displayCurrent != head);
    }

    public static void main(String[] args) {
        CircularList cl = new CircularList(10);

        cl.display();
    }
}