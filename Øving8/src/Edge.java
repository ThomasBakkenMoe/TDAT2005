public class Edge {
    private Node fromNode;
    private Node toNode;
    private int flow = 0;
    private int capacity;
    private Edge opposingEdge;

    public Edge(Node fromNode, Node toNode, int capacity){
        this.fromNode = fromNode;
        this.toNode = toNode;
        this.capacity = capacity;
    }

    @Override
    public String toString() {
        return "Edge from: " + fromNode + " to: " + toNode;
    }

    @Override
    public boolean equals(Object obj) {

        return obj instanceof Edge && ((Edge) obj).fromNode == this.fromNode && ((Edge) obj).toNode == this.toNode;

    }

    public Node getFromNode(){
        return fromNode;
    }

    public void setFromNode(Node fromNode) {
        this.fromNode = fromNode;
    }

    public Node getToNode() {
        return toNode;
    }

    public void setToNode(Node toNode) {
        this.toNode = toNode;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getFlow() {
        return flow;
    }

    public void setFlow(int flow) {
        this.flow = flow;
    }

    public int getRemainingCapacity(){
        return capacity - flow;
    }

    public Edge getOpposingEdge() {
        return opposingEdge;
    }

    public void setOpposingEdge(Edge opposingEdge) {
        this.opposingEdge = opposingEdge;
    }
}