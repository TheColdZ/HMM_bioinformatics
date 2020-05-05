package Main.Conversions.DNAConversion;

import Main.Conversions.Conversion;

import java.util.ArrayList;

public class DNAConversion31States implements Conversion {
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
                case 'R': states[i] = reverseCodingStates(trueAnnotation,observed,states,i) ;
                    break;
                default: throw new RuntimeException("Main.Conversion error");

            }
        }
        return states;
    }

    private int reverseCodingStates(String trueAnnotation,String observed, int[] states,int n) {
        if(observed.charAt(n) == 'T' && states[n-1] == 0 && observed.charAt(n+1) == 'T' && observed.charAt(n+2)== 'A')return 16;
        else if(states[n-1] == 16)return 17;
        else if(states[n-1] == 17)return 18;
        else if(states[n-1] == 18)return 25;

        else if(observed.charAt(n) == 'C' && states[n-1] == 0 && observed.charAt(n+1) == 'T' && observed.charAt(n+2)== 'A' )return 19;

        else if(states[n-1] == 19) return 20;
        else if(states[n-1] == 20) return 21;
        else if(states[n-1] == 21) return 25;

        else if(observed.charAt(n) == 'T' && states[n-1] == 0 && observed.charAt(n+1) == 'C' && observed.charAt(n+2)== 'A' )return 22;
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

    private int codingStates(String trueAnnotation,String observed, int[] states,int n) {   //TODO Code duplication, almost same as in 16 state
        if(observed.charAt(n) == 'A' && states[n-1] == 0 )return 1;
        else if(observed.charAt(n) == 'T' && states[n-1] == 1)return 2;
        else if(observed.charAt(n) == 'G' && states[n-1] == 2)return 3;
        else if(states[n-1] == 3) return 4;
        else if(states[n-1] == 4) return 5;
        else if(states[n-1] == 5) return 6;

        else if(observed.charAt(n) == 'T' && states[n-1] == 6 && observed.charAt(n+1) == 'A'
                && observed.charAt(n+2) == 'A' ) return 7; //&& trueAnnotation.charAt(n+3)== 'N'

        else if(states[n-1] == 7 ) return 8;
        else if(states[n-1] == 8 ) return 9;
        else if(observed.charAt(n) == 'T' && states[n-1] == 6 && observed.charAt(n+1) == 'A'
                && observed.charAt(n+2) == 'G'  ) return 10;
        else if(states[n-1] == 10 ) return 11;
        else if(states[n-1] == 11 ) return 12;
        else if(observed.charAt(n) == 'T' && states[n-1] == 6 && observed.charAt(n+1) == 'G'
                && observed.charAt(n+2) == 'A'   ) return 13;
        else if(states[n-1] == 13 ) return 14;
        else if(states[n-1] == 14 ) return 15;
        else if( n+4 < observed.length()) {
            if (observed.charAt(n + 4) != 'N') return 4; //We loop if we are not done in 4 steps. 4->5->6->4..
        }
        else return 0;
        return 0;
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
        else if (i <16) return "C";
        else if (i <31) return "R";
        else return "N";

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
        return "Main.Conversions.DNAConversion.DNAConversion31States";
    }

    @Override
    public int getNumberOfstates() {
        return 31;
    }
}
