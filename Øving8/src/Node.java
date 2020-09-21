import java.util.ArrayList;

public class Node {

    private int nodeNum;
    private int distance = 1000000000;
    private Node previousNode;
    private ArrayList<Edge> outgoingEdgeList = new ArrayList();
    private boolean discovered = false;
    private boolean temporaryFlag = false;

    public Node(int nodeNum){
        this.nodeNum = nodeNum;
    }

    @Override
    public boolean equals(Object obj) {

        return obj instanceof Node && ((Node) obj).nodeNum == this.nodeNum;

    }

    @Override
    public String toString() {
        return "" + nodeNum;
    }

    public int getLength() {
        return distance;
    }

    public void setLength(int length) {
        this.distance = length;
    }

    public Node getPreviousNode() {
        return previousNode;
    }

    public void setPreviousNode(Node previousNode) {
        this.previousNode = previousNode;
    }

    public int getNodeNum() {
        return nodeNum;
    }

    public void setNodeNum(int nodeNum) {
        this.nodeNum = nodeNum;
    }

    public ArrayList<Edge> getOutgoingEdgeList() {
        return outgoingEdgeList;
    }

    public boolean isDiscovered() {
        return discovered;
    }

    public void setDiscovered(boolean discovered) {
        this.discovered = discovered;
    }

    public boolean hasTemporaryFlag() {
        return temporaryFlag;
    }

    public void setTemporaryFlag(boolean temporaryFlag) {
        this.temporaryFlag = temporaryFlag;
    }
}
