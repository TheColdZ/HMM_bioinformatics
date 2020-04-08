import java.util.ArrayList;

/**
 * This interface handles conversions from data to int arrays
 */
public interface Conversion {
    ArrayList<int[]> getObservables();
    ArrayList<int[]> getStates();
}
