import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        for (int i = 1; i < 6; i++) {
            viterbi(transitions(),emission(),start(),i);
        }
    }

    /**
     *
     * @param transition   a quadratic transistion matrix
     * @param emission  an emission probability matrix, |states| x |emission alphabet|
     * @param start vector of starting probabilities.
     * @param iteration to name the files according to iteration
     * @return
     */

    public static double viterbi( double[][] transition, double[][] emission, double[] start, int iteration ){
        int states = transition.length;
        String gene = reader(iteration);
        int inputLength = gene.length(); //length of input
        double [][] delta = new double[states][inputLength];
        int [][] psi = new int[states][inputLength];
        for (int i = 0; i <states ; i++) {
            delta[i][0] = log(start[i]) + log(emission[i][1]);
            //System.out.println(delta[i][0]);

        }
        for (int j = 0; j < inputLength-1 ; j++) {
            for (int i = 0; i <states ; i++) {
                psi[i][j+1]=-1;
                //System.out.println("input:"+j);
                //System.out.println("state:"+i);

                double maxTransProb = Double.NEGATIVE_INFINITY;
                for (int k = 0; k <states ; k++) {
                    double transitionProb = log(transition[k][i])+delta[k][j];
                    if(maxTransProb < transitionProb){
                        psi[i][j+1] = k;
                        maxTransProb = transitionProb;
                    }
                }
                delta[i][j+1]= maxTransProb+log(emissionProbability(i,gene.charAt(j)));
                //System.out.println(delta[i][j]);
                if(j % 10000 == 0 && i ==0){
                    System.out.println("I'm alive, don't kill me yet!: "+ j);
                }
            }
        }
        int [] qstar = new int[inputLength];
        double possibleMaxLikelihood = delta[0][inputLength-1];
        int possibleIndex =0;
        for (int i = 0; i <states ; i++) {

            if(possibleMaxLikelihood<delta[i][inputLength-1]){
                possibleMaxLikelihood = delta[i][inputLength-1];
                possibleIndex = i;
            }
        }

        //Printing below
        System.out.println("Done John, starting print");
        try{
            FileWriter outputFileWriter = new FileWriter("output"+iteration+".txt");

        //String builder2 = "";
        for (int j = 0; j <states ; j++) {  // looper omvendt med j og i fordi pretty print TODO
            StringBuilder sb1 = new StringBuilder();

            for (int i = 0; i <inputLength  ; i++) {
                sb1.append(" "+ (int) delta[j][i]);
                //builder2 += "  "+psi[j][i];
            }
             outputFileWriter.write("J"+j+" "+sb1.toString());
            outputFileWriter.write("\n");
            //System.out.println(j+sb1.toString());
            //sb1.append("\n");
            //builder2 += "\n";

        }
        }
        catch (Exception e){}
        //System.out.println(builder2);
        qstar[inputLength-1] = possibleIndex;
        for (int j = inputLength-2; j >0 ; j--) {       //Backtracking through the most likely path.
            qstar[j] = psi[qstar[j+1]][j];
        }
        StringBuilder stateStringBuilder = new StringBuilder();    //Stringuilder
        StringBuilder decodeStringBuilder = new StringBuilder();
        Map<Integer,Integer> stateCounter = new HashMap<>();
        for (int i = 0; i < 7; i++) {
            stateCounter.put(i,0);
        }
        for (int j = 0; j < inputLength; j++) {
            //System.out.print(qstar[j]+" ");
            stateCounter.put(qstar[j],stateCounter.get(qstar[j])+1);
            stateStringBuilder.append(qstar[j]);
            decodeStringBuilder.append(decodeState7states(qstar[j]));
        }
        System.out.println();
        System.out.println(stateCounter);
        try {
            FileWriter fw = new FileWriter("states"+iteration+".txt");      //Writing states to a file
            fw.write(stateStringBuilder.toString());
            FileWriter fw2 = new FileWriter("decoding"+iteration+".txt");   //Writing decoded states to file
            fw2.write(decodeStringBuilder.toString());
        }
        catch(Exception e){}


        return 0.1;
    }

    /**
     * This method decodes the state to a coding, non-coding and reverse coding denoted as C, N R
     * @param i the state to translate to "coding"
     * @return  what the given state did.
     */
    private static String decodeState7states(int i) {
        String decode = "";
        switch(i){
            case 0: decode = "C";
                break;
            case 1: decode = "C";
                break;
            case 2: decode = "C";
                break;
            case 3: decode = "N";
                break;
            case 4: decode = "R";
                break;
            case 5: decode = "R";
                break;
            case 6: decode = "R";
                break;
            default: ;
        }
        return decode;
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
        double[][] emissionProbabilities = emission();
        return emissionProbabilities[state][index];
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
        StringBuilder sb1 = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));

            String st;
            while ((st = br.readLine()) != null ){
                System.out.println(st);
                sb1.append(st);
            }
        }
        catch(Exception e){ }
        return sb1.toString();
    }
}





