package Test;

import Main.Algorithms.ForwardBackwardScaled;
import Main.Conversions.Conversion;
import Main.Algorithms.ForwardBackward;
import Main.Conversions.DNAConversion.DNAConversion3States;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Jens Kristian Refsgaard Nielsen & Thomas Damgaard Vinther
 */
public class TestForwardBackwardScaled {
    private ForwardBackward FB;
    private ForwardBackwardScaled FBs;

    @Before
    public void setUp() {
        double[][] P = {{0.9, 0.1},     // H -> H   H ->L
                {0.2, 0.8}};    // L-> H    L -> L
        double[] pi = {0.5, 0.5};
        double[][] E = {{0.9, 0.1},         // x = sun|H    x= rain|H
                {0.3, 0.7}};        // x = sun|L    x= rain|L
        String obs = "CCCCA";
        String[] obs_arr = new String[1];
        obs_arr[0] = obs;
        Conversion conv = new DNAConversion3States();
        int[] obs_int_arr = conv.observables(obs_arr).get(0);
        FB = new ForwardBackward(obs_int_arr,pi,P,E);
        FBs = new ForwardBackwardScaled(obs_int_arr,pi,P,E);
    }

    /**
     * This should fail
     */
    @Test
    public void testTermination(){
        double b = 0;
        for (int i = 0; i < 2; i++) {
            b += FBs.getAlpha()[i][1];
        }
        assert(compareFactor(1,b,0.01));
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
}
