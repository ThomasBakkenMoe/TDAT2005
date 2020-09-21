import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class BracketCheck {

    public static void main(String[] args) throws Exception {
        File fil = new File("src/BracketCheck.java");
        FileReader fileReader = new FileReader(fil);
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        ArrayList<Character> stack = new ArrayList<>();

        String currentLine;
        int lineNumber = 0;
        boolean ignore = false;

        while ((currentLine = bufferedReader.readLine()) != null) {

            for (int i = 0; i < currentLine.length(); i++) {

                if (currentLine.charAt(i) == '\'' || currentLine.charAt(i) == '\"') {
                    ignore = !ignore;
                }

                if ((currentLine.charAt(i) == '(' || currentLine.charAt(i) == '{' || currentLine.charAt(i) == '[') && !ignore) {
                    stack.add(currentLine.charAt(i));
                } else if ((currentLine.charAt(i) == ')' || currentLine.charAt(i) == '}' || currentLine.charAt(i) == ']') && !ignore) {

                    switch (currentLine.charAt(i)) {

                        case ')':
                            try {
                                compareToStack('(', stack);
                            } catch (Exception e) {
                                compileError(lineNumber, i);
                                System.exit(1);
                            }
                            break;

                        case '}':
                            try {
                                compareToStack('{', stack);
                            } catch (Exception e) {
                                compileError(lineNumber, i);
                                System.exit(1);
                            }
                            break;

                        case ']':
                            try {
                                compareToStack('[', stack);
                            } catch (Exception e) {
                                compileError(lineNumber, i);
                                System.exit(1);
                            }
                            break;
                    }
                }
            }

            lineNumber++;
        }

        // kommentar (
        if (stack.size() > 0) {
            System.out.println("Error, reached end of file while still parsing, expecting: " + stack.get(stack.size() - 1));

            switch (stack.get(stack.size() - 1)) {

                case '(':
                    System.out.println("Error, reached end of file while still parsing, expecting: )");
                    break;

                case '{':
                    System.out.println("Error, reached end of file while still parsing, expecting: }");
                    break;

                case '[':
                    System.out.println("Error, reached end of file while still parsing, expecting: ]");
                    break;
            }

            System.exit(1);
        }

        bufferedReader.close();
        fileReader.close();

    }

    private static void compareToStack(char character, ArrayList<Character> stack) throws Exception {
        if (character == stack.get(stack.size() - 1)) {
            stack.remove(stack.size() - 1);
        } else {
            throw new Exception("1");
        }
    }

    private static void compileError(int line, int character) {

        System.out.println("Error at line " + line + ", character " + character);

    }
}