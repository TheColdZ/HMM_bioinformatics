package Main.Conversions.DNAConversion;

import Main.Conversions.Conversion;

import java.util.ArrayList;


/**
 *
 * @author Jens Kristian Refsgaard Nielsen & Thomas Damgaard Vinther
 */
public class DNAConversion5States implements Conversion {
    private int emissionConversionCharToInt(Character c){
        switch(c){
            case 'A': return 0;

            case 'C': return 1;

            case 'G': return 2;

            case 'T': return 3;

            default: throw new RuntimeException("conversion error, observable char to int");
        }
    }


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


    private int codingStates(String trueAnnotation,String observed, int[] states,int n) {
        if(observed.charAt(n) == 'A' && observed.charAt(n+1) == 'T' && observed.charAt(n+2) == 'G' && trueAnnotation.charAt(n-1)== 'N' )return 1;
        else if(states[n-1] == 1)return 2;
        else if(states[n-1] == 2)return 3;
        else if (states[n-1] == 3 || states[n-1] == 4) return 4;
        else return 0;
    }

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


    @Override
    public ArrayList<int[]> observables(String[] observables) {
        return convertStrToInt(observables,observables,true);    //TODO this is not pretty... but it should work
    }


    @Override
    public ArrayList<int[]> states(String[] trueAnnotation, String[] observed) {
        return convertStrToInt(trueAnnotation,observed,false);
    }

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

    private String emissionConversionIntToString(int i) {
        switch(i){
            case 0: return "A";

            case 1: return "C";

            case 2: return "G";

            case 3: return "T";

            default: throw new RuntimeException("conversion error, observable int to str");
        }
    }

    private String stateConversionIntToString(int i) {
        if(i == 0) return "N";
        else if (i <5) return "C";
        else return "N"; //This model does not model reverse coding.

    }

    @Override
    public String[] observables(ArrayList<int[]> observables) {
        return convertIntToString(observables,true);
    }

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
    @Override
    public double[][] getInitialP(){
        double[][] P = {{0.999, 0.001, 0, 0, 0 },
                {0, 0, 1, 0, 0 },
                {0, 0, 0, 1, 0 },
                {0, 0, 0, 0, 1 },
                {0.001, 0, 0, 0, 0.999 }
        };
        return P;
    }
    @Override
    public double[][] getInitialE(){
        double[][] E = {{0.3, 0.2, 0.15, 0.35},
                {1, 0, 0, 0 },
                {0, 0, 0, 1 },
                {0, 0, 1, 0 },
                {0.34, 0.16, 0.2, 0.3}};
        return E;
    }
    @Override
    public double[] getInitialPi(){
        double[] pi ={1,0,0,0,0};
        return pi;
    }
}
