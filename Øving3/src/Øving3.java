import java.util.Random;

public class Øving3 {

    public static void main(String[] args) {

        int arraySize = 10;
        int randomBound = 1000;
        Random rnd = new Random();

        int[] array = new int[arraySize];

        //Fills array
        for(int i = 0; i < arraySize; i++){
            array[i]= rnd.nextInt(randomBound);
        }

        System.out.println("usortert");
        for (int i = 0; i < arraySize; i++) {
            System.out.println(array[i]);
        }

        quicksort(array, 0, array.length-1);


        System.out.println("sortert");
        for (int i = 0; i < arraySize; i++) {
            System.out.println(array[i]);
        }
    }

    static void quicksort(int[] array, int nedreGrense, int ovreGrense){

        int deleverdi = (ovreGrense - nedreGrense) / 2;
        System.out.println("Deleverdi: " + array[deleverdi]);

        int nedreIndex = deleverdi - 1;

        int ovreIndex = deleverdi + 1;

        while ((nedreIndex >= nedreGrense) && (ovreIndex <= ovreGrense +1)){
/*
            if (array[deleverdi] < array[nedreGrense]){
                bytt(array, deleverdi, nedreIndex);
                deleverdi = nedreGrense;

            }
            if (array[deleverdi] > array[ovreGrense]) {
                bytt(array, deleverdi, ovreIndex);
                deleverdi = ovreGrense;
            }*/

            if (array[deleverdi] < array[nedreIndex]){
                bytt(array, deleverdi, nedreIndex);
                deleverdi = nedreIndex;

            }
            if (array[deleverdi] > array[ovreIndex]){
                bytt(array, deleverdi, ovreIndex);
                deleverdi = ovreIndex;

                while (array[deleverdi] <= array[deleverdi - 1]){
                    bytt(array, deleverdi, deleverdi - 1);
                    deleverdi--;
                }
            }

            nedreIndex--;
            ovreIndex++;
        }

        if (nedreGrense != deleverdi){
            quicksort(array, nedreIndex, deleverdi - 1);
        }
        if(deleverdi != ovreGrense){
            quicksort(array, deleverdi + 1, ovreIndex);
        }
    }

    public static void bytt(int[] array, int i, int j){
        int k = array[j];
        array[j] = array[i];
        array[i] = k;
    }
}
