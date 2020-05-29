package Main.Conversions;

import java.util.ArrayList;

/**
 *
 * @author Jens Kristian Refsgaard Nielsen & Thomas Damgaard Vinther
 */
public class weatherConversion implements Conversion {
    private int emissionConversionCharToInt(Character c){
        switch(c){
            case 'S': return 0;

            case 'R': return 1;

            default: throw new RuntimeException("conversion error, observable char to int");
        }
    }
    private int stateConversionCharToInt(Character c){
        switch(c){
            case 'H': return 0;

            case 'L': return 1;

            default: throw new RuntimeException("conversion error, state char to int");
        }
    }
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


    @Override
    public ArrayList<int[]> observables(String[] observables) {
        return convertStringToInt(observables,true);
    }

    @Override
    public ArrayList<int[]> states(String[] states, String[] observed) {
        return convertStringToInt(states,false);
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
            case 0: return "S";

            case 1: return "R";

            default: throw new RuntimeException("conversion error, observable int to str");
        }
    }

    private String stateConversionIntToString(int i) {
        switch(i){
            case 0: return "H";

            case 1: return "L";

            default: throw new RuntimeException("conversion error, state int to str");
        }
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
        return "Weather_conversion";
    }

    @Override
    public int getNumberOfStates() {
        return 2;
    }

    @Override
    public double[][] getInitialP(){
        double[][] P = {{0.9, 0.1},     // H -> H   H -> L
                {0.2, 0.8}};             // L -> H   L -> L
        return P;
    }
    @Override
    public double[][] getInitialE(){
        double[][] E = {{0.9, 0.1},         // x = sun|H   x = rain|H
                {0.3, 0.7}};        // x = sun|L   x = rain|L
        return E;
    }
    @Override
    public double[] getInitialPi(){
        double[] pi = {0.5, 0.5};
        return pi;
    }
}
