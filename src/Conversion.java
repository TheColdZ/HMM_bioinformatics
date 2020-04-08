import java.util.ArrayList;

/**
 * This interface handles conversions from data to int arrays
 */
public interface Conversion {
    ArrayList<int[]> observables(String[] observables);
    ArrayList<int[]> states(String[] states);
    String[] observables(ArrayList<int[]> observables);
    String[] states(ArrayList<int[]> states);
}
