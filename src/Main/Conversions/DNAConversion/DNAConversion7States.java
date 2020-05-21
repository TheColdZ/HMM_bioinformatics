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
                System.out.println("Previous state reversecoding was:"+state); //TODO delete print
                return 4;       //We have a C -> R transition
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
                System.out.println("Previous state, coding was:"+state); //TODO delete print
                return 2; //We have a R -> C transition
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
        return "DNAConversion7States";
    }

    @Override
    public int getNumberOfStates() {
        return 7;
    }
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
    @Override
    public double[][] getInitialE(){
        double[][] E = {{0.3392152281731009, 0.12997639165905922, 0.13122386966234448, 0.39958451050549537 },
                {0.3525726169204167, 0.20005148550834367, 0.13622459795875103, 0.3111512996124886 },
                {0.32120539545052706, 0.15902748633903424, 0.32264828763814196, 0.19711883057229676 },
                {0.3343431467742144, 0.1647945258526105, 0.1661272788031685, 0.33473504857000663 },
                {0.3994628035006402, 0.13240714425930755, 0.12861146351253555, 0.3395185887275167 },
                {0.31271296684236277, 0.13711402277701437, 0.1975614472516107, 0.35261156312901215 },
                {0.19904836965916514, 0.3182585281958577, 0.1605063153916246, 0.32218678675335255}};        // x = sun|L   x = rain|L
        return E;
    }
    @Override
    public double[] getInitialPi(){
        double[] pi = {0.0,0.0,0.0,1.0,0.0,0.0,0.0};
        return pi;
    }
}
