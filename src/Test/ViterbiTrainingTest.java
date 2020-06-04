package Test;

import Main.Algorithms.ViterbiTraining;
import Main.Conversions.Conversion;
import Main.Conversions.WeatherConversion;
import org.junit.Test;

import java.util.ArrayList;

/**
 *
 * @author Jens Kristian Refsgaard Nielsen & Thomas Damgaard Vinther
 */
public class ViterbiTrainingTest {

    @Test
    public void viterbiTrainingExample(){
        double[][] P = {{0.9, 0.1},     // H -> H   H -> L
                {0.2, 0.8}};    // L -> H   L -> L
        double[] pi = {0.5, 0.5};
        double[][] E = {{0.9, 0.1},         // x = sun|H   x = rain|H
                {0.3, 0.7}};        // x = sun|L   x = rain|L
        String[] observed = new String[1];
        observed[0] = "RRRRS";
        Conversion converter = new WeatherConversion();
        ViterbiTraining vt = new ViterbiTraining(converter.observables(observed), P, E, pi);


        pi = vt.getPi();
        assert(compareFactor(pi[0],0,0.001));
        assert(compareFactor(pi[1],1,0.001));
        E = vt.getE();
        assert(compareFactor(E[0][0],0.5,0.001));
        assert(compareFactor(E[0][1],0.5,0.001));
        assert(compareFactor(E[1][0],1/5.,0.001));
        assert(compareFactor(E[1][1],4/5.,0.001));


        P = vt.getP();
        assert(compareFactor(P[0][0],1,0.001));
        assert(compareFactor(P[0][1],0,0.001));
        assert(compareFactor(P[1][0],0,0.001));
        assert(compareFactor(P[1][1],1,0.001));



        ArrayList<int[]> states = vt.getStates();
        String[] strings = converter.states(states);
        assert(strings[0].equals("LLLLL"));
    }
    @Test
    public void example2(){

        String[] observed = new String[4];
        observed[0] = "RRRRRSSRSRSRSRSR";
        observed[1] = "RSRRSRSRSRSRSRSRRR";
        observed[2] = "RRSSRRRRRRRRSSSSRSRSRS";
        observed[3] = "RRRRRSSSSSRRRRRRSSSSSR";
        Conversion converter = new WeatherConversion();
        ViterbiTraining vt = new ViterbiTraining(converter.observables(observed), converter.getInitialP(), converter.getInitialE(), converter.getInitialPi());

        double[] pi = vt.getPi();
        assert(compareFactor(pi[0],0,0.001));
        assert(compareFactor(pi[1],1,0.001));

        double[][] E = vt.getE();
        assert(compareFactor(E[0][0],1.,0.001));
        assert(compareFactor(E[0][1],0,0.001));
        assert(compareFactor(E[1][0],0.281,0.001));
        assert(compareFactor(E[1][1],0.718,0.001));


        double[][] P = vt.getP();
        assert(compareFactor(P[0][0],0.785,0.001));
        assert(compareFactor(P[0][1],0.214,0.001));
        assert(compareFactor(P[1][0],0.05,0.001));
        assert(compareFactor(P[1][1],0.95,0.001));



        ArrayList<int[]> states = vt.getStates();
        String[] strings = converter.states(states);
        assert(strings[0].equals("LLLLLLLLLLLLLLLL"));
        assert(strings[1].equals("LLLLLLLLLLLLLLLLLL"));
        assert(strings[2].equals("LLLLLLLLLLLLHHHHLLLLLL"));
        assert(strings[3].equals("LLLLLHHHHHLLLLLLHHHHHL"));
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