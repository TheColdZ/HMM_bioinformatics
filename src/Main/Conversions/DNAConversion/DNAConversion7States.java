package Main.Conversions.DNAConversion;

import Main.Conversions.Conversion;

import java.util.ArrayList;



/**
 *  This class handles conversions from observable data given as a String an int array and vice versa from an int array to a String.
 *  It does so for the 7-state model.
 *  It also holds initial parameters for the 7-state model.
 * @author Jens Kristian Refsgaard Nielsen & Thomas Damgaard Vinther
 */
public class DNAConversion7States implements Conversion {

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
     * This method converts a true annotated state from non-coding, coding and reverse-coding parts, to what states it would have been in, when doing TBC
     * It does this for 7 states and returns an int
     * @param previousState The state just before the state we are converting now
     * @param trueAnnotationToConvert   The char that we would like to convert
     * @return  the state that it corresponds to
     */
    public int convertAnnotationToState7States(int previousState, char trueAnnotationToConvert){
            switch(trueAnnotationToConvert){
                case 'N': return 3;

                case 'C': return codingState7States(previousState);

                case 'R': return reverseCodingState7States(previousState);

                default: throw new RuntimeException("Main.Conversion error");

            }
    }

    /**
     * Method that aids in finding the correct annotation of a true annotated genom, for the 7 states. It emulates a cycle
     * @param previousState    The previous state
     * @return  The next state
     */
    private int reverseCodingState7States(int previousState) {
        switch(previousState){
            case 3 : return 4;
            case 4 : return 5;
            case 5 : return 6;
            case 6 : return 4;
            default: return 4;       //We have a C -> R transition, this is modelled by beginning in the 'first' reverse coding state.
        }
    }
    /**
     * Method that aids in finding the correct annotation of a true annotated gene, for the 7 states. It emulates a cycle
     * @param state    The previous state
     * @return  The next state
     */
    private int codingState7States(int state) {
        switch(state){
            case 3 : return 2;
            case 2 : return 1;
            case 1 : return 0;
            case 0 : return 2;
            default: return 2; //We have a R -> C transition, this is modelled by beginning in the 'first' coding state.
        }
    }
    /**
     * Method used for both states and observables conversion from String to int
     * @param strings String to be converted
     * @param observables   If converting the observables or not
     * @return  converted string
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
                    if(k == 0) {
                        stringsInt.get(l)[k] = convertAnnotationToState7States(stringsInt.get(l)[k], obs.charAt(k));
                    }
                    else{
                        stringsInt.get(l)[k] = convertAnnotationToState7States(stringsInt.get(l)[k-1], obs.charAt(k));

                    }
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
    public ArrayList<int[]> states(String[] states,String[] observed) {
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
                    sb.append(emissionConversionIntToString(intRow[k]));
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
    private String emissionConversionIntToString(int i) {
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

            case 1: return "C";

            case 2: return "C";

            case 3: return "N";

            case 4: return "R";

            case 5: return "R";

            case 6: return "R";

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

    @Override
    public String getNameOfModel() {
        return "DNAConversion7States";
    }


    @Override
    public int getNumberOfStates() {
        return 7;
    }

    /**
     * When doing Viterbi training og Baum-Welch, the methods require initial parameters. The below are there for just that purpose.
     * Notice however that this allows transitions between state 0 and 2, as these initial parameters come from Training By Counting and this was observed,
     * however the transition are extremely unlikely.
     * @return the transition matrix
     */
    @Override
    public double[][] getInitialP(){
        double[][] P = {{0.0, 0.0, 0.9965734014614962, 0.0034244352818507094, 2.1632566530958366E-6, 0.0, 0 },
                        {1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 },
                        {0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0 },
                        {0.0, 0.0, 0.0017035029916180194, 0.9966607612374366, 0.00163573577094536, 0.0, 0.0 },
                        {0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0 },
                        {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0 },
                        {0.0, 0.0, 7.324740923913521E-7, 0.0033430117576741312, 0.9966562557682335, 0.0, 0.0 }};
        return P;
    }

    /**
     * When doing Viterbi training og Baum-Welch, the methods require initial parameters. The below are there for just that purpose.
     * @return the emission matrix
     */
    @Override
    public double[][] getInitialE(){
        double[][] E = {{0.3392152281731009, 0.12997639165905922, 0.13122386966234448, 0.39958451050549537 },
                {0.3525726169204167, 0.20005148550834367, 0.13622459795875103, 0.3111512996124886 },
                {0.32120539545052706, 0.15902748633903424, 0.32264828763814196, 0.19711883057229676 },
                {0.3343431467742144, 0.1647945258526105, 0.1661272788031685, 0.33473504857000663 },
                {0.3994628035006402, 0.13240714425930755, 0.12861146351253555, 0.3395185887275167 },
                {0.31271296684236277, 0.13711402277701437, 0.1975614472516107, 0.35261156312901215 },
                {0.19904836965916514, 0.3182585281958577, 0.1605063153916246, 0.32218678675335255}};
        return E;
    }

    /**
     * When doing Viterbi training og Baum-Welch, the methods require initial parameters. The below are there for just that purpose.
     * @return The initial state distribution vector
     */
    @Override
    public double[] getInitialPi(){
        double[] pi = {0.0,0.0,0.0,1.0,0.0,0.0,0.0};
        return pi;
    }
}
