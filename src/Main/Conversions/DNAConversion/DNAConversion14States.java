package Main.Conversions.DNAConversion;

import Main.Conversions.Conversion;

import java.util.ArrayList;

public class DNAConversion14States implements Conversion {
    private int emission_conversion_char_to_int(Character c){

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
                default: states[i] = 0 ;   //This model does not model reverse coding, therefore any reversecoding found is set to noncoding(0)


            }
        }
        return states;
    }


    private int codingStates(String trueAnnotation,String observed, int[] states,int n) {
        boolean correctStartCodon = observed.charAt(n) == 'A' && observed.charAt(n+1) == 'T' && observed.charAt(n+2) == 'G' && trueAnnotation.charAt(n-1)== 'N';
        if( correctStartCodon){
            int endOfCoding = n;
            while(trueAnnotation.charAt(endOfCoding) != 'N'){
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
        else if(states[n-1] == 1)return 2;
        else if(states[n-1] == 2)return 3;
        else if(states[n-1] == 3) return 4;

        else if(observed.charAt(n) == 'T' && states[n-1] == 4 && observed.charAt(n+1) == 'A'
                && observed.charAt(n+2) == 'A' && trueAnnotation.charAt(n+3)== 'N' ) return 5; //&& trueAnnotation.charAt(n+3)== 'N'

        else if(states[n-1] == 5 ) return 6;
        else if(states[n-1] == 6 ) return 7;
        else if(observed.charAt(n) == 'T' && states[n-1] == 4 && observed.charAt(n+1) == 'A'
                && observed.charAt(n+2) == 'G' && trueAnnotation.charAt(n+3)== 'N' ) return 8;
        else if(states[n-1] == 8 ) return 9;
        else if(states[n-1] == 9 ) return 10;
        else if(observed.charAt(n) == 'T' && states[n-1] == 4 && observed.charAt(n+1) == 'G'
                && observed.charAt(n+2) == 'A' && trueAnnotation.charAt(n+3)== 'N' ) return 11;
        else if(states[n-1] == 11 ) return 12;
        else if(states[n-1] == 12 ) return 13;
        else if( n+4 < observed.length()) {
            if (observed.charAt(n + 4) != 'N' && (states[n-1] == 3|| states[n-1] == 4)) return 4; //We loop if we are not done in 4 steps. 4->5->6->4..
        }
        else return 0;
        return 0;
    }



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
                    stringsInt.get(l)[k] = emission_conversion_char_to_int(obs.charAt(k));
                }
            } else {
                stringsInt.add(convertAnnotationToState(annotation,obs));
            }

        }
        return stringsInt;
    }


    @Override
    public ArrayList<int[]> observables(String[] observables) {
        return convertStringToInt(observables,observables,true);
    }


    @Override
    public ArrayList<int[]> states(String[] trueAnnotation, String[] observed) {
        return convertStringToInt(trueAnnotation,observed,false);
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
        else if (i <14) return "C";
        else return "N"; //This model does not model reversecoding

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
        return "DNAConversion14States";
    }

    @Override
    public int getNumberOfStates() {
        return 14;
    }
    @Override
    public double[][] getInitialP(){
        double[][] P = {{0.9994664817527306, 5.335182472693972E-4, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 },
                {0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 },
                {0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0},
                {0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 },
                {0.0, 0.0, 0.0, 0.0, 0.998858566505379, 7.927650151061189E-4, 0.0, 0.0, 2.0424452599032957E-4, 0.0, 0.0, 1.4442395352454266E-4, 0.0, 0.0 },
                {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 },
                {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0},
                {1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0},
                {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0 },
                {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0 },
                {1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 },
                {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0 },
                {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0 },
                {1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 }};             // L -> H   L -> L
        return P;
    }
    @Override
    public double[][] getInitialE(){
        double[][] E = {{0.31768254314012095, 0.18164000763728133, 0.16667860546651458, 0.33399884375608313 },
                {1.0, 0.0, 0.0, 0.0 },
                {0.0, 0.0, 0.0, 1.0 },
                {0.0, 0.0, 1.0, 0.0 },
                {0.3374347457255352, 0.16410007924801553, 0.19627557115828012, 0.3021896038681691 },
                {0.0, 0.0, 0.0, 1.0 },
                {1.0, 0.0, 0.0, 0.0 },
                {1.0, 0.0, 0.0, 0.0 },
                {0.0, 0.0, 0.0, 1.0 },
                {1.0, 0.0, 0.0, 0.0 },
                {0.0, 0.0, 1.0, 0.0 },
                {0.0, 0.0, 0.0, 1.0 },
                {0.0, 0.0, 1.0, 0.0 },
                {1.0, 0.0, 0.0, 0.0 }};
        return E;
    }
    @Override
    public double[] getInitialPi(){
        double[] pi = {1.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,};
        return pi;
    }
}
