import java.util.Date;

public class Øving2 {

    public static void main(String[] args) {

        double base = 1.0001;
        int exponent = 300000;

        System.out.println("Base: " + base);
        System.out.println("Eksponent: " + exponent);

        Date start = new Date();
        int runder = 0;
        double tid;
        Date slutt;
        do {
            exponentAlt(base, exponent);
            slutt = new Date();
            ++runder;
        } while (slutt.getTime()-start.getTime() < 1000);
        tid = (double) (slutt.getTime()-start.getTime()) / runder;

        System.out.println("Millisekund pr. runde (2.2-3):" + tid);
        System.out.println("Resultat (2.1-1): " + exponentAlt(base, exponent));

/*
        start = new Date();
        runder = 0;
        do {
            exponentAlt(base, exponent);
            slutt = new Date();
            ++runder;
        } while (slutt.getTime()-start.getTime() < 1000);
        tid = (double) (slutt.getTime()-start.getTime()) / runder;

        System.out.println("Millisekund pr. runde (2.2-3):" + tid);
        System.out.println("Resultat (2.2-3): " + exponentAlt(base, exponent));


        start = new Date();
        runder = 0;
        do {
            Math.pow(base, exponent);
            slutt = new Date();
            ++runder;
        } while (slutt.getTime()-start.getTime() < 1000);
        tid = (double) (slutt.getTime()-start.getTime()) / runder;

        System.out.println("Millisekund pr. runde (Innebygd):" + tid);
        System.out.println("Resultat (Innebygd): " + Math.pow(base, exponent));*/


    }

    static double exponent(double base, int exponent){

        if (exponent == 0){
            return 1;
        }else {

            return base * exponent(base, exponent-1);
        }
    }

    static double exponentAlt(double base, int expontent){

        if (expontent == 0){
            return 1;

        } else if(expontent % 2 == 0){ //partall
            return exponentAlt((base * base), expontent/2);

        } else{ //oddetall
            return base * exponentAlt(base * base, (expontent - 1)/2);
        }
    }
}
