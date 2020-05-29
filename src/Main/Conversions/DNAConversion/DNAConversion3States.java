package Main.Conversions.DNAConversion;

import Main.Conversions.Conversion;

import java.util.ArrayList;

/**
 *  This class handles conversions from observable data given as a String an int array and vice versa from an int array to a String.
 *  It does so for the 3-state model.
 *  It also holds initial parameters for the 3-state model.
 * @author Jens Kristian Refsgaard Nielsen & Thomas Damgaard Vinther
 */
public class DNAConversion3States implements Conversion {

    /**
     * Converts an emission char to an Int
     * @param c The char to convert
     * @return  Converted char as int.
     */
    private int emissionConversionCharToInt(Character c){
        switch(c){
            case 'A': return 0;

            case 'C': return 1;

            case 'G': return 2;

            case 'T': return 3;

            default: throw new RuntimeException("conversion error, observable char to int");
        }
    }

    /**
     * Converts a state as char to an Int
     * @param c The char to convert
     * @return  Converted char as int.
     */
    private int stateConversionCharToInt(Character c){
        switch(c){
            case 'C': return 0;

            case 'N': return 1;

            case 'R': return 2;

            default: throw new RuntimeException("conversion error, state char to int");
        }

    }

    /**
     * Method used for both states and observables conversion from String to int
     * @param strings   Array of String to Convert
     * @param observables   If it is the observables or states to convert.
     * @return The converted string(s)
     */
    private ArrayList<int[]> convertStringToInt(String[] strings, boolean observables){
        int L = strings.length;
        ArrayList<int[]> stringsInt = new ArrayList<>();
        for (int i = 0; i < L; i++) {
            stringsInt.add(new int[strings[i].length()]);
        }
        for (int l = 0; l < L; l++) {
            String obs = strings[l];
            for (int k = 0; k < obs.length(); k++) {
                if(observables) {
                    stringsInt.get(l)[k] = emissionConversionCharToInt(obs.charAt(k));
                } else {
                    stringsInt.get(l)[k] = stateConversionCharToInt(obs.charAt(k));
                }
            }
        }
        return stringsInt;
    }


    /**
     * Overloaded method that converts Strings of observables to int
     * @param observables Array of strings to convert
     * @return  Converted strings
     */
    @Override
    public ArrayList<int[]> observables(String[] observables) {
        return convertStringToInt(observables,true);
    }

    /**
     * Overloaded method that converts Strings of states to int
     * @param states    String of states
     * @param observed  String of the corresponding observables, in this class not used.
     * @return  converted strings.
     */
    @Override
    public ArrayList<int[]> states(String[] states, String[] observed) {
        return convertStringToInt(states,false);
    }

    /**
     * Method used for both states and observables conversion from int to String
     * @param ints  Arrays of int to convert
     * @param observables   if the string to convert is the observable or not.
     * @return  converted ints.
     */
    private String[] convertIntToString(ArrayList<int[]> ints, boolean observables){
        int L = ints.size();
        String[] strings = new String[L];
        for (int l = 0; l < L; l++) {
            int[] intRow = ints.get(l);
            StringBuilder sb = new StringBuilder();
            for (int k = 0; k < intRow.length; k++) {
                if(observables) {
                    sb.append(emissionConversionIntToStr(intRow[k]));
                } else {
                    sb.append(stateConversionIntToString(intRow[k]));
                }
            }
            strings[l] = sb.toString();
        }
        return strings;
    }

    /**
     * Converts an emission int to the corresponding char in the model.
     * @param i int to be converted
     * @return  converted int
     */
    private String emissionConversionIntToStr(int i) {
        switch(i){
            case 0: return "A";

            case 1: return "C";

            case 2: return "G";

            case 3: return "T";

            default: throw new RuntimeException("conversion error, observable int to str");
        }
    }

    /**
     * Converts a state int ti the corresponding char in the model.
     * @param i int to be converted
     * @return  converted int
     */
    private String stateConversionIntToString(int i) {

        switch(i){
            case 0: return "C";

            case 1: return "N";

            case 2: return "R";

            default: throw new RuntimeException("conversion error, state int to str");
        }
    }

    /**
     * Overloaded method that converts ints of observables to strings.
     * @param observables   The observables given as ints
     * @return  observables as string
     */
    @Override
    public String[] observables(ArrayList<int[]> observables) {
        return convertIntToString(observables,true);
    }

    /**
     * Overloaded method that converts ints of states to strings
     * @param states The states given as ints
     * @return  states as string
     */
    @Override
    public String[] states(ArrayList<int[]> states) {
        return convertIntToString(states,false);
    }

    /**
     * This methods holds the number of states in the model
     * @return  Number of states in model
     */
    @Override
    public int getNumberOfStates() {
        return 3;
    }

    /**
     * Holds the name of the model, used for naming files and similar.
     * @return Name of model
     */
    @Override
    public String getNameOfModel() {
        return "DNAConversion3States";
    }

    /**
     * When doing Viterbi training og Baum-Welch, the methods require initial parameters. The below are there for just that purpose.
     * Notice however that this allows transitions between state 0 and 2, as these initial parameters come from Training By Counting and this was observed,
     * however the transition are extremely unlikely.
     * @return the transition matrix
     */
    @Override
    public double[][] getInitialP(){
        double[][] P = {
        {0.9988578004871654, 0.0011414784272835699, 7.210855510319455E-7 },
        {0.0017035029916180194, 0.9966607612374366, 0.00163573577094536 },
        {2.441580307971174E-7, 0.0011143372525580437, 0.9988854185894112 }};
        return P;
    }

    /**
     * When doing Viterbi training og Baum-Welch, the methods require initial parameters. The below are there for just that purpose.
     * @return the emission matrix
     */
    @Override
    public double[][] getInitialE(){
        double[][] E = {{0.3376644135146815, 0.16301845450214572, 0.19669891841974582, 0.30261821356342694 },
                {0.3343431467742144, 0.1647945258526105, 0.1661272788031685, 0.33473504857000663 },
                {0.3037413800007227, 0.1959265650773932, 0.1622264087185903, 0.3381056462032938 }};
        return E;
    }

    /**
     * When doing Viterbi training og Baum-Welch, the methods require initial parameters. The below are there for just that purpose.
     * @return The initial state distribution vector
     */
    @Override
    public double[] getInitialPi(){
        double[] pi = {0,1,0};
        return pi;
    }
}
