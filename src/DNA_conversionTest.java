import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class DNA_conversionTest {
    private Conversion conversion;
    private String[] observed_str;
    private String[] states_str;
    private ArrayList<int[]> observed_ints;
    private ArrayList<int[]> states_ints;

    @Before
    public void setUp() {
        conversion = new DNA_conversion();
        observed_str = new String[2];
        observed_str[0] = "CCCCA";
        observed_str[1] = "ACCAC";
        states_str = new String[2];
        states_str[0] = "NNNCC";
        states_str[1] = "CCNNC";
        observed_ints = new ArrayList<>();
        int[] ints = new int[5];
        ints[0] = 1; ints[1] = 1; ints[2] = 1; ints[3] = 1; ints[4] = 0;
        observed_ints.add(ints);
        ints = new int[5];
        ints[0] = 0; ints[1] = 1; ints[2] = 1; ints[3] = 0; ints[4] = 1;
        observed_ints.add(ints);
        states_ints = new ArrayList<>();
        ints = new int[5];
        ints[0] = 1; ints[1] = 1; ints[2] = 1; ints[3] = 0; ints[4] = 0;
        states_ints.add(ints);
        ints = new int[5];
        ints[0] = 0; ints[1] = 0; ints[2] = 1; ints[3] = 1; ints[4] = 0;
        states_ints.add(ints);
    }

    @Test
    public void getObservables_str_to_int() {
        ArrayList<int[]> observed_converted = conversion.observables(observed_str);
        for (int i = 0; i < observed_converted.size(); i++) {
            for (int j = 0; j < observed_converted.get(i).length; j++) {
                assertThat(observed_ints.get(i)[j],is(observed_converted.get(i)[j]));
            }
        }

    }

    @Test
    public void getStates_str_to_int() {
        ArrayList<int[]> states_converted = conversion.states(states_str);
        for (int i = 0; i < states_converted.size(); i++) {
            for (int j = 0; j < states_converted.get(i).length; j++) {
                assertThat(states_ints.get(i)[j],is(states_converted.get(i)[j]));
            }
        }
    }

    @Test
    public void getObservables_int_to_str(){
        String[] observed_converted = conversion.observables(observed_ints);
        for (int i = 0; i < observed_converted.length; i++) {
            assertThat(observed_str[i],is(observed_converted[i]));
        }
    }
    @Test
    public void getStates_int_to_str(){
        String[] states_converted = conversion.states(states_ints);
        for (int i = 0; i < states_converted.length; i++) {
            assertThat(states_str[i],is(states_converted[i]));
        }
    }

    private void print_matr(ArrayList<int[]> input, String name){
        System.out.println(name);
        for (int[] ints : input) {
            for (int k = 0; k < ints.length; k++) {
                System.out.print(ints[k]);
                System.out.print(" ");
            }
            System.out.println();
        }
    }


    @Test
    public void convertStates7States(){
        String trueAnnotation = "NNCCCCCCCCCNNRRR";
        int[] preCalculated = new int[]{ 3, 3, 2, 1, 0, 2, 1, 0, 2, 1, 0, 3, 3, 4, 5, 6 };
        DNA_conversion conversion = new DNA_conversion();
        int [] states = conversion.convertAnnotationToState7States(trueAnnotation);
        for (int i = 0; i < trueAnnotation.length(); i++) {
            assert(states[i]==preCalculated[i]);
        }
    }

    @Test
    public void convertStates7statemodel(){
        String[] trueAnnotation = new String[1];
        trueAnnotation[0] = "NNCCCCCCCCCNNRRR";
        int[] preCalculated = new int[]{ 3, 3, 2, 1, 0, 2, 1, 0, 2, 1, 0, 3, 3, 4, 5, 6 };
        DNAConversion7States conversion = new DNAConversion7States();
        ArrayList<int[]> states = conversion.states(trueAnnotation);
        int[] statesInt = states.get(0);
        for (int i = 0; i < trueAnnotation[0].length(); i++) {
            assert(statesInt[i]==preCalculated[i]);
        }
    }
}