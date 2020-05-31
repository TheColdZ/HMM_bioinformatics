package Test;

import Main.Algorithms.BaumWelchTraining;
import Main.Algorithms.ForwardBackward;
import Main.Conversions.Conversion;
import Main.Conversions.weatherConversion;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

/**
 *
 * @author Jens Kristian Refsgaard Nielsen & Thomas Damgaard Vinther
 */
public class BaumWelchTrainingTest {
    private BaumWelchTraining training;

    @Before
    public void setup(){

    }

    private void setup1(){
        double[][] P = {{0.9, 0.1},     // H -> H   H -> L
                        {0.2, 0.8}};    // L -> H   L -> L
        double[] pi = {0.5, 0.5};
        double[][] E = {{0.9, 0.1},         // x = sun|H   x = rain|H
                        {0.3, 0.7}};        // x = sun|L   x = rain|L
        String[] observed = new String[4];
        observed[0] = "RRRRRSSRSRSRSRSR";
        observed[1] = "RSRRSRSRSRSRSRSRRR";
        observed[2] = "RRSSRRRRRRRRSSSSRSRSRS";
        observed[3] = "RRRRRSSSSSRRRRRRSSSSSR";
        Conversion converter = new weatherConversion();
        training = new BaumWelchTraining(converter.observables(observed),P,E,pi,0.00000000001);
    }

    @Test
    public void BaumWelchTrainingExample(){
        double[][] P = {{0.9, 0.1},     // H -> H   H -> L
                {0.2, 0.8}};    // L -> H   L -> L
        double[] pi = {0.5, 0.5};
        double[][] E = {{0.9, 0.1},         // x = sun|H   x = rain|H
                {0.3, 0.7}};        // x = sun|L   x = rain|L
        String[] observed = new String[1];
        observed[0] = "RRRRS";
        Conversion converter = new weatherConversion();

        training = new BaumWelchTraining(converter.observables(observed),P,E,pi,1.0); //we make sure it only runs one time with comparefator= 1;

        ArrayList<int[]> observedConverted = converter.observables(observed);
        ForwardBackward forwardBackward = new ForwardBackward(observedConverted.get(0),pi,P,E);
        double[][] alpha = forwardBackward.getAlpha();

        System.out.println("alpha");        //TODO kill and clean entire example
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j <5 ; j++) {
                System.out.print(alpha[i][j]);
                System.out.print(" ");
            }
            System.out.println();
        }
        double[][] beta = forwardBackward.getBeta();
        System.out.println("beta");
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j <5 ; j++) {
                System.out.print(beta[i][j]);
                System.out.print(" ");
            }
            System.out.println();
        }

        P = training.getP();
        pi = training.getPi();
        E = training.getE();

        System.out.println("Pi");
        for (int i = 0; i < pi.length; i++) {
            System.out.println(pi[i]);
        }
        System.out.println("E:");

        for (int i = 0; i < E.length; i++) {
            for (int j = 0; j < E[i].length; j++) {
                System.out.print(E[i][j]);
                System.out.print(" ");
            }
            System.out.println();
        }
        System.out.println("P:");
        for (int i = 0; i < P.length; i++) {
            for (int j = 0; j < P[0].length; j++) {
                System.out.print(P[i][j]);
                System.out.print(" ");
            }
            System.out.println();
        }



        boolean cmpPi1 = compareFactor(pi[0],(0.05*0.0124635)/0.028838165,0.01);

        assert(cmpPi1);
        boolean cmpPi2 = compareFactor(pi[1],(0.35*0.080745)/0.028838165,0.01);

        assert(cmpPi2);

        double numeratorP = (0.05*0.9*0.1*0.02709+0.0115*0.9*0.1*0.105+0.005025*0.9*0.1*0.84+0.00270275*0.9*0.9*1.0);
        double denominatorP = (0.05*0.0124635+0.0115*0.02709+0.005025*0.105+0.00270275*0.84);

        boolean cmpP11 = compareFactor(P[0][0],numeratorP/denominatorP,0.001);
        assert(cmpP11);
        assert(compareFactor(P[0][1],0.24994,0.001));
        assert(compareFactor(P[1][0],0.12436,0.001));
        assert(compareFactor(P[1][1],0.87563,0.001));


        double numeratorE = 0.35*0.080745*1+0.1995*0.14322*1+0.112525*1+0.0633658*0.41*1;
        double denominatorE = 0.35*0.080745+0.1995*0.14322+0.112525+0.0633658*0.41;

        boolean cmpE22 = compareFactor(E[1][1],numeratorE/denominatorE,0.01);
        assert (cmpE22);
        assert(compareFactor(E[0][0],0,0.001));
        assert(compareFactor(E[0][1],1,0.001));
        assert(compareFactor(E[1][0],0,0.001));
    }

    /**
     * Check if n1 in [n2*(1-factor) , n2*(1+factor)], for instance a deviation of 0.01 means within 1%
     * @param n1 first number
     * @param n2 second number
     * @param factor allowed deviation in %
     * @return true if they are close
     */
    private boolean compareFactor(double n1, double n2, double factor){
        double standardDeviation = factor;
        return n2-standardDeviation <= n1 && n1 <= n2+standardDeviation;
    }

    @Test
    public void getPi() {
        System.out.println("Pi");
        double[] pi = training.getPi();
        for (int i = 0; i < pi.length; i++) {
            System.out.println(pi[i]);
        }
    }

    @Test
    public void getE() {
        System.out.println("E");
        double[][] E = training.getE();
        for (int i = 0; i < E.length; i++) {
            for (int j = 0; j < E[i].length; j++) {
                System.out.print(E[i][j]);
                System.out.print(" ");
            }
            System.out.println();
        }
    }

    @Test
    public void getP() {
        System.out.println("P");
        double[][] P = training.getP();
        for (int i = 0; i < P.length; i++) {
            for (int j = 0; j < P[0].length; j++) {
                System.out.print(P[i][j]);
                System.out.print(" ");
            }
            System.out.println();
        }
    }
}
