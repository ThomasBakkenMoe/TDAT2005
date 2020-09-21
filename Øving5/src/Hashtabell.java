import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class Hashtabell {

    private Node[] table;
    private int numberOfElements = 0;
    int collisions = 0;

    public Hashtabell(int length){

        int nearestprime = length;

        boolean foundNearestPrime = false;

        while(!foundNearestPrime){

            foundNearestPrime = true;

            for (int i = 2; i < nearestprime / 2; i++) {
                if (nearestprime % i == 0){
                    foundNearestPrime = false;
                }
            }

            if (!foundNearestPrime){
                nearestprime++;
            }
        }

        table = new Node[nearestprime];
    }

    public void insert(String inputNavn){
        int hashedValue = primaryHashFunction(findWeightedIntValueFromString(inputNavn));

        if (table[hashedValue] == null){
            table[hashedValue] = new Node(inputNavn);
            numberOfElements ++;
        }else {

            System.out.println("Kollisjon! Mellom: " + table[hashedValue].getNavn() + " og " + inputNavn);
            collisions++;

            Node currentNode = table[hashedValue];
            while (currentNode.getNext() != null){
                currentNode = currentNode.getNext();
            }

            currentNode.setNext(new Node(inputNavn));
            numberOfElements++;
        }
    }

    public Node get(String inputNavn){
        System.out.println("Trying to find: " + inputNavn);

        int hashedValue = primaryHashFunction(findWeightedIntValueFromString(inputNavn));

        if (table[hashedValue] == null){
            return null;
        }
        if (table[hashedValue].getNavn().equals(inputNavn)){
            System.out.println("Found: " + inputNavn + "!");
            return table[hashedValue];
        } else {

            System.out.println("Kollisjon! Mellom: " + table[hashedValue].getNavn() + " og " + inputNavn);

            Node currentNode = table[hashedValue];
            while (!currentNode.getNavn().equals(inputNavn)){
                currentNode = currentNode.getNext();
            }

            System.out.println("Found: " + inputNavn + "!");

            return currentNode;
        }
    }

    private int findWeightedIntValueFromString(String string){

        int output = 0;

        for (int i = 0; i < string.length(); i++) {
            output += (string.charAt(i) * (i + 1));
        }

        return output;
    }

    private int primaryHashFunction(int intValue){
        return intValue % table.length;
    }

    public int getNumberOfElements(){
        return numberOfElements;
    }

    public double loadFactor(){
        return (double)numberOfElements / (double)table.length;
    }

    public int getCollisions() {
        return collisions;
    }

    public void resetCollisions(){
        collisions = 0;
    }

    public static void main(String[] args) throws Exception{

        FileReader fileReader = new FileReader("src/navn.txt");
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        boolean read = true;

        ArrayList<String> readList= new ArrayList<>();

        while (read){
            String line = bufferedReader.readLine();

            if (line != null){
                readList.add(line);
            }else {
                read = false;
            }
        }

        Hashtabell hashtabell = new Hashtabell(readList.size());

        for (int i = 0; i < readList.size(); i++) {
            hashtabell.insert(readList.get(i));
        }

        System.out.println("Kollisjoner: " + hashtabell.getCollisions());
        System.out.println("Fyllingsgrad: " + hashtabell.loadFactor());
        System.out.println("Kollisjoner per element: " + ((double)hashtabell.getCollisions() / (double)hashtabell.numberOfElements));
    }
}
