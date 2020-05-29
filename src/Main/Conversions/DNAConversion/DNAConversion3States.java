package Main.Conversions.DNAConversion;

import Main.Conversions.Conversion;

import java.util.ArrayList;

/**
 *
 * @author Jens Kristian Jensen & Thomas Damgaard Vinther
 */
public class DNAConversion3States implements Conversion {


    private int emissionConversionCharToInt(Character c){
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
    private int stateConversionCharToInt(Character c){
        int res;
        switch(c){
            case 'C': res = 0;
                break;
            case 'N': res = 1;
                break;
            case 'R': res = 2;
                break;
            default: throw new RuntimeException("conversion error, state char to int");
        }
        return res;
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
            int[] int_row = ints.get(l);
            StringBuilder sb = new StringBuilder();
            for (int k = 0; k < int_row.length; k++) {
                if(observables) {
                    sb.append(emissionConversionIntToStr(int_row[k]));
                } else {
                    sb.append(stateConversionIntToStr(int_row[k]));
                }
            }
            strings[l] = sb.toString();
        }
        return strings;
    }

    private String emissionConversionIntToStr(int i) {
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

    private String stateConversionIntToStr(int i) {
        String res;
        switch(i){
            case 0: res = "C";
                break;
            case 1: res = "N";
                break;
            case 2: res = "R";
                break;
            default: throw new RuntimeException("conversion error, state int to str");
        }
        return res;
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
    public int getNumberOfStates() {
        return 3;
    }

    @Override
    public String getNameOfModel() {
        return "DNAConversion3States";
    }
    @Override
    public double[][] getInitialP(){
        double[][] P = {
        {0.9988578004871654, 0.0011414784272835699, 7.210855510319455E-7 },
        {0.0017035029916180194, 0.9966607612374366, 0.00163573577094536 },
        {2.441580307971174E-7, 0.0011143372525580437, 0.9988854185894112 }};


        return P;
    }
    @Override
    public double[][] getInitialE(){
        double[][] E = {{0.3376644135146815, 0.16301845450214572, 0.19669891841974582, 0.30261821356342694 },
                {0.3343431467742144, 0.1647945258526105, 0.1661272788031685, 0.33473504857000663 },
                {0.3037413800007227, 0.1959265650773932, 0.1622264087185903, 0.3381056462032938 }};
        return E;
    }
    @Override
    public double[] getInitialPi(){
        double[] pi = {0,1,0};
        return pi;
    }
}
