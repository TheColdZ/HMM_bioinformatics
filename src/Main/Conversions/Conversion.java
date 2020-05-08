package Main.Conversions;

import java.util.ArrayList;

/**
 * This interface handles conversions from data to int arrays
 */
public interface Conversion {
    ArrayList<int[]> observables(String[] observables);
    ArrayList<int[]> states(String[] trueAnnotation,String[] observed);
    String[] observables(ArrayList<int[]> observables);
    String[] states(ArrayList<int[]> states);
    String getNameOfModel();
    int getNumberOfStates();
}
