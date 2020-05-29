package Main.Conversions;

import java.util.ArrayList;

/**
 *
 * @author Jens Kristian Jensen & Thomas Damgaard Vinther
 */
public class weather_conversion implements Conversion {
    private int emission_conversion_char_to_int(Character c){
        int res;
        switch(c){
            case 'S': res = 0;
                break;
            case 'R': res = 1;
                break;
            default: throw new RuntimeException("conversion error, observable char to int");
        }
        return res;
    }
    private int state_conversion_char_to_int(Character c){
        int res;
        switch(c){
            case 'H': res = 0;
                break;
            case 'L': res = 1;
                break;
            default: throw new RuntimeException("conversion error, state char to int");
        }
        return res;
    }
    private ArrayList<int[]> convert_str_to_int(String[] strings, boolean observables){
        int L = strings.length;
        int K = strings[0].length();
        ArrayList<int[]> strings_int = new ArrayList<>();
        for (int i = 0; i < L; i++) {
            strings_int.add(new int[strings[i].length()]);
        }
        for (int l = 0; l < L; l++) {
            String obs = strings[l];
            for (int k = 0; k < obs.length(); k++) {
                if(observables) {
                    strings_int.get(l)[k] = emission_conversion_char_to_int(obs.charAt(k));
                } else {
                    strings_int.get(l)[k] = state_conversion_char_to_int(obs.charAt(k));
                }
            }
        }
        return strings_int;
    }


    @Override
    public ArrayList<int[]> observables(String[] observables) {
        return convert_str_to_int(observables,true);
    }

    @Override
    public ArrayList<int[]> states(String[] states, String[] observed) {
        return convert_str_to_int(states,false);
    }

    private String[] convert_int_to_str(ArrayList<int[]> ints, boolean observables){
        int L = ints.size();
        String[] strings = new String[L];
        for (int l = 0; l < L; l++) {
            int[] int_row = ints.get(l);
            StringBuilder sb = new StringBuilder();
            for (int k = 0; k < int_row.length; k++) {
                if(observables) {
                    sb.append(emission_conversion_int_to_str(int_row[k]));
                } else {
                    sb.append(state_conversion_int_to_str(int_row[k]));
                }
            }
            strings[l] = sb.toString();
        }
        return strings;
    }

    private String emission_conversion_int_to_str(int i) {
        String res;
        switch(i){
            case 0: res = "S";
                break;
            case 1: res = "R";
                break;
            default: throw new RuntimeException("conversion error, observable int to str");
        }
        return res;
    }

    private String state_conversion_int_to_str(int i) {
        String res;
        switch(i){
            case 0: res = "H";
                break;
            case 1: res = "L";
                break;
            default: throw new RuntimeException("conversion error, state int to str");
        }
        return res;
    }

    @Override
    public String[] observables(ArrayList<int[]> observables) {
        return convert_int_to_str(observables,true);
    }

    @Override
    public String[] states(ArrayList<int[]> states) {
        return convert_int_to_str(states,false);
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
