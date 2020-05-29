package Test;
import Main.Algorithms.CountTraining;
import Main.Conversions.Conversion;
import Main.Conversions.weather_conversion;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

/**
 *
 * @author Jens Kristian Jensen & Thomas Damgaard Vinther
 */
public class CountTrainingTest {
    private CountTraining example2training;
    private CountTraining example1training;

    @Before
    public void setUp() {
        example1Setup();
        example2Setup();
    }

    private void example1Setup(){
        String[] observed = new String[1];
        String[] states = new String[1];
        observed[0] = "RRRRS";
        states[0]   = "LLLHH";
        Conversion converter = new weather_conversion();
        example1training = new CountTraining(converter.observables(observed),converter.states(states,observed),2,2);
    }
    private void example2Setup(){
        String[] observed = new String[2];
        observed[0] = "RRRRS";
        observed[1] = "SRRSR";
        String[] states = new String[2];
        states[0] = "LLLHH";
        states[1] = "HHLLH";
        Conversion conversion = new weather_conversion();
        ArrayList<int[]> observed_converted = conversion.observables(observed);
        ArrayList<int[]> states_converted = conversion.states(states,observed);
        example2training = new CountTraining(observed_converted,states_converted,2,2);
    }


    @Test
    public void comparePiExample1(){
        double[] pi = example1training.getPi();
        assert(compareFactor(pi[0],0,0.001));
        assert(compareFactor(pi[1],1,0.001));

    }
    @Test
    public void comparePExample1(){
        double[][] P = example1training.getP();
        assert(compareFactor(P[0][0],1,0.001));
        assert(compareFactor(P[0][1],0,0.001));
        assert(compareFactor(P[1][0],1/3.,0.001));
        assert(compareFactor(P[1][1],2/3.,0.001));
    }

    @Test
    public void compareEExample1(){
        double[][] E = example1training.getE();
        assert(compareFactor(E[0][0],0.5,0.001));
        assert(compareFactor(E[0][1],0.5,0.001));
        assert(compareFactor(E[1][0],0,0.001));
        assert(compareFactor(E[1][1],1,0.001));
    }


    @Test
    public void comparePiExample2() {
        double[] pi = example2training.getPi();
        assert(compareFactor(pi[0],0.5,0.001));
        assert(compareFactor(pi[1],0.5,0.001));
    }
    @Test
    public void comparePExample2() {
        double[][] P = example2training.getP();
        assert(compareFactor(P[0][0],2/3.,0.001));
        assert(compareFactor(P[0][1],1/3.,0.001));
        assert(compareFactor(P[1][0],2/5.,0.001));
        assert(compareFactor(P[1][1],3/5.,0.001));

    }

    @Test
    public void compareEExample2() {
        double[][] E = example2training.getE();
        assert(compareFactor(E[0][0],2/5.,0.001));
        assert(compareFactor(E[0][1],3/5.,0.001));
        assert(compareFactor(E[1][0],1/5.,0.001));
        assert(compareFactor(E[1][1],4/5.,0.001));
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