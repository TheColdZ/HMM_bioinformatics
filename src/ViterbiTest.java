import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class ViterbiTest {
    public Viterbi viterbi;

    @Before
    public void setup(){
        double[][] transition = {{0,0,0.9 ,0.1,0   ,0,0},
                    {1,0,0   ,0  ,0   ,0,0},
                    {0,1,0   ,0  ,0   ,0,0},
                    {0,0,0.05,0.9,0.05,0,0},
                    {0,0,0   ,0  ,0   ,1,0},
                    {0,0,0   ,0  ,0   ,0,1},
                    {0,0,0   ,0.1,0.9 ,0,0} };
        double[][] emission ={{0.3 ,0.25,0.25,0.2},
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
        this.viterbi = new Viterbi(transition,emission,start,decodeMap);
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
        viterbi.viterbi("genome1");
    }



    @Test
    public void rainExample(){
        double[][] transition = {{0.9 , 0.1},     // H -> H   H ->L
                                {0.2, 0.8}};    // L-> H    L -> L
        double[] start = {0.5, 0.5};
        double[][] emission = {{0.9, 0.1},         // x = sun|H    x= rain|H
                                {0.3, 0.7}};        // x = sun|L   x= rain|L


        Map decodeMap =  new HashMap<Integer, String>(){{ put(0,"C");
            put(1,"C");
           }};

        this.viterbi = new Viterbi(transition,emission,start,decodeMap);
        viterbi.viterbi("BachelorRainExample");
        double[][] delta = viterbi.getDelta();
        double[][] preCalculatedDelta = preCalculatedDelta();
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 5; j++) {
                System.out.println("delta"+delta[i][j]);
                System.out.println(preCalculatedDelta[i][j]);
                System.out.println();
                assert(compareFactor(delta[i][j],preCalculatedDelta[i][j],0.01));
            }
        }


    }

    private double[][] preCalculatedDelta(){
        double[][] preCalculatedDelta = new double[2][5];
        preCalculatedDelta[0][0] = Math.log(0.05);
        preCalculatedDelta[1][0] = Math.log(0.35);
        preCalculatedDelta[0][1] = Math.log(0.007);
        preCalculatedDelta[1][1] = Math.log(0.196);
        preCalculatedDelta[0][2] = Math.log(0.0039);
        preCalculatedDelta[1][2] = Math.log(0.1098);
        preCalculatedDelta[0][3] = Math.log(0.0022);
        preCalculatedDelta[1][3] = Math.log(0.0615);
        preCalculatedDelta[0][4] = Math.log(0.0012);
        preCalculatedDelta[1][4] = Math.log(0.0148);
        return preCalculatedDelta;
    }


}