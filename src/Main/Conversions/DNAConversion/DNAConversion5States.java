package Main.Conversions.DNAConversion;

import Main.Conversions.Conversion;

import java.util.ArrayList;



/**
 *  This class handles conversions from observable data given as a String an int array and vice versa from an int array to a String.
 *  It does so for the 5-state model.
 *  It also holds initial parameters for the 5-state model.
 * @author Jens Kristian Refsgaard Nielsen & Thomas Damgaard Vinther
 */
public class DNAConversion5States implements Conversion {

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
     * Converts a true annotation as a string to an int array. In the 5 state model this has multiple dependencies.
     * @param trueAnnotation    The true state annotation of a gene
     * @param observed  The observable gene
     * @return  conversion of state as string to int
     */
    public int[] convertAnnotationToState(String trueAnnotation, String observed){
        int[] states = new int[trueAnnotation.length()];
        for (int i = 0; i < trueAnnotation.length(); i++) {
            char trueAnnotationToConvert = trueAnnotation.charAt(i);
            switch(trueAnnotationToConvert){
                case 'N': states[i] = 0;
                    break;
                case 'C': states[i] = codingStates(trueAnnotation,observed,states,i);
                    break;
                default: states[i] = 0 ;   //This model does not model any reverse coding.
            }
        }
        return states;
    }

    /**
     * Method that aids in finding the correct annotation of a true annotated gene, for the 7 states. It emulates a cycle
     * @param trueAnnotation   The previous state
     * @return  The next state
     */


    /**
     * Method that aids in finding the correct annotation of a true annotated gene.
     * For the 5-state model this is emulated through a start codon that creates a 'cycle'.
     * It is a prerequisite that the gene that is being converted ends in at least NNN so as not to get index out
     * of bounds exceptions
     * @param trueAnnotation    The true annotation of a gene.
     * @param observed  The corresponding observables
     * @param states    The states converted so far.
     * @param n The index at which we are converting.
     * @return  The converted annotation
     */
    private int codingStates(String trueAnnotation,String observed, int[] states,int n) {
        if(observed.charAt(n) == 'A' && observed.charAt(n+1) == 'T' && observed.charAt(n+2) == 'G' && trueAnnotation.charAt(n-1)== 'N' )return 1;
        else if(states[n-1] == 1)return 2;
        else if(states[n-1] == 2)return 3;
        else if (states[n-1] == 3 || states[n-1] == 4) return 4;
        else return 0;
    }

    /**
     * Method used for both states and observables conversion from String to int
     * @param trueAnnotation    True annotation of a gene
     * @param observed  Corresponding observations
     * @param observables   If converting the observables or not
     * @return  converted string
     */
    private ArrayList<int[]> convertStrToInt(String[] trueAnnotation, String[] observed, boolean observables){
        int L = trueAnnotation.length;
        ArrayList<int[]> stringsInt = new ArrayList<>();
        if(observables) {
            for (int i = 0; i < L; i++) {
                stringsInt.add(new int[trueAnnotation[i].length()]);
            }
        }
        for (int l = 0; l < L; l++) {
            String annotation = trueAnnotation[l];
            String obs = observed[l];
            if(observables) {
                for (int k = 0; k < annotation.length(); k++) {
                    stringsInt.get(l)[k] = emissionConversionCharToInt(obs.charAt(k));
                }
            } else {
                stringsInt.add(convertAnnotationToState(annotation,obs));
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
        return convertStrToInt(observables,observables,true);
    }


    /**
     * Overloaded method that converts Strings of states to int
     * @param trueAnnotation   True annotation of gene
     * @param observed  String of the corresponding observables.
     * @return  converted strings.
     */
    @Override
    public ArrayList<int[]> states(String[] trueAnnotation, String[] observed) {
        return convertStrToInt(trueAnnotation,observed,false);
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
        if(i == 0) return "N";
        else if (i <5) return "C";
        else return "N"; //This model does not model reverse coding.

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
        return "DNAConversion5states";
    }


    @Override
    public int getNumberOfStates() {
        return 5;
    }

    /**
     * When doing Viterbi training og Baum-Welch, the methods require initial parameters. The below are there for just that purpose.
     * Notice however that this allows transitions between state 0 and 2, as these initial parameters come from Training By Counting and this was observed,
     * however the transition are extremely unlikely.
     * @return the transition matrix
     */
    @Override
    public double[][] getInitialP(){
        double[][] P = {{0.9994657980889959, 5.342019110040931E-4, 0.0, 0.0, 0.0 },
                {0.0, 0.0, 1.0, 0.0, 0.0},
                {0.0, 0.0, 0.0, 1.0, 0.0 },
                {0.0, 0.0, 0.0, 0.0, 1.0 },
                {0.001137102843437669, 0.0, 0.0, 0.0, 0.9988628971565623 }
        };
        return P;
    }

    /**
     * When doing Viterbi training og Baum-Welch, the methods require initial parameters. The below are there for just that purpose.
     * @return the emission matrix
     */
    @Override
    public double[][] getInitialE(){
        double[][] E = {{0.3176419730554597, 0.18165490579535418, 0.1666847398198635, 0.3340183813293226},
                {1.0, 0.0, 0.0, 0.0},
                {0.0, 0.0, 0.0, 1.0 },
                {0.0, 0.0, 1.0, 0.0 },
                {0.3382739175830128, 0.16352928365923666, 0.1959066566170595, 0.3022901421406911}};
        return E;
    }

    /**
     * When doing Viterbi training og Baum-Welch, the methods require initial parameters. The below are there for just that purpose.
     * @return The initial state distribution vector
     */
    @Override
    public double[] getInitialPi(){
        double[] pi ={1,0,0,0,0};
        return pi;
    }
}
