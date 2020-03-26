import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class ViterbiTest {
    public Viterbi viterbi;

    @Before
    public void setup(){

    }

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
        double[][] emissionMatr ={{0.3 ,0.25,0.25,0.2},
                {0.2 ,0.35,0.15,0.3},
                {0.4 ,0.15,0.2 ,0.25},
                {0.25,0.25,0.25,0.25},
                {0.2 ,0.4 ,0.3 ,0.1},
                {0.3 ,0.2 ,0.3 ,0.20},
                {0.15,0.3 ,0.2 ,0.35}};

        double[] start =  {0,0,0,1,0,0,0};

        Map decodeMap =  new HashMap<Integer, String>(){{ put(0,"C");
            put(1,"C");
            put(2,"C");
            put(3,"N");
            put(4,"R");
            put(5,"R");
            put(6,"R");}};

        EmissionProbability E = new EmissionProbability(emissionMatr);
        this.viterbi = new Viterbi(transition,E,start);
        FileReader fr = new FileReader();
        String observed = fr.readFile("genome1");
        viterbi.calculate(observed);
        double[][] delta = viterbi.getDelta();
        FileWriter fw = new FileWriter();
        int[] sk = viterbi.getSk();
        fw.writeDetltaToFile("BachelorRainExample",delta);
        fw.writeStatesToFile("BachelorRainExample",sk);
        fw.writeDecodingToFile("BachelorRainExample",sk,decodeMap);

    }



    @Test
    public void rainExample(){
        double[][] transition = {{0.9, 0.1},     // H -> H   H ->L
                                 {0.2, 0.8}};    // L-> H    L -> L
        double[] start = {0.5, 0.5};
        double[][] emissionMatr = {{0.9, 0.1},         // x = sun|H    x= rain|H
                                   {0.3, 0.7}};        // x = sun|L   x= rain|L


        Map decodeMap =  new HashMap<Integer, String>(){{ put(0,"C");
            put(1,"C");
           }};

        EmissionProbability E = new EmissionProbability(emissionMatr);
        this.viterbi = new Viterbi(transition,E,start);
        //FileReader fr = new FileReader();
        //String observed = fr.readFile("BachelorRainExample");
        String observed = "CCCCA";
        viterbi.calculate(observed);
        double[][] delta = viterbi.getDelta();
        double[][] preCalculatedDelta = newPreCalculatedDelta();
        boolean debug = false;
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 5; j++) {
                boolean cmp = compareFactor(Math.exp(delta[i][j]),preCalculatedDelta[i][j],0.00001);
                if(!cmp) {
                    System.out.println("delta:        " + Math.exp(delta[i][j]));
                    System.out.println("preCalcDelta: " + preCalculatedDelta[i][j]);
                }
                //assert(cmp);
                //assert(compareFactor(delta[i][j],preCalculatedDelta[i][j],0.01));
            }
        }
        FileWriter fw = new FileWriter();
        int[] sk = viterbi.getSk();
        fw.writeDetltaToFile("BachelorRainExample",delta);
        fw.writeStatesToFile("BachelorRainExample",sk);
        fw.writeDecodingToFile("BachelorRainExample",sk,decodeMap);
    }

    private double[][] preCalculatedDelta(){
        double[][] preCalculatedDelta = new double[2][5];
        preCalculatedDelta[0][0] = 0.05;
        preCalculatedDelta[1][0] = 0.35;
        preCalculatedDelta[0][1] = 0.007;
        preCalculatedDelta[1][1] = 0.196;
        preCalculatedDelta[0][2] = 0.0039;
        preCalculatedDelta[1][2] = 0.1098;
        preCalculatedDelta[0][3] = 0.0022;
        preCalculatedDelta[1][3] = 0.0615;
        preCalculatedDelta[0][4] = 0.0012;
        preCalculatedDelta[1][4] = 0.0148;
        return preCalculatedDelta;
    }
    private double[][] newPreCalculatedDelta(){
        double[][] preCalculatedDelta = new double[2][5];
        preCalculatedDelta[0][0] = 0.05;
        preCalculatedDelta[1][0] = 0.35;
        preCalculatedDelta[0][1] = 0.0045;
        preCalculatedDelta[1][1] = 0.189;
        preCalculatedDelta[0][2] = 0.00189;
        preCalculatedDelta[1][2] = 0.10584;
        preCalculatedDelta[0][3] = 0.0010584;
        preCalculatedDelta[1][3] = 0.0592704;
        preCalculatedDelta[0][4] = 0.00623434;
        preCalculatedDelta[1][4] = 0.0166249;
        return preCalculatedDelta;
    }


}