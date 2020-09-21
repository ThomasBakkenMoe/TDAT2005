package Oppgave2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Random;

public class Hashtabell {

    private Node[] table;
    private int numberOfElements = 0;
    int collisions = 0;

    public Hashtabell(int length){

        int nearestprime = (int)(length * 1.25);

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

    public void insert(int inputData){
        int hashedValue = primaryHashFunction(inputData);

        if (table[hashedValue] == null){
            table[hashedValue] = new Node(inputData);
            numberOfElements ++;
        }else {

            //System.out.println("Kollisjon! Mellom: " + table[hashedValue].getData() + " og " + inputData);
            collisions++;

            int secondHashedValue = secondaryHashFunction(hashedValue);

            int i = 0;

            while (true){

                int probe = ((hashedValue + i * secondHashedValue) % table.length);

                if(table[probe] == null){
                    table[probe] = new Node(inputData);
                    numberOfElements++;
                    break;
                }else {
                    //System.out.println("Kollisjon! Mellom: " + table[probe].getData() + " og " + inputData);
                    collisions++;
                }
                i++;
            }
        }
    }

    public Node get(int inputData){
        System.out.println("Trying to find: " + inputData);

        int hashedValue = primaryHashFunction(inputData);

        if (table[hashedValue].getData() == inputData){
            return table[hashedValue];
        }else {
            //System.out.println("Kollisjon! Mellom: " + table[hashedValue].getData() + " og " + inputData);
            int secondHashedValue = secondaryHashFunction(hashedValue);

            int i = 0;
            int checkedNodes = 1;

            while (true){
                int probe = ((hashedValue + i * secondHashedValue) % table.length);

                if(table[probe].getData() == inputData){
                    return table[probe];
                }else {
                    //System.out.println("Kollisjon! Mellom: " + table[probe].getData() + " og " + inputData);
                    checkedNodes++;
                }

                if (checkedNodes >= table.length){
                    return null;
                }
                i++;
            }
        }
    }

    private int primaryHashFunction(int intValue){
        return intValue % table.length;
    }

    private int secondaryHashFunction(int intValue){
        return intValue % (table.length - 1) + 1;
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

    public static void main(String[] args){

        Random random = new Random();

        int[] numbers = new int[5000000];

        for(int i=0; i<5000000; i++){
            numbers[i] = random.nextInt(60000110);
        }

        Hashtabell hashtabell = new Hashtabell(numbers.length);


        for (int i = 0; i < numbers.length; i++) {
            hashtabell.insert(numbers[i]);
        }

        System.out.println("Kollisjoner: " + hashtabell.getCollisions());
        System.out.println("Fyllingsgrad: " + hashtabell.loadFactor());
        System.out.println("Kollisjoner per element: " + ((double)hashtabell.getCollisions() / (double)hashtabell.numberOfElements));
        System.out.println("Table length: " + hashtabell.table.length);
    }
}
