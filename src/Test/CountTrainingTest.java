package Test;
import Main.Algorithms.CountTraining;
import Main.Conversions.weather_conversion;
import Main.Conversions.Conversion;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class CountTrainingTest {
    private CountTraining ct;

    @Before
    public void setUp() {
        String[] observed = new String[2];
        observed[0] = "RRRRS";
        observed[1] = "SRRSR";
        String[] states = new String[2];
        states[0] = "LLLHH";
        states[1] = "HHLLH";
        Conversion conversion = new weather_conversion();
        ArrayList<int[]> observed_converted = conversion.observables(observed);
        ArrayList<int[]> states_converted = conversion.states(states,observed);
        ct = new CountTraining(observed_converted,states_converted,2,2);
    }

    @Test
    public void getPi() {
        System.out.println("Pi");
        double[] pi = ct.getPi();
        for (int i = 0; i < 2; i++) {
            System.out.println(pi[i]);
        }
    }

    @Test
    public void getE() {
        System.out.println("E");
        double[][] E = ct.getE();
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
        double[][] P = ct.getP();
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                System.out.print(P[i][j]);
                System.out.print(" ");
            }
            System.out.println();
        }
    }


}