package Main.Conversions.DNAConversion;

import Main.Conversions.Conversion;

import java.util.ArrayList;



/**
 *  This class handles conversions from observable data given as a String an int array and vice versa from an int array to a String.
 *  It does so for the 16-state model.
 *  It also holds initial parameters for the 316-state model.
 * @author Jens Kristian Refsgaard Nielsen & Thomas Damgaard Vinther
 */
public class DNAConversion16States implements Conversion {

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
     * Converts a true annotation as a string to an int array. In the 16 state model this has multiple dependencies.
     * @param trueAnnotation    The true state annotation of a gene
     * @param observed  The observable gene
     * @return  conversion of state as string to int
     */
    private int[] convertAnnotationToState(String trueAnnotation, String observed){
        int[] states = new int[trueAnnotation.length()];
        for (int i = 0; i < trueAnnotation.length(); i++) {
            char trueAnnotationToConvert = trueAnnotation.charAt(i);
            switch(trueAnnotationToConvert){
                case 'N': states[i] = 0;
                    break;
                case 'C': states[i] = codingStates(trueAnnotation,observed,states,i);
                    break;
                default: states[i] = 0 ;   //This model does not model reverse coding, therefore any reversecoding found is set to noncoding(0)
            }
        }
        return states;
    }

    /**
     * Method that aids in finding the correct annotation of a true annotated gene.
     * For the 16-state model this is emulated through a start codon and multiple end codons that creates a 'cycle'.
     * It is a prerequisite that the gene that is being converted starts and ends in at least NNNN so as not to get index out
     * of bounds exceptions
     * @param trueAnnotation    The true annotation of a gene.
     * @param observed  The corresponding observables
     * @param states    The states converted so far.
     * @param n The index at which we are converting.
     * @return  The converted annotation
     */
    public int codingStates(String trueAnnotation,String observed, int[] states,int n) {
        boolean correctStartCodon = observed.charAt(n) == 'A' && observed.charAt(n+1) == 'T' && observed.charAt(n+2) == 'G' && (trueAnnotation.charAt(n-1)== 'N' || trueAnnotation.charAt(n-1)== 'R' );
        if( correctStartCodon){
            int endOfCoding = n;
            while(trueAnnotation.charAt(endOfCoding) == 'C'){
                endOfCoding++;
            }
            if((endOfCoding-n)%3 != 0) return 0;        //Check multiplum of 3
            boolean TAA = observed.charAt(endOfCoding-3) == 'T' && observed.charAt(endOfCoding-2) == 'A' && observed.charAt(endOfCoding-1) == 'A' && (trueAnnotation.charAt(endOfCoding)== 'N' || trueAnnotation.charAt(endOfCoding)== 'R');
            boolean TAG = observed.charAt(endOfCoding-3) == 'T' && observed.charAt(endOfCoding-2) == 'A' && observed.charAt(endOfCoding-1) == 'G' && (trueAnnotation.charAt(endOfCoding)== 'N' || trueAnnotation.charAt(endOfCoding)== 'R');
            boolean TGA = observed.charAt(endOfCoding-3) == 'T' && observed.charAt(endOfCoding-2) == 'G' && observed.charAt(endOfCoding-1) == 'A' && (trueAnnotation.charAt(endOfCoding)== 'N' || trueAnnotation.charAt(endOfCoding)== 'R');
            boolean correctEndCodons = TAA || TAG || TGA;
            if(correctEndCodons){
                return 1;
            }
        }
        else if(observed.charAt(n) == 'T' && states[n-1] == 1)return 2;
        else if(observed.charAt(n) == 'G' && states[n-1] == 2)return 3;
        else if(states[n-1] == 3) return 4;
        else if(states[n-1] == 4) return 5;
        else if(states[n-1] == 5) return 6;

        else if(observed.charAt(n) == 'T' && states[n-1] == 6 && observed.charAt(n+1) == 'A'
                && observed.charAt(n+2) == 'A' && (trueAnnotation.charAt(n+3)== 'N' || trueAnnotation.charAt(n+3)== 'R' )) return 7;
        else if(states[n-1] == 7 ) return 8;
        else if(states[n-1] == 8 ) return 9;
        else if(observed.charAt(n) == 'T' && states[n-1] == 6 && observed.charAt(n+1) == 'A'
                && observed.charAt(n+2) == 'G' && (trueAnnotation.charAt(n+3)== 'N' || trueAnnotation.charAt(n+3)== 'R')) return 10;
        else if(states[n-1] == 10 ) return 11;
        else if(states[n-1] == 11 ) return 12;
        else if(observed.charAt(n) == 'T' && states[n-1] == 6 && observed.charAt(n+1) == 'G'
                && observed.charAt(n+2) == 'A' && (trueAnnotation.charAt(n+3)== 'N' || trueAnnotation.charAt(n+3)== 'R')) return 13;
        else if(states[n-1] == 13 ) return 14;
        else if(states[n-1] == 14 ) return 15;
        else if( n+4 < observed.length()) {
            if ((observed.charAt(n + 4) != 'N' || observed.charAt(n + 4) != 'R') && (states[n-1] == 3 || states[n-1] == 6) ) return 4; //We loop if we are not done in 4 steps. 4->5->6->4..
        }
        else return 0;
        return 0;
    }

    /**
     * Method used for both states and observables conversion from String to int
     * @param trueAnnotation    True annotation of a gene
     * @param observed  Corresponding observations
     * @param observables   If converting the observables or not
     * @return  converted string
     */
    private ArrayList<int[]> convertStringToInt(String[] trueAnnotation, String[] observed, boolean observables){
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
        return convertStringToInt(observables,observables,true);
    }

    /**
     * Overloaded method that converts Strings of states to int
     * @param trueAnnotation   True annotation of gene
     * @param observed  String of the corresponding observables.
     * @return  converted strings.
     */
    @Override
    public ArrayList<int[]> states(String[] trueAnnotation, String[] observed) {
        return convertStringToInt(trueAnnotation,observed,false);
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
        else if (i <16) return "C";
        else return "N"; //This model does not model reversecoding

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
        return "DNAConversion16States";
    }

    @Override
    public int getNumberOfStates() {
        return 16;
    }

    /**
     * When doing Viterbi training og Baum-Welch, the methods require initial parameters. The below are there for just that purpose.
     * Notice however that this allows transitions between state 0 and 2, as these initial parameters come from Training By Counting and this was observed,
     * however the transition are extremely unlikely.
     * @return the transition matrix
     */
    @Override
    public double[][] getInitialP(){
        double[][] P = {{0.9994664817527306, 5.335182472693972E-4, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, },
                {0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 },
                {0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 },
                {0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 },
                {0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 },
                {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 },
                {0.0, 0.0, 0.0, 0.0, 0.996575699516137, 0.0, 0.0, 0.0023782950453183566, 0.0, 0.0, 6.127335779709887E-4, 0.0, 0.0, 4.3327186057362803E-4, 0.0, 0.0, },
                {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 },
                {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 },
                {1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 },
                {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0 },
                {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0 },
                {1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 },
                {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0 },
                {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0 },
                {1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 }};
        return P;
    }

    /**
     * When doing Viterbi training og Baum-Welch, the methods require initial parameters. The below are there for just that purpose.
     * @return the emission matrix
     */
    @Override
    public double[][] getInitialE(){
        double[][] E = {{0.31768254314012095, 0.18164000763728133, 0.16667860546651458, 0.33399884375608313 },
                {1.0, 0.0, 0.0, 0.0 },
                {0.0, 0.0, 0.0, 1.0 },
                {0.0, 0.0, 1.0, 0.0 },
                {0.3202161060909307, 0.1596380342618056, 0.3253119642854091, 0.19483389536185464 },
                {0.3522201123601267, 0.2019021232884907, 0.13627382781588254, 0.3096039365355001 },
                {0.33986801872554834, 0.1307600801937503, 0.1272409213735487, 0.40213097970715267 },
                {0.0, 0.0, 0.0, 1.0 },
                {1.0, 0.0, 0.0, 0.0 },
                {1.0, 0.0, 0.0, 0.0 },
                {0.0, 0.0, 0.0, 1.0 },
                {1.0, 0.0, 0.0, 0.0 },
                {0.0, 0.0, 1.0, 0.0 },
                {0.0, 0.0, 0.0, 1.0 },
                {0.0, 0.0, 1.0, 0.0 },
                {1.0, 0.0, 0.0, 0.0 }};
        return E;
    }

    /**
     * When doing Viterbi training og Baum-Welch, the methods require initial parameters. The below are there for just that purpose.
     * @return The initial state distribution vector
     */
    @Override
    public double[] getInitialPi(){
        double[] pi = {1.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0};
        return pi;
    }

}
