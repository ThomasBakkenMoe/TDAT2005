import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;

public class NodeLoad {

    public void loadNodesFromFile(String filename, ArrayList<Node> nodeList, ArrayList<Edge> edgeList) throws Exception{

        FileReader fileReader = new FileReader(filename);
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        //TODO: Optimize this code by using the header
        bufferedReader.readLine();

        while (true){

            String currentLine = bufferedReader.readLine();
            if (currentLine == null){
                break;
            }

            String[] currentLineArray = currentLine.trim().split("\\s+");

            int fromNodeNumber = Integer.parseInt(currentLineArray[0].trim());
            int toNodeNumber = Integer.parseInt(currentLineArray[1].trim());

            Node potentialFromNode = new Node(fromNodeNumber);
            Node potentialToNode = new Node(toNodeNumber);
            Node newFromNode;
            Node newToNode;

            int fromNodeNumberIndex = nodeList.indexOf(potentialFromNode);
            int toNodeNumberIndex = nodeList.indexOf(potentialToNode);

            if (fromNodeNumberIndex == -1){
                nodeList.add(potentialFromNode);
                newFromNode = potentialFromNode;
                //System.out.println("Added from-node with number: " + potentialFromNode);
            }else{
                newFromNode = nodeList.get(nodeList.indexOf(potentialFromNode));
            }

            if (toNodeNumberIndex == -1){
                nodeList.add(potentialToNode);
                newToNode = potentialToNode;
                //System.out.println("Added to-node with number: " + potentialToNode);
            }else{
                newToNode = nodeList.get(nodeList.indexOf(potentialToNode));

            }

            edgeList.add(new Edge(newFromNode, newToNode));
            newFromNode.getOutgoingEdgeList().add(edgeList.get(edgeList.size()-1));
            //System.out.println("Added " + edgeList.get(edgeList.size()-1) + " to node: " + newFromNode);
            //System.out.println("Added edge nr. " + edgeList.size() + " " + edgeList.get(edgeList.size() -1));
        }
    }

    public static void main(String[] args) throws Exception{
        NodeLoad nodeLoad = new NodeLoad();

        //nodeLoad.loadNodesFromFile("src/L7g1.txt");
    }
}