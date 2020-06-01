package Test;

import Main.Algorithms.BaumWelchTraining;
import Main.Conversions.Conversion;
import Main.Conversions.weatherConversion;
import org.junit.Test;

/**
 *
 * @author Jens Kristian Refsgaard Nielsen & Thomas Damgaard Vinther
 */
public class BaumWelchTrainingTest {

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

        BaumWelchTraining training = new BaumWelchTraining(converter.observables(observed),P,E,pi,1.0); //we make sure it only runs one time with comparefator= 1;

        P = training.getP();
        pi = training.getPi();
        E = training.getE();

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


        double numeratorE = 0.35*0.080745*1+0.1995*0.14322*1+0.112525*0.252*1+0.0633658*0.42*1;
        double denominatorE = 0.35*0.080745+0.1995*0.14322+0.112525*0.252+0.0633658*0.42;
        boolean cmpE22 = compareFactor(E[1][1],numeratorE/denominatorE,0.01);
        assert (cmpE22);
        assert(compareFactor(E[0][0],0,0.001));
        assert(compareFactor(E[0][1],1,0.001));
        assert(compareFactor(E[1][0],0,0.001));
    }

    @Test
    public void BaumWelchTrainingExample2(){
        double[][] P = {{0.9, 0.1},     // H -> H   H -> L
                        {0.2, 0.8}};    // L -> H   L -> L
        double[] pi = {0.5, 0.5};
        double[][] E = {{0.9, 0.1},         // x = sun|H   x = rain|H
                        {0.3, 0.7}};        // x = sun|L   x = rain|L
        String[] observed = new String[2];
        observed[0] = "RRRRS";
        observed[1] ="SRRSR";
        Conversion converter = new weatherConversion();

        BaumWelchTraining training = new BaumWelchTraining(converter.observables(observed),P,E,pi,1.0); //we make sure it only runs one time with comparefator= 1;


        P = training.getP();
        pi = training.getPi();
        E = training.getE();


        double newNumerator = (0.45*0.9*0.1*0.02499+0.0435*0.9*0.1*0.147+0.006225*0.9*0.9*0.16+0.0172375*0.9*0.1*1)/0.012292874999999998;
        double oldNumerator = (0.05*0.9*0.1*0.02709+0.0115*0.9*0.1*0.105+0.005025*0.9*0.1*0.84+0.00270275*0.9*0.9*1.0)*(1/0.0288838165);
        double numerator = oldNumerator+newNumerator;

        double newDenominator = (0.45*0.0090405+0.0435*0.02499+0.006225*0.147+0.01723275*0.16)*(1/0.012292874999999998);
        double oldDenominator = (0.05*0.0124635+0.0115*0.02709+0.005025*0.105+0.00270275*0.84)*(1/0.028883925);
        double denominator = newDenominator+oldDenominator;

        boolean cmpP11 = compareFactor(P[0][0],numerator/denominator,0.001);
        assert(cmpP11);
        assert(compareFactor(P[0][1],0.50684,0.001));
        assert(compareFactor(P[1][0],0.09795,0.001));
        assert(compareFactor(P[1][1],0.902059,0.001));

        double newNumeratorE =  (0.15*0.054831*0+0.1155*0.09702*1+0.067725*0.168*1+0.01644075*0.58*0)/0.012292874999999998;
        double newDenominatorE = (0.15*0.054831+0.1155*0.09702+0.067725*0.168+0.01644075*0.58)*(1/0.012292874999999998);

        double oldNumeratorE = (0.35*0.080745*1+0.1995*0.14322*1+0.112525*0.252*1+0.0633658*0.42*1)/0.028883925;
        double oldDenominatorE = (0.35*0.080745+0.1995*0.14322+0.112525*0.252+0.0633658*0.42)/0.028883925;
        double numeratorE = newNumeratorE+oldNumeratorE;
        double denominatorE = newDenominatorE+oldDenominatorE;

        double newE11 = numeratorE/denominatorE;

        assert(compareFactor(E[1][1],newE11,0.00001));
        assert(compareFactor(E[0][0],0.65527,0.001));
        assert (compareFactor(E[0][1],0.344726,0.001));
        assert(compareFactor(E[1][0],0.2019,0.001));



        double oldPi1=(0.05*0.0124635)/0.028838165;
        double newPi1= (0.45*0.0090405)/0.012292875;
        double pi1 = 0.5*(oldPi1+newPi1);

        boolean cmpPi1 = compareFactor(pi[0],pi1,0.001);
        assert(cmpPi1);
        assert(compareFactor(pi[1],1-pi1,0.001));
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

}
