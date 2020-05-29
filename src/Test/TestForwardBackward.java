package Test;

import Main.Algorithms.ForwardBackward;
import Main.Conversions.Conversion;
import Main.Conversions.weather_conversion;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Jens Kristian Refsgaard Nielsen & Thomas Damgaard Vinther
 */
public class TestForwardBackward {
    private ForwardBackward FB;

    @Before
    public void setUp() {
        double[][] P = {{0.9, 0.1},     // H -> H   H ->L
                        {0.2, 0.8}};    // L-> H    L -> L
        double[] pi = {0.5, 0.5};
        double[][] E = {{0.9, 0.1},         // x = sun|H    x= rain|H
                            {0.3, 0.7}};        // x = sun|L    x= rain|L
        String obs = "RRRRS";
        String[] obs_arr = new String[1];
        obs_arr[0] = obs;
        Conversion conv = new weather_conversion();
        int[] obs_int_arr = conv.observables(obs_arr).get(0);
        FB = new ForwardBackward(obs_int_arr,pi,P,E);
    }


    /**
     * Check if n1 in [n2*(1-factor) , n2*(1+factor)], for instance a deviation of 0.01 means within 1%
     * @param n1 first number
     * @param n2 second number
     * @param factor allowed deviation in %
     * @return true if they are close
     */
    private boolean compareFactor(double n1, double n2, double factor){
        double standardDeviation = n2*factor;
        return n2-standardDeviation <= n1 && n1 <= n2+standardDeviation;
    }


    @Test
    public void TestAlpha(){
        double[][] preCalculatedAlpha = PreCalculatedAlpha();
        double[][] alpha = FB.getAlpha();
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 5; j++) {
                boolean cmp = compareFactor(alpha[i][j],preCalculatedAlpha[i][j],0.001);

                assert(cmp);
            }
        }
    }

    @Test
    public void TestBeta(){
        double[][] preCalculatedBeta = PreCalculatedBeta();
        double[][] beta = FB.getBeta();
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 5; j++) {
                boolean cmp = compareFactor(beta[i][j],preCalculatedBeta[i][j],0.001);
                assert(cmp);
            }
        }
    }

    private double[][] PreCalculatedAlpha(){
        double[][] preCalculatedAlpha = new double[2][5];
        preCalculatedAlpha[0][0] = 0.05;
        preCalculatedAlpha[1][0] = 0.35;
        preCalculatedAlpha[0][1] = 0.0115;
        preCalculatedAlpha[1][1] = 0.1995;
        preCalculatedAlpha[0][2] = 0.005025;
        preCalculatedAlpha[1][2] = 0.112525;
        preCalculatedAlpha[0][3] = 0.00270275;
        preCalculatedAlpha[1][3] = 0.0633658;
        preCalculatedAlpha[0][4] = 0.0135950715;
        preCalculatedAlpha[1][4] = 0.015288745;
        return preCalculatedAlpha;
    }
    private double[][] PreCalculatedBeta(){
        double[][] preCalculatedBeta = new double[2][5];
        preCalculatedBeta[0][0] = 0.0124635;
        preCalculatedBeta[1][0] = 0.080745;
        preCalculatedBeta[0][1] = 0.02709;
        preCalculatedBeta[1][1] = 0.14322;
        preCalculatedBeta[0][2] = 0.105;
        preCalculatedBeta[1][2] = 0.252;
        preCalculatedBeta[0][3] = 0.84;
        preCalculatedBeta[1][3] = 0.42;
        preCalculatedBeta[0][4] = 1;
        preCalculatedBeta[1][4] = 1;
        return preCalculatedBeta;
    }

}
