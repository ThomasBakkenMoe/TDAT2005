import java.util.ArrayList;

public class NodeGraph {

    private ArrayList<Node> nodeList;
    private ArrayList<Edge> edgeList;
    private boolean useCapacity;

    public NodeGraph(String generationFile, boolean useCapacity) throws Exception{

        nodeList = new ArrayList();
        edgeList = new ArrayList();
        this.useCapacity = useCapacity;

        NodeLoad nodeLoader = new NodeLoad();
        nodeLoader.loadNodesFromFile(generationFile, nodeList, edgeList, useCapacity);
    }

    private void createOpposingEdges(){
        for (Edge workingEdge : edgeList) {
            boolean foundOpposingEdge = false;

            if (workingEdge.getOpposingEdge() == null) {

                for (int j = 0; j < workingEdge.getToNode().getOutgoingEdgeList().size(); j++) {
                    if (workingEdge.getToNode().getOutgoingEdgeList().get(j).getToNode() == workingEdge.getFromNode()) {
                        workingEdge.setOpposingEdge(workingEdge.getToNode().getOutgoingEdgeList().get(j));
                        foundOpposingEdge = true;
                        break;
                    }
                }

                if (!foundOpposingEdge) {
                    Edge newOpposingEdge = new Edge(workingEdge.getToNode(), workingEdge.getFromNode(), 0);
                    workingEdge.setOpposingEdge(newOpposingEdge);
                    newOpposingEdge.getFromNode().getOutgoingEdgeList().add(newOpposingEdge);
                    newOpposingEdge.setOpposingEdge(workingEdge);
                }
            }
        }
    }

    public void edmonsKarp(int startingNodeNumber, int endNodeNumber) throws Exception{

        if (!useCapacity){
            throw new Exception("Edmons Karp requires useCapacity to be true!");
        }

        createOpposingEdges();

        Node startNode = nodeList.get(nodeList.indexOf(new Node(startingNodeNumber)));
        Node endNode = nodeList.get(nodeList.indexOf(new Node(endNodeNumber)));
        ArrayList<Integer> tracebackNodeNumberList;
        ArrayList<Edge> tracebackEdgeList;
        Edge lowestRemainingFlowEdge;
        int totaltFlow = 0;

        System.out.println("Maksimum flyt fra " + startingNodeNumber + " til " + endNodeNumber + " med Edmond-Karp");
        System.out.println("Økning : Flytøkende vei");

        while (true){
            //Run BFS
            BFS(startingNodeNumber, false);

            if (endNode.getPreviousNode() == null){
                break;
            }

            // find route from end node, back to the start
            tracebackNodeNumberList = new ArrayList<>();
            tracebackNodeNumberList.add(endNode.getNodeNum());
            tracebackEdgeList = new ArrayList<>();
            lowestRemainingFlowEdge = endNode.getPreviousNode().getOutgoingEdgeList().get(endNode.getPreviousNode().getOutgoingEdgeList().indexOf(new Edge(endNode.getPreviousNode(), endNode, 0)));
            Node currentNode = endNode;
            boolean noTraceFound = false;

            while (true){

                if (currentNode.getPreviousNode() != null){
                    if (currentNode.getPreviousNode().getOutgoingEdgeList().get(currentNode.getPreviousNode().getOutgoingEdgeList().indexOf(new Edge(currentNode.getPreviousNode(), currentNode, 0))).getRemainingCapacity() < lowestRemainingFlowEdge.getRemainingCapacity()){
                        lowestRemainingFlowEdge = currentNode.getPreviousNode().getOutgoingEdgeList().get(currentNode.getPreviousNode().getOutgoingEdgeList().indexOf(new Edge(currentNode.getPreviousNode(), currentNode, 0)));
                    }

                    tracebackEdgeList.add(currentNode.getPreviousNode().getOutgoingEdgeList().get(currentNode.getPreviousNode().getOutgoingEdgeList().indexOf(new Edge(currentNode.getPreviousNode(), currentNode, 0))));
                    tracebackNodeNumberList.add(currentNode.getPreviousNode().getNodeNum());
                    currentNode = currentNode.getPreviousNode();
                }else{
                    noTraceFound = true;
                    break;
                }

                if (currentNode == startNode){
                    break;
                }
            }

            if (noTraceFound){
                break;
            }

            System.out.print("\t" + lowestRemainingFlowEdge.getRemainingCapacity() + "\t");
            for (int i = tracebackEdgeList.size(); i >= 0; i--) {
                System.out.print(tracebackNodeNumberList.get(i) + " ");
            }
            System.out.println();

            int flowDelta = lowestRemainingFlowEdge.getRemainingCapacity();
            for (Edge edge: tracebackEdgeList){
                edge.setFlow(edge.getFlow() + flowDelta);
                edge.getOpposingEdge().setFlow(edge.getOpposingEdge().getFlow() - flowDelta);
            }

            totaltFlow += flowDelta;

            resetForBFS();

        }
        System.out.println("Maksimal flyt ble: " + totaltFlow);
    }

    private void resetForBFS(){
        for (Node node: nodeList){
            node.setDiscovered(false);
            node.setPreviousNode(null);
        }
    }

    public void BFS(int startingNodeNumber, boolean verbose){
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

            for (int j = 0; j < currentNode.getOutgoingEdgeList().size(); j++) {
                Node workingNode  = currentNode.getOutgoingEdgeList().get(j).getToNode();
                if (!workingNode.isDiscovered()){
                    if (useCapacity){
                        if (currentNode.getOutgoingEdgeList().get(j).getRemainingCapacity() > 0){
                            workingNode.setDiscovered(true);
                            workingNode.setLength(currentNode.getLength() + 1);
                            workingNode.setPreviousNode(currentNode);
                            waitingList.add(workingNode);
                        }
                    }else {
                        workingNode.setDiscovered(true);
                        workingNode.setLength(currentNode.getLength() + 1);
                        workingNode.setPreviousNode(currentNode);
                        waitingList.add(workingNode);
                    }
                }
            }
        }

        if (verbose){
            System.out.println("BFS result");
            for (int i = 0; i < nodeList.size(); i++) {

                System.out.println("From: " + nodeList.get(i) + " to: " + startingNode + " Length: " + nodeList.get(i).getLength() + " Prev.: " + nodeList.get(i).getPreviousNode());
            }
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
            checkNodeTopological(node.getOutgoingEdgeList().get(i).getToNode(), resultList);
        }

        node.setTemporaryFlag(false);

        node.setDiscovered(true);

        resultList.add(node);

    }

    public static void main(String[] args) throws Exception{

        NodeGraph nodeGraph = new NodeGraph("src/flytgraf3.txt", true);

        nodeGraph.edmonsKarp(0, 1);

        /*
        NodeGraph nodeGraph = new NodeGraph("src/L7g2.txt");
        nodeGraph.BFS(0);

        System.out.println();

        nodeGraph = new NodeGraph("src/L7g1.txt");
        nodeGraph.topologicalSort();
        */
    }
}