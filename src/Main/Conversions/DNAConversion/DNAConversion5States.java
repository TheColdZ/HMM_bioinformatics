package Main.Conversions.DNAConversion;

import Main.Conversions.Conversion;

import java.util.ArrayList;

public class DNAConversion5States implements Conversion {
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


    public int[] convertAnnotationToState(String trueAnnotation, String observed){
        int[] states = new int[trueAnnotation.length()];
        for (int i = 0; i < trueAnnotation.length(); i++) {
            char trueAnnotationToConvert = trueAnnotation.charAt(i);
            switch(trueAnnotationToConvert){
                case 'N': states[i] = 0;
                    break;
                case 'C': states[i] = codingStates(trueAnnotation,observed,states,i);
                    break;
                default: states[i] = 0 ;   //TODO maybe change this, but this model does not model R
                    //default: throw new RuntimeException("Main.Conversion error");

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
    private ArrayList<int[]> convert_str_to_int(String[] trueAnnotation,String[] observed,boolean observables){
        int L = trueAnnotation.length;
        ArrayList<int[]> strings_int = new ArrayList<>();
        if(observables) {       //TODO not the prettiest
            for (int i = 0; i < L; i++) {
                strings_int.add(new int[trueAnnotation[i].length()]);
            }
        }
        for (int l = 0; l < L; l++) {
            String annotation = trueAnnotation[l];
            String obs = observed[l];
            if(observables) {
                for (int k = 0; k < annotation.length(); k++) {
                    strings_int.get(l)[k] = emission_conversion_char_to_int(obs.charAt(k));
                }
            } else {
                strings_int.add(convertAnnotationToState(annotation,obs));
            }

        }
        return strings_int;
    }


    @Override
    public ArrayList<int[]> observables(String[] observables) {
        return convert_str_to_int(observables,observables,true);    //TODO this is not pretty... but it should work
    }


    @Override
    public ArrayList<int[]> states(String[] trueAnnotation, String[] observed) {
        return convert_str_to_int(trueAnnotation,observed,false);
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
        if(i == 0) return "N";
        else if (i <5) return "C";
        else return "N"; //TODO right now we do not look at R's, as this model does not represent it.

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
        return "DNAConversion5states";
    }

    @Override
    public int getNumberOfStates() {
        return 5;
    }
    @Override
    public double[][] getInitialP(){
        double[][] P = {{0.9994657980889959, 5.342019110040931E-4, 0, 0, 0 },
                {0, 0, 1, 0, 0 },
                {0, 0, 0, 1, 0 },
                {0, 0, 0, 0, 1 },
                {0.001137102843437669, 0, 0, 0, 0.9988628971565623 }
        };
        return P;
    }
    @Override
    public double[][] getInitialE(){
        double[][] E = {{0.3176419730554597, 0.18165490579535418, 0.1666847398198635, 0.3340183813293226 },
                {1, 0, 0, 0 },
                {0, 0, 0, 1 },
                {0, 0, 1, 0 },
                {0.3382739175830128, 0.16352928365923666, 0.1959066566170595, 0.3022901421406911 }};
        return E;
    }
    @Override
    public double[] getInitialPi(){
        double[] pi ={1,0,0,0,0};
        return pi;
    }
}
