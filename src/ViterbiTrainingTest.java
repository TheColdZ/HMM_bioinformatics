import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class ViterbiTrainingTest {
    private ViterbiTraining vt;

    @Before
    public void setup(){
        double[][] P = {{0.9, 0.1},     // H -> H   H ->L
                        {0.2, 0.8}};    // L-> H    L -> L
        double[] pi = {0.5, 0.5};
        double[][] E = {{0.9, 0.1},         // x = sun|H   x= rain|H
                        {0.3, 0.7}};        // x = sun|L   x= rain|L
        String[] observed = new String[2];
        observed[0] = "CCCCAAAAAACACACA";
        observed[1] = "ACCACCACAACACACACAC";
        Conversion conv = new DNA_conversion();
        vt = new ViterbiTraining(conv.observables(observed), P, E, pi);
    }

    @Test
    public void getPi() {
        System.out.println("Pi");
        double[] pi = vt.getPi();
        for (int i = 0; i < 2; i++) {
            System.out.println(pi[i]);
        }
    }

    @Test
    public void getE() {
        System.out.println("E");
        double[][] E = vt.getE();
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
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
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
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
        states.forEach((ints -> {
            for (int i = 0; i < ints.length; i++) {
                System.out.print(ints[i]);
            }
            System.out.println();
        }));
    }
}