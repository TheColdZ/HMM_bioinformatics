package Test;

import Main.*;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class ViterbiTrainingTest {
    private ViterbiTraining vt;

    @Before
    public void setup(){
        setup1();
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
        Conversion converter = new weather_conversion();
        vt = new ViterbiTraining(converter.observables(observed), P, E, pi);
    }

    private void setup2(){
        double[][] P = {{0.5, 0.5},     // H -> H   H -> L
                {0.5, 0.5}};    // L -> H   L -> L
        double[] pi = {0.5, 0.5};
        double[][] E = {{0.5, 0.5},         // x = sun|H   x = rain|H
                {0.5, 0.5}};        // x = sun|L   x = rain|L
        String[] observed = new String[4];
        observed[0] = "RRRRRSSRSRSRSRSR";
        observed[1] = "RSRRSRSRSRSRSRSRRR";
        observed[2] = "RRSSRRRRRRRRSSSSRSRSRS";
        observed[3] = "RRRRRSSSSSRRRRRRSSSSSR";
        Conversion converter = new weather_conversion();
        vt = new ViterbiTraining(converter.observables(observed), P, E, pi);
    }

    @Test
    public void getPi() {
        System.out.println("Pi");
        double[] pi = vt.getPi();
        for (int i = 0; i < pi.length; i++) {
            System.out.println(pi[i]);
        }
    }

    @Test
    public void getE() {
        System.out.println("E");
        double[][] E = vt.getE();
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
        double[][] P = vt.getP();
        for (int i = 0; i < P.length; i++) {
            for (int j = 0; j < P[0].length; j++) {
                System.out.print(P[i][j]);
                System.out.print(" ");
            }
            System.out.println();
        }
    }

    @Test
    public void getStates() {
        System.out.println("states");
        ArrayList<int[]> states = vt.getStates();
        Conversion conv = new weather_conversion();
        String[] strings = conv.states(states);
        for (String str : strings) {
            System.out.println(str);
        }
    }
}