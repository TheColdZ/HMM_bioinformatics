package Main.Conversions.DNAConversion;

import Main.Conversions.Conversion;

import java.util.ArrayList;



/**
 *  This class handles conversions from observable data given as a String an int array and vice versa from an int array to a String.
 *  It does so for the 31-state model.
 *  It also holds initial parameters for the 3-state model.
 * @author Jens Kristian Refsgaard Nielsen & Thomas Damgaard Vinther
 */
public class DNAConversion31States implements Conversion {
    private DNAConversion16States DNAConversion16StatesConverter;
    public  DNAConversion31States(){
        this.DNAConversion16StatesConverter = new DNAConversion16States();
    }

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
     * Converts a true annotation as a string to an int array. In the 31 state model this has multiple dependencies.
     * As the 31-state model builds upon the 16-state model, the 16-state model conversion is reused.
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
                case 'C': states[i]= DNAConversion16StatesConverter.codingStates(trueAnnotation,observed,states,i);
                    break;
                case 'R': states[i] = reverseCodingStates(trueAnnotation,observed,states,i) ;
                    break;
                default: throw new RuntimeException("Conversion error");

            }
        }
        return states;
    }

    /**
     * Method that aids in finding the correct annotation of the reverse part of the gene.
     * For the 31-state model this is emulated through a start codon that creates a 'cycle'.
     * It is a prerequisite that the gene that is being converted starts and ends in at least NNNN so as not to get index out
     * of bounds exceptions
     * @param trueAnnotation    The true annotation of a gene.
     * @param observed  The corresponding observables
     * @param states    The states converted so far.
     * @param n The index at which we are converting.
     * @return  The converted annotation
     */
    private int reverseCodingStates(String trueAnnotation,String observed, int[] states,int n) {

        boolean TTA = observed.charAt(n) == 'T' && observed.charAt(n+1) == 'T' && observed.charAt(n+2) == 'A';
        boolean CTA = observed.charAt(n) == 'C' && observed.charAt(n+1) == 'T' && observed.charAt(n+2) == 'A';
        boolean TCA = observed.charAt(n) == 'T' && observed.charAt(n+1) == 'C' && observed.charAt(n+2) == 'A';
        boolean previousTrue = trueAnnotation.charAt(n-1)== 'N' || trueAnnotation.charAt(n-1)== 'C';
        boolean correctStartCodons = TTA || CTA || TCA;
        if(correctStartCodons && previousTrue ){
            int endOfCoding = n;
            while(trueAnnotation.charAt(endOfCoding) == 'R'){
                endOfCoding++;
            }
            if((endOfCoding-n)%3 != 0) return 0; //Check multiple of 3
            boolean correctEndCodon = observed.charAt(endOfCoding-3) == 'C' && observed.charAt(endOfCoding-2) == 'A' && observed.charAt(endOfCoding-1) == 'T' && (trueAnnotation.charAt(endOfCoding)== 'N' || trueAnnotation.charAt(endOfCoding)== 'C');

            if(correctEndCodon){
                if (TTA) return 16;
                if (CTA) return 19;
                if (TCA) return 22;
            }
        }

        else if(states[n-1] == 16)return 17;
        else if(states[n-1] == 17)return 18;
        else if(states[n-1] == 18)return 25;

        else if(states[n-1] == 19) return 20;
        else if(states[n-1] == 20) return 21;
        else if(states[n-1] == 21) return 25;

        else if(states[n-1] == 22) return 23;
        else if(states[n-1] == 23) return 24;
        else if(states[n-1] == 24) return 25;

        else if(states[n-1] == 25) return 26;
        else if(states[n-1] == 26) return 27;


        else if(observed.charAt(n) == 'C' && states[n-1] == 27 && observed.charAt(n+1) == 'A'
                && observed.charAt(n+2) == 'T' && trueAnnotation.charAt(n+3)== 'N' ) return 28;
        else if(observed.charAt(n) == 'A' && states[n-1] == 28 && observed.charAt(n+1) == 'T') return 29;
        else if(observed.charAt(n) == 'T' && states[n-1] == 29) return 30;


        else if( n+4 < observed.length()) {
            if (observed.charAt(n + 4) != 'N' && (states[n-1] == 27 || states[n-1] == 18 || states[n-1] == 21 || states[n-1] == 24)) return 25; //We loop if we are not done in 4 steps. 25->26->27->25..
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
        else if (i <31) return "R";
        else return "N";

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
        return "DNAConversion31States";
    }

    @Override
    public int getNumberOfStates() {
        return 31;
    }

    /**
     * When doing Viterbi training og Baum-Welch, the methods require initial parameters. The below are there for just that purpose.
     * Notice however that this allows transitions between state 0 and 2, as these initial parameters come from Training By Counting and this was observed,
     * however the transition are extremely unlikely.
     * @return the transition matrix
     */
    @Override
    public double[][] getInitialP(){
        double[][] P = {{0.9980806655788146, 9.847148194669034E-4, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 6.699007549889558E-4, 0.0, 0.0, 1.7164979022627567E-4, 0.0, 0.0, 9.306905650323102E-5, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 },
                {0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 },
                {0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 },
                {0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 },
                {0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 },
                {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 },
                {0.0, 0.0, 0.0, 0.0, 0.9965455721729901, 0.0, 0.0, 0.002398286052467676, 0.0, 0.0, 6.185234862326837E-4, 0.0, 0.0, 4.376182883094754E-4, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 },
                {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 },
                {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 },
                {0.9996408045977011, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 3.5919540229885057E-4, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 },
                {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 },
                {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 },
                {0.9986072423398329, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.001392757660167131, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 },
                {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 },
                {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 },
                {0.9980314960629921, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.001968503937007874, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 },
                {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 },
                {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 },
                {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0 },
                {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 },
                {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 },
                {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0 },
                {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 },
                {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 },
                {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0 },
                {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0 },
                {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0 },
                {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.9966814949564647, 0.0, 0.0, 0.0033185050435353, 0.0, 0.0 },
                {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0 },
                {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0 },
                {1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 }};
        return P;
    }

    /**
     * When doing Viterbi training og Baum-Welch, the methods require initial parameters. The below are there for just that purpose.
     * @return the emission matrix
     */
    @Override
    public double[][] getInitialE(){
        double[][] E = {{0.3293650930075511, 0.16966787402541592, 0.17084560132604826, 0.33012143164098473 },
                {1.0, 0.0, 0.0, 0.0 },
                {0.0, 0.0, 0.0, 1.0 },
                {0.0, 0.0, 1.0, 0.0 },
                {0.3203426172158001, 0.15959973432779506, 0.3252210273864626, 0.19483662106994226 },
                {0.35220605274334116, 0.20193068918850235, 0.13628622303543417, 0.3095770350327223 },
                {0.3400104580433466, 0.13084097657794558, 0.127022153995119, 0.4021264113835888 },
                {0.0, 0.0, 0.0, 1.0 },
                {1.0, 0.0, 0.0, 0.0 },
                {1.0, 0.0, 0.0, 0.0 },
                {0.0, 0.0, 0.0, 1.0 },
                {1.0, 0.0, 0.0, 0.0 },
                {0.0, 0.0, 1.0, 0.0 },
                {0.0, 0.0, 0.0, 1.0 },
                {0.0, 0.0, 1.0, 0.0 },
                {1.0, 0.0, 0.0, 0.0 },
                {0.0, 0.0, 0.0, 1.0 },
                {0.0, 0.0, 0.0, 1.0 },
                {1.0, 0.0, 0.0, 0.0 },
                {0.0, 1.0, 0.0, 0.0 },
                {0.0, 0.0, 0.0, 1.0 },
                {1.0, 0.0, 0.0, 0.0 },
                {0.0, 0.0, 0.0, 1.0 },
                {0.0, 1.0, 0.0, 0.0 },
                {1.0, 0.0, 0.0, 0.0 },
                {0.4027788668119874, 0.12812443914738206, 0.12934067255267875, 0.3397560214879518 },
                {0.31167723465463676, 0.13722440900291513, 0.19939955009818733, 0.3516988062442608 },
                {0.1970550772517307, 0.3209087598426912, 0.16085122398732887, 0.32118493891824923 },
                {0.0, 1.0, 0.0, 0.0 },
                {1.0, 0.0, 0.0, 0.0 },
                {0.0, 0.0, 0.0, 1.0 }};        // x = sun|L   x = rain|L
        return E;
    }

    /**
     * When doing Viterbi training og Baum-Welch, the methods require initial parameters. The below are there for just that purpose.
     * @return The initial state distribution vector
     */
    @Override
    public double[] getInitialPi(){
        double[] pi = {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
        return pi;
    }
}
