public class JosephusAlgorithm {

    public static void main(String[] args) {

        int interval = 3;
        int length = 2;
        CircularList circularList= new CircularList(length);

        while (circularList.getLength() > 1){
            for (int i = 1; i < interval; i++) {

                System.out.println("Current: " + circularList.getCurrent().getId());

                circularList.next();
            }

            System.out.println("Kill: " + circularList.getCurrent().getId());

            circularList.removeCurrentNode();
        }

        System.out.println("\n" + "Soldier number " + circularList.getCurrent().getId() + " survives");
    }
}