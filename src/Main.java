import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        for (int i = 1; i < 6; i++) {
            System.out.println("Doing nr:"+i);
            viterbi(transitions(),emission(),start(),i,decodeMap());
        }
        //viterbi(exampleTransitions(),exampleEmission(),exampleStart(),1,decodeMap());

    }

    /**
     *
     * @param transition a quadratic transition matrix
     * @param emission an emission probability matrix, |states| x |emission alphabet|
     * @param start vector of starting probabilities.
     * @param iteration to name the files according to iteration
     * @return
     */
    public static double viterbi( double[][] transition, double[][] emission, double[] start, int iteration, Map decodeMap ){
        int states = transition.length;
        String gene = reader(iteration);
        //String gene = "CCCCA";
        int inputLength = gene.length(); //length of input
        double [][] delta = new double[states][inputLength];
        int [][] psi = new int[states][inputLength];
        for (int i = 0; i <states ; i++) {                      //Init step
            delta[i][0] = log(start[i]) + log(emission[i][1]);
            //System.out.println(delta[i][0]);

        }
        for (int j = 1; j < inputLength ; j++) {
            for (int i = 0; i <states ; i++) {
                psi[i][j]=-1;
                double maxTransProb = Double.NEGATIVE_INFINITY;
                for (int k = 0; k <states ; k++) {
                    double transitionProb = log(transition[k][i])+delta[k][j-1];
                    if(maxTransProb < transitionProb){
                        psi[i][j] = k;
                        maxTransProb = transitionProb;
                    }
                }
                delta[i][j]= maxTransProb+log(emissionProbability(i,gene.charAt(j)));

                if(j % 10000 == 0 && i ==0){
                    System.out.println("I'm alive, don't kill me yet!: "+ j);
                }
            }
        }
        int [] qstar = new int[inputLength];
        double possibleMaxLikelihood = delta[0][inputLength-1];
        int possibleIndex = 0;
        for (int i = 0; i <states ; i++) {

            if(possibleMaxLikelihood <= delta[i][inputLength-1]){
                possibleMaxLikelihood = delta[i][inputLength-1];
                possibleIndex = i;
            }

        }

        //Printing below
        try{
            FileWriter outputFileWriter = new FileWriter("output"+iteration+".txt");

            //String builder2 = "";
            for (int j = 0; j <states ; j++) {  // looper omvendt med j og i fordi pretty print TODO
                StringBuilder sb1 = new StringBuilder();

                for (int i = 0; i <inputLength  ; i++) {
                    sb1.append(" "+  delta[j][i]);
                    //builder2 += "  "+psi[j][i];
                }
                outputFileWriter.write(sb1.toString());
                outputFileWriter.write("\n");
                //System.out.println(j+sb1.toString());
                //sb1.append("\n");
                //builder2 += "\n";

            }
            //System.out.println(builder2);
            outputFileWriter.close();
        }
        catch (Exception e){}

        qstar[inputLength-1] = possibleIndex;

        //System.out.println("possible: "+possibleIndex);
        for (int j = inputLength-2; j >-1 ; j--) {       //Backtracking through the most likely path. Remember to visit 0...
            qstar[j] = psi[qstar[j+1]][j+1];
        }


        //System.out.println("Q star: "+q);
        StringBuilder stateStringBuilder = new StringBuilder();    //Stringbuilder
        StringBuilder decodeStringBuilder = new StringBuilder();
        Map<Integer,Integer> stateCounter = new HashMap<>();
        for (int i = 0; i < 7; i++) {
            stateCounter.put(i,0);
        }
        for (int j = 0; j < inputLength; j++) {
            //System.out.print(qstar[j]+" ");
            stateCounter.put(qstar[j],stateCounter.get(qstar[j])+1);
            stateStringBuilder.append(qstar[j]);
            decodeStringBuilder.append(decodeMap.get(qstar[j]));
        }
        //System.out.println();
        System.out.println(stateCounter);
        try {
            FileWriter fw = new FileWriter("states"+iteration+".txt");      //Writing states to a file
            fw.write(stateStringBuilder.toString());
            FileWriter fw2 = new FileWriter("decoding"+iteration+".txt");   //Writing decoded states to file
            fw2.write(decodeStringBuilder.toString());
            fw.close();
            fw2.close();
        }
        catch(Exception e){}


        return 0.1;
    }

    /**
     * Used to lookup the coding ability for the various states.
     * @return Map to lookup coding for states.
     */
    private static Map<Integer, String> decodeMap(){
        Map<Integer,String> map = new HashMap();
        map.put(0,"C");
        map.put(1,"C");
        map.put(2,"C");
        map.put(3,"N");
        map.put(4,"R");
        map.put(5,"R");
        map.put(6,"R");
        return map;
    }

    /**
     * Our very own log function, in case of 0 input the method returns negative infinity
     * @param input double to perform log on
     * @return  ln(input)
     */
    private static double log(double input){
        if(input ==0.0){
            return Double.NEGATIVE_INFINITY;
        }
        return Math.log(input);
    }

    private static String getGene() {
        String gene = "TTGTTGATATTCTGTTTTTTCTTTTTTAGTTTTCCACATGAAAAATAGTTGAAAACAATA"+
                "GCGGTGTCCCCTTAAAATGGCTTTTCCACAGGTTGTGGAGAACCCAAATTAACAGTGTTA" +
                "ATTTATTTTCCACAGGTTGTGGAAAAACTAACTATTATCCATCGTTCTGTGGAAAACTAG" +
                "AATAGTTTATGGTAGAATAGTTCTAGAATTATCCACAAGAAGGAACCTAGTATGACTGAA" +
                "AATGAACAAATTTTTTGGAACAGGGTCTTGGAATTAGCTCAGAGTCAATTAAAACAGGCA" +
                "ACTTATGAATTTTTTGTTCATGATGCCCGTCTATTAAAGGTCGATAAGCATATTGCAACT" ;
        return gene;
    }

    public static double[][] emission(){
        double[][] emissionProbabilities = {{0.3 ,0.25,0.25,0.2},
                {0.2 ,0.35,0.15,0.3},
                {0.4 ,0.15,0.2 ,0.25},
                {0.25,0.25,0.25,0.25},
                {0.2 ,0.4 ,0.3 ,0.1},
                {0.3 ,0.2 ,0.3 ,0.20},
                {0.15,0.3 ,0.2 ,0.35}};

        return emissionProbabilities;
    }
    public static double emissionProbability(int state, char letter){
        String match = String.valueOf(letter);
        int index = 0;
        switch(match){
            case "A": index = 0;
                break;
            case "C": index = 1;
                break;
            case "G": index = 2;
                break;
            case "T": index = 3;
                break;
            default:

        }
        double[][] emissionProbabilities = emission();   //TODO change back to emission
        return emissionProbabilities[state][index];
    }

    public static double[][] exampleTransitions(){
        double[][] transitionMatrix = {{0.9 , 0.1},     // H -> H   H ->L
                                        {0.2, 0.8}};    // L-> H    L -> L
        return transitionMatrix;
    }

    public static double[] exampleStart(){
        double[] startingState = {0.5, 0.5};
        return startingState;
    }

    public static double[][] exampleEmission(){
        double[][] emissionProbabilities = {{0.9, 0.1},         // x = sun|H    x= rain|H
                                            {0.3, 0.7}};        // x = sun|L   x= rain|L

        return emissionProbabilities;
    }


    public static double[] start(){
        double[] startingState = {0,0,0,1,0,0,0};
        return startingState;
    }
    public static double[][] transitions(){
        double[][] transitionMatrix = {{0,0,0.9 ,0.1,0   ,0,0},
                {1,0,0   ,0  ,0   ,0,0},
                {0,1,0   ,0  ,0   ,0,0},
                {0,0,0.05,0.9,0.05,0,0},
                {0,0,0   ,0  ,0   ,1,0},
                {0,0,0   ,0  ,0   ,0,1},
                {0,0,0   ,0.1,0.9 ,0,0} };
        return transitionMatrix;
    }

    /**
     * https://www.geeksforgeeks.org/different-ways-reading-text-file-java/
     * @param iteration which file to read.
     * @return string containing gene
     */

    public  static String reader(int iteration) {
        File file = new File("C:\\Users\\thoma\\Bachelor\\genome"+iteration+".txt");
        //File file = new File("E:\\Uni\\Dat\\HMM_bioinformatics\\genome1.txt");
        StringBuilder sb1 = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));

            String st;
            while ((st = br.readLine()) != null ){
                //System.out.println(st);
                sb1.append(st);
            }
        }
        catch(Exception e){ }
        return sb1.toString();
    }
}





