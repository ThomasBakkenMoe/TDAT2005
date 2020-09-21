public class Edge {
    Node fromNode;
    Node toNode;

    public Edge(Node fromNode, Node toNode){
        this.fromNode = fromNode;
        this.toNode = toNode;
    }

    @Override
    public String toString() {
        return "Edge from: " + fromNode + " to: " + toNode;
    }
}