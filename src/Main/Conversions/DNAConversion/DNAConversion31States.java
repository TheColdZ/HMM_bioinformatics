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

    private int codingStates(String trueAnnotation,String observed, int[] states,int n) {   //TODO Code duplication, almost same as in 16 state
        boolean correctStartCodon = observed.charAt(n) == 'A' && observed.charAt(n+1) == 'T' && observed.charAt(n+2) == 'G' && (trueAnnotation.charAt(n-1)== 'N' || trueAnnotation.charAt(n-1)== 'R');
        if( correctStartCodon){
            int endOfCoding = n;
            while(trueAnnotation.charAt(endOfCoding) == 'C'){
                endOfCoding++;
            }
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
            if (observed.charAt(n + 4) != 'N' && (states[n-1] == 3 || states[n-1] == 6)) return 4; //We loop if we are not done in 4 steps. 4->5->6->4..
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
        return "DNAConversion31States";
    }

    @Override
    public int getNumberOfStates() {
        return 31;
    }
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
                {0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.9966814949564647, 0.0, 0.0, 0.0033176338161675406, 0.0, 0.0 },        //Correction needed in this line
                {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0 },
                {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0 },
                {1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 }};
        return P;
    }
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
    @Override
    public double[] getInitialPi(){
        double[] pi = {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
        return pi;
    }
}
