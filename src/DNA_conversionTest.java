import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class DNA_conversionTest {
    ArrayList<int[]> observed_converted;
    ArrayList<int[]> states_converted;
    private int L;
    private int K;

    @Before
    public void setUp() {
        String[] observed = new String[2];
        observed[0] = "CCCCA";
        observed[1] = "ACCAC";
        String[] states = new String[2];
        states[0] = "NNNCC";
        states[1] = "CCNNC";
        Conversion conversion = new DNA_conversion(observed,states);
        observed_converted = conversion.getObservables();
        states_converted = conversion.getStates();
        L = 2;
        K = 5;
    }

    @Test
    public void getObservables() {
        System.out.println("Observed");
        for (int l = 0; l < L; l++) {
            for (int k = 0; k < K; k++) {
                System.out.print(observed_converted.get(l)[k]);
                System.out.print(" ");
            }
            System.out.println();
        }
    }

    @Test
    public void getStates() {
        System.out.println("States");
        for (int l = 0; l < L; l++) {
            for (int k = 0; k < K; k++) {
                System.out.print(states_converted.get(l)[k]);
                System.out.print(" ");
            }
            System.out.println();
        }
    }
}