import java.util.Date;
import java.util.Random;

public class Øving1 {

    public static void main(String[] args) {

        int itterasjoner = 10000000;

        int[] datasett = genererDatasett(itterasjoner);

        Resultat resultat;
        Date start = new Date();
        int runder = 0;
        double tid;
        Date slutt;
        do {
            resultat = algoritme(datasett);
            slutt = new Date();
            ++runder;
        } while (slutt.getTime()-start.getTime() < 1000);
        tid = (double) (slutt.getTime()-start.getTime()) / runder;

        System.out.println("Antall Itterasjoner: " + itterasjoner);
        System.out.println("Millisekund pr. runde:" + tid);
        System.out.println("Kjøpsdag: dag " + resultat.getKjopsdag());
        System.out.println("Salgsdag: dag " + resultat.getSalgsdag());
        System.out.println("Fortjeneste: " + resultat.getFortjeneste());



    }

    private static Resultat algoritme(int [] datasett){

        int kjopsdag = 0;
        int salgsdag = 0;
        int potensiellsalgsdag = 0;
        int potensiellkjopsdag = 0;
        int prisdelta = kjopsdag;
        int salgsdagsdelta = 0;


        for (int i = 0; i < datasett.length; i++) {


            prisdelta += datasett[i];

            if (prisdelta < 0){
                potensiellkjopsdag = i;
                prisdelta = 0;
            } else if (prisdelta > 0){
                potensiellsalgsdag = i;
            }

            if (salgsdagsdelta < prisdelta){
                kjopsdag = potensiellkjopsdag;
                salgsdag = potensiellsalgsdag;
                salgsdagsdelta = prisdelta; //6 + 10n
            }

        }

        return new Resultat(kjopsdag + 1, salgsdag + 1, salgsdagsdelta);

    }

    private static int[] genererDatasett(int itterasjoner){

        int[] datasett = new int[itterasjoner];

        Random random = new Random();

        for (int i = 0; i < itterasjoner; i++) {
            datasett[i] = random.nextInt(20) - 10;
        }

        return datasett;
    }
}

class Resultat{

    //Variables
    int kjopsdag;
    int salgsdag;
    int fortjeneste;

    Resultat(int kjopsdag, int salgsdag, int fortjeneste){

        this.kjopsdag = kjopsdag;
        this.salgsdag = salgsdag;
        this.fortjeneste = fortjeneste;
    }

    public int getKjopsdag() {
        return kjopsdag;
    }

    public int getSalgsdag() {
        return salgsdag;
    }

    public int getFortjeneste() {
        return fortjeneste;
    }
}
