public class Automat {

    char[] alphabet;
    int[][] nextStateArray;
    int[] acceptedState;

    public Automat(char[] alphabet, int[][] nextStateArray, int[] acceptedState){
        this.alphabet = alphabet;
        this.nextStateArray = nextStateArray;
        this.acceptedState = acceptedState;
    }

    //Checks if a input string is valid
    public boolean checkInput(String input){
        char[] inputArray = input.toCharArray();
        int currentState = 0; //0 is the starting state

        for (char letter: inputArray) {
            boolean foundLetterInAlphabet = false;
            int letterAlphabetIndex = 0;


            //Check if the current letter is in the alphabet
            for (int i = 0; i < alphabet.length; i++) {
                if (letter == alphabet[i]) {
                    foundLetterInAlphabet = true;
                    letterAlphabetIndex = i;
                    break;
                }
            }

            if (!foundLetterInAlphabet){
                return false;
            }else {
                currentState = nextStateArray[currentState][letterAlphabetIndex];
            }
        }

        for (int i = 0; i < acceptedState.length; i++) {
            if (currentState == acceptedState[i]){
                return true;
            }
        }

        return false;
    }

    public static void main(String[] args) {

        //Test
        Automat automat = new Automat(new char[]{'0','1'}, new int[][]{
                                                                        {1, 2},
                                                                        {1, 3},
                                                                        {2, 2},
                                                                        {3, 2}
                                                                        }, new int[]{3});

        System.out.println(automat.checkInput(""));
        System.out.println(automat.checkInput("010"));
        System.out.println(automat.checkInput("111"));
        System.out.println(automat.checkInput("010110"));
        System.out.println(automat.checkInput("001000"));

        automat = new Automat(new char[]{'0','1'}, new int[][]{
                {1, 2},
                {1, 3},
                {2, 2},
                {3, 2}
        }, new int[]{3});

    }

}
