import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

public class TestForwardBackward {
    private ForwardBackward FB;

    @Before
    public void setUp() {
        double[][] P = {{0.9 , 0.1},     // H -> H   H ->L
                {0.2, 0.8}};    // L-> H    L -> L
        double[] pi = {0.5, 0.5};
        double[][] Ematr = {{0.9, 0.1},         // x = sun|H    x= rain|H
                {0.3, 0.7}};        // x = sun|L   x= rain|L
        EmissionProbability E = new EmissionProbability(Ematr);
        String obs = "CCCCA";
        FB = new ForwardBackward(obs,pi,P,E);
    }



    @Test
    public void TestAlpha(){
        System.out.println("alpha "+Arrays.deepToString(FB.calculateAlpha()));
        assert(true);
    }

    @Test
    public void TestBeta(){
        System.out.println("beta " +Arrays.deepToString(FB.calculateBeta()));
    }

}
