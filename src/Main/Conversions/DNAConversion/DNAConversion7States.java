package Main.Conversions.DNAConversion;

import Main.Conversions.Conversion;

import java.util.ArrayList;

public class DNAConversion7States implements Conversion {
    private int emission_conversion_char_to_int(Character c){
        int res;
        switch(c){
            case 'A': res = 0;
                break;
            case 'C': res = 1;
                break;
            case 'G': res = 2;
                break;
            case 'T': res = 3;
                break;
            default: throw new RuntimeException("conversion error, observable char to int");
        }
        return res;
    }

    /**
     * This method converts a true annotated state from non-coding, coding and reverse-coding parts, to what states it would have been in.
     * It does this for 7 states and returns an int TODO
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
     * @param state    The previous state
     * @return  The next state
     */
    private int reverseCodingState7States(int state) {
        int foundState = -1;
        switch(state){
            case 3 : foundState = 4;
                return foundState;
            case 4 : foundState = 5;
                return foundState;
            case 5 : foundState = 6;
                return foundState;
            case 6 : foundState = 4;
                return foundState;
            default:
                System.out.println("Previous state reversecoding was:"+state);
                return 4;  //TODO this is bad
            //default: throw new RuntimeException("CodingState error, previous state not correct. Reverse coding");
        }
    }
    /**
     * Method that aids in finding the correct annotation of a true annotated genom, for the 7 states. It emulates a cycle
     * @param state    The previous state
     * @return  The next state
     */
    private int codingState7States(int state) {
        int foundState = -1;
        switch(state){
            case 3 : foundState = 2;
                return foundState;
            case 2 : foundState = 1;
                return foundState;
            case 1 : foundState = 0;
                return foundState;
            case 0 : foundState = 2;
                return foundState;
            default:
                System.out.println("Previous state, coding was:"+state);
                return 2; //TODO This... is bad
            //default: throw new RuntimeException("CodingState error, previous state not correct., state:"+state);
        }
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
                    if(k == 0) {
                        strings_int.get(l)[k] = convertAnnotationToState7States(strings_int.get(l)[k], obs.charAt(k));
                    }
                    else{
                        strings_int.get(l)[k] = convertAnnotationToState7States(strings_int.get(l)[k-1], obs.charAt(k));

                    }
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
    public ArrayList<int[]> states(String[] states,String[] observed) {
        return convert_str_to_int(states,false);
    }

    private String[] convert_int_to_str(ArrayList<int[]> ints, boolean observables){
        int L = ints.size();
        int K = ints.get(0).length;
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
            case 0: res = "A";
                break;
            case 1: res = "C";
                break;
            case 2: res = "G";
                break;
            case 3: res = "T";
                break;
            default: throw new RuntimeException("conversion error, observable int to str");
        }
        return res;
    }

    private String state_conversion_int_to_str(int i) {
        String res;
        switch(i){
            case 0: res = "C";
                break;
            case 1: res = "C";
                break;
            case 2: res = "C";
                break;
            case 3: res = "N";
                break;
            case 4: res = "R";
                break;
            case 5: res = "R";
                break;
            case 6: res = "R";
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
        return "Main.Conversions.DNAConversion.DNAConversion7States";
    }

    @Override
    public int getNumberOfStates() {
        return 7;
    }
}
