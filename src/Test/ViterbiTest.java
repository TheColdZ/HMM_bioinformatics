package Test;

import Main.Algorithms.Viterbi;
import Main.Conversions.Conversion;
import Main.Conversions.WeatherConversion;
import org.junit.Test;

/**
 *
 * @author Jens Kristian Refsgaard Nielsen & Thomas Damgaard Vinther
 */
public class ViterbiTest {

    /**
     * Check if n1 in [n2*(1-factor) , n2*(1+factor)], for instance a deviation of 0.01 means within 1%
     * @param n1 first number
     * @param n2 second number
     * @param factor allowed deviation in %
     * @return true if they are close
     */
    private boolean compareFactor(double n1, double n2, double factor){
        double standardDeviation = factor*n2;
        return n2-standardDeviation <= n1 && n1 <= n2+standardDeviation;
    }

    @Test
    public void rainExample(){
        double[][] P = {{0.9, 0.1},     // H -> H   H ->L
                        {0.2, 0.8}};    // L-> H    L -> L
        double[] pi = {0.5, 0.5};
        double[][] E = {{0.9, 0.1},         // x = sun|H    x= rain|H
                        {0.3, 0.7}};        // x = sun|L   x= rain|L


        Viterbi viterbi = new Viterbi(P,E,pi);
        String observed = "RRRRS";
        String[] obs = new String[1];
        obs[0] = observed;
        Conversion conv = new WeatherConversion();
        viterbi.calculate(conv.observables(obs).get(0));
        double[][] delta = viterbi.getDelta();
        double[][] preCalculatedDelta = preCalculatedDelta();
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 5; j++) {
                boolean cmp = compareFactor(Math.exp(delta[i][j]),preCalculatedDelta[i][j],0.001);
                assert(cmp);
            }
        }
    }



    private double[][] preCalculatedDelta(){
        double[][] preCalculatedDelta = new double[2][5];
        preCalculatedDelta[0][0] = 0.05;
        preCalculatedDelta[1][0] = 0.35;
        preCalculatedDelta[0][1] = 0.007;
        preCalculatedDelta[1][1] = 0.196;
        preCalculatedDelta[0][2] = 0.00392;
        preCalculatedDelta[1][2] = 0.10976;
        preCalculatedDelta[0][3] = 0.0021952;
        preCalculatedDelta[1][3] = 0.0614656;
        preCalculatedDelta[0][4] = 0.011063808;
        preCalculatedDelta[1][4] = 0.014751744;
        return preCalculatedDelta;
    }



}
