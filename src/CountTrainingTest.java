import org.junit.Before;
import org.junit.Test;

public class CountTrainingTest {
    private CountTraining ct;

    @Before
    public void setUp() {
        String[] observed = new String[1];
        observed[0] = "CCCCA";
        String[] states = new String[1];
        states[0] = "CCCNN";
        ct = new CountTraining(observed,states,2,2);
    }

    @Test
    public void getPi() {
        double[] pi = ct.getPi();
        for (int i = 0; i < 2; i++) {
            System.out.println(pi[i]);
        }
    }

    @Test
    public void getE() {
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