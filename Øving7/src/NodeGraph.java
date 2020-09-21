import java.util.ArrayList;

public class NodeGraph {

    ArrayList<Node> nodeList;
    ArrayList<Edge> edgeList;

    public NodeGraph(String generationFile) throws Exception{

        nodeList = new ArrayList();
        edgeList = new ArrayList();

        NodeLoad nodeLoader = new NodeLoad();
        nodeLoader.loadNodesFromFile(generationFile, nodeList, edgeList);
    }

    public void BFS(int startingNodeNumber){
        ArrayList<Node> waitingList = new ArrayList<>();
        Node startingNode = nodeList.get(nodeList.indexOf(new Node(startingNodeNumber)));
        //System.out.println("starting node:" + startingNode);
        waitingList.add(startingNode);
        startingNode.setLength(0);

        Node currentNode;
        for (int i = 0; i < nodeList.size(); i++) {
            if (waitingList.isEmpty()){
                break;
            }
            currentNode = waitingList.get(0);
            currentNode.setDiscovered(true);
            waitingList.remove(0);
            /*
            for (int k = 0; k < currentNode.getOutgoingEdgeList().size(); k++) {
                System.out.println("Connected edges: " + currentNode.getOutgoingEdgeList().get(k));

            }
            */

            for (int j = 0; j < currentNode.getOutgoingEdgeList().size(); j++) {
                Node workingNode  = currentNode.getOutgoingEdgeList().get(j).toNode;
                //System.out.println("working with: " + workingNode);
                if (!workingNode.isDiscovered()){
                   workingNode.setDiscovered(true);
                   workingNode.setLength(currentNode.getLength() + 1);
                   workingNode.setPreviousNode(currentNode);
                   waitingList.add(workingNode);
                }
            }
        }

        System.out.println("BFS result");
        for (int i = 0; i < nodeList.size(); i++) {

            System.out.println("From: " + nodeList.get(i) + " to: " + startingNode + " Length: " + nodeList.get(i).getLength() + " Prev.: " + nodeList.get(i).getPreviousNode());
        }
    }

    public void topologicalSort() throws Exception{
        ArrayList<Node> resultList = new ArrayList<>();

        boolean stop = false;
        Node currentNode = null;
        while (!stop){

            //Select current node
            for (int i = 0; i < nodeList.size(); i++) {
                if (!nodeList.get(i).isDiscovered() && !nodeList.get(i).hasTemporaryFlag()){
                    currentNode = nodeList.get(i);
                    break;
                }
            }

            if (currentNode != null){
                checkNodeTopological(currentNode, resultList);
            }

            //Sets stop condition
            stop = true;
            for (int i = 0; i < nodeList.size(); i++) {
                if (!nodeList.get(i).isDiscovered()){
                    stop = false;
                }
            }
        }

        //Print result
        System.out.println("Topological sort result:");
        for (int i = resultList.size() - 1; i >= 0; i--) {
            System.out.println(resultList.get(i));
        }
    }

    private void checkNodeTopological(Node node, ArrayList<Node> resultList) throws Exception{
        if (node.isDiscovered()){
            return;
        }

        if (node.hasTemporaryFlag()){
            throw new Exception("This graph is cyclical!");
        }

        node.setTemporaryFlag(true);

        for (int i = 0; i < node.getOutgoingEdgeList().size(); i++) {
            checkNodeTopological(node.getOutgoingEdgeList().get(i).toNode, resultList);
        }

        node.setTemporaryFlag(false);

        node.setDiscovered(true);

        resultList.add(node);

    }

    public static void main(String[] args) throws Exception{
        NodeGraph nodeGraph = new NodeGraph("src/L7g2.txt");
        nodeGraph.BFS(0);

        System.out.println();

        nodeGraph = new NodeGraph("src/L7g1.txt");
        nodeGraph.topologicalSort();
    }
}