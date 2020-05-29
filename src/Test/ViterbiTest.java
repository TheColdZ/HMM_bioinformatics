package Test;

import Main.Algorithms.Viterbi;
import Main.Conversions.Conversion;
import Main.Conversions.DNAConversion.DNAConversion3States;
import Main.Conversions.weather_conversion;
import Main.FileInteraction.FileReader;
import Main.FileInteraction.FileWriter;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Jens Kristian Jensen & Thomas Damgaard Vinther
 */
public class ViterbiTest {
    private Viterbi viterbi;

    /**
     * Check if n1 in [n2*(1-factor) , n2*(1+factor)], for instance a deviation of 0.01 means within 1%
     * @param n1 first number
     * @param n2 second number
     * @param factor allowed deviation in %
     * @return true if they are close
     */
    private boolean compareFactor(double n1, double n2, double factor){
        double standardDeviation = factor;
        return n2-standardDeviation <= n1 && n1 <= n2+standardDeviation;
    }


    @Test
    public void viterbiTestFirstFile(){
        double[][] transition = {{0,0,0.9 ,0.1,0   ,0,0},
                {1,0,0   ,0  ,0   ,0,0},
                {0,1,0   ,0  ,0   ,0,0},
                {0,0,0.05,0.9,0.05,0,0},
                {0,0,0   ,0  ,0   ,1,0},
                {0,0,0   ,0  ,0   ,0,1},
                {0,0,0   ,0.1,0.9 ,0,0} };

        double[][] E ={{0.3 ,0.25,0.25,0.2},
                {0.2 ,0.35,0.15,0.3},
                {0.4 ,0.15,0.2 ,0.25},
                {0.25,0.25,0.25,0.25},
                {0.2 ,0.4 ,0.3 ,0.1},
                {0.3 ,0.2 ,0.3 ,0.20},
                {0.15,0.3 ,0.2 ,0.35}};


        double[] start =  {0,0,0,1,0,0,0};

        Map decodeMap =  new HashMap<Integer, String>(){{
            put(0,"C");
            put(1,"C");
            put(2,"C");
            put(3,"N");
            put(4,"R");
            put(5,"R");
            put(6,"R");}};

        Viterbi viterbi = new Viterbi(transition,E,start);
        FileReader fr = new FileReader();
        String observed = fr.readFile("genome1");
        String[] obs = new String[1];
        obs[0] = observed;
        Conversion conv = new DNAConversion3States();
        int[] sk = viterbi.calculate(conv.observables(obs).get(0));
        double[][] delta = viterbi.getDelta();
        FileWriter fw = new FileWriter();
        //ArrayList<int[]> temp = new ArrayList<>();
        //temp.add(sk);
        //String[] sk_string = conv.states(temp);
        //fw.writeStatesToFile("BachelorRainExample",sk_string[0]);
        fw.writeStatesToFile("BachelorRainExample", Arrays.toString(sk));   //This line replaces the four above. If we want it to... TODO
        fw.writeDetltaToFile("BachelorRainExample",delta);
        fw.writeDecodingToFile("BachelorRainExample",sk,decodeMap);

    }



    @Test
    public void rainExample(){
        double[][] transition = {{0.9, 0.1},     // H -> H   H ->L
                                 {0.2, 0.8}};    // L-> H    L -> L
        double[] start = {0.5, 0.5};
        double[][] E = {{0.9, 0.1},         // x = sun|H    x= rain|H
                        {0.3, 0.7}};        // x = sun|L   x= rain|L


        Map decodeMap =  new HashMap<Integer, String>(){{
            put(0,"H");
            put(1,"L");
           }};

        Viterbi viterbi = new Viterbi(transition,E,start);
        FileReader fr = new FileReader();
        String observed = fr.readFile("BachelorRainExample");
        //String observed = "RRRRS";
        String[] obs = new String[1];
        obs[0] = observed;
        Conversion conv = new weather_conversion();
        viterbi.calculate(conv.observables(obs).get(0));
        double[][] delta = viterbi.getDelta();
        double[][] preCalculatedDelta = preCalculatedDelta(); //TODO rename
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 5; j++) {
                boolean cmp = compareFactor(Math.exp(delta[i][j]),preCalculatedDelta[i][j],0.001);
                if(!cmp) {
                    System.out.println("delta:        " + Math.exp(delta[i][j]));
                    System.out.println("preCalcDelta: " + preCalculatedDelta[i][j]);
                }
                assert(cmp);
            }
        }
        FileWriter fw = new FileWriter();
        int[] sk = viterbi.getSk();
        ArrayList<int[]> temp = new ArrayList<>();
        temp.add(sk);
        String[] sk_string = conv.states(temp);
        fw.writeDetltaToFile("BachelorRainExample",delta);
        fw.writeStatesToFile("BachelorRainExample",sk_string[0]);
        fw.writeDecodingToFile("BachelorRainExample",sk,decodeMap);
    }


    private double[][] preCalculatedDelta(){
        double[][] preCalculatedDelta = new double[2][5];
        preCalculatedDelta[0][0] = 0.05;
        preCalculatedDelta[1][0] = 0.35;
        preCalculatedDelta[0][1] = 0.0045;
        preCalculatedDelta[1][1] = 0.196;
        preCalculatedDelta[0][2] = 0.00196;
        preCalculatedDelta[1][2] = 0.10976;
        preCalculatedDelta[0][3] = 0.0010976;
        preCalculatedDelta[1][3] = 0.0614656;
        preCalculatedDelta[0][4] = 0.005531904;
        preCalculatedDelta[1][4] = 0.014751744;
        return preCalculatedDelta;
    }



}
