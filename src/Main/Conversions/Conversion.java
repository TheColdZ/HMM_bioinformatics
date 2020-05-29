package Main.Conversions;

import java.util.ArrayList;

/**
 * This interface handles conversions from data to int arrays according to the appropriate model. It also holds the name of the model, number of states
 * and initial parameters for a model. Notice the overload of methods for ease of use.
 * @author Jens Kristian Refsgaard Nielsen & Thomas Damgaard Vinther
 */
public interface Conversion {
    ArrayList<int[]> observables(String[] observables);

    ArrayList<int[]> states(String[] trueAnnotation, String[] observed);

    String[] observables(ArrayList<int[]> observables);

    String[] states(ArrayList<int[]> states);

    String getNameOfModel();

    int getNumberOfStates();

    double[][] getInitialP();

    double[][] getInitialE();

    double[] getInitialPi();
}