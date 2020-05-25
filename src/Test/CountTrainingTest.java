package Test;
import Main.Algorithms.BaumWelchTraining;
import Main.Algorithms.CountTraining;
import Main.Conversions.weather_conversion;
import Main.Conversions.Conversion;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.function.Function;

public class CountTrainingTest {
    private CountTraining ct;
    private CountTraining training;

    @Before
    public void setUp() {
        setup1();
        setup2();
    }
    private void setup1(){
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
    private void setup2(){
        String[] observed = new String[4];
        String[] states = new String[4];
        observed[0] = "RRRRRSSRSRSRSRSR";
        states[0]   = "LHHHLHLHHLLLHHHH";
        observed[1] = "RSRRSRSRSRSRSRSRRR";
        states[1]   = "HLHLHLHLHLHLHHLHHH";
        observed[2] = "RRSSRRRRRRRRSSSSRSRSRS";
        states[2]   = "HLLLLHHHHLLLLLLHHLHLHL";
        observed[3] = "RRRRRSSSSSRRRRRRSSSSSR";
        states[3]   = "HHHHLLLLLLLLHHHHHHLLLL";
        Conversion converter = new weather_conversion();
        training = new CountTraining(converter.observables(observed),converter.states(states,observed),2,2);
    }

    @Test
    public void getPiSmall() {
        System.out.println("Pi small");
        double[] pi = ct.getPi();
        for (int i = 0; i < 2; i++) {
            System.out.println(pi[i]);
        }
    }

    @Test
    public void getE() {
        System.out.println("E small");
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
        System.out.println("P small");
        double[][] P = ct.getP();
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                System.out.print(P[i][j]);
                System.out.print(" ");
            }
            System.out.println();
        }

    }

    @Test
    public void getPiLarge(){
        System.out.println("Pi large");
        double[] pi = training.getPi();
        for (int i = 0; i < 2; i++) {
            System.out.println(pi[i]);
        }
    }

    @Test
    public void getELarge(){
        System.out.println("E large");
        double[][] E = training.getE();
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                System.out.print(E[i][j]);
                System.out.print(" ");
            }
            System.out.println();
        }
    }
    @Test
    public void getPLarge(){
        System.out.println("P large");
        double[][] P = training.getP();
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                System.out.print(P[i][j]);
                System.out.print(" ");
            }
            System.out.println();
        }
    }

}