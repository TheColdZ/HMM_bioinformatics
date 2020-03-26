import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;

public class Viterbi {
    private Map decodeMap;
    private double[][] P;
    private double [][] E;
    private double[] pi;
    private double[][] delta;
    private int[] psi;

    /**
     ** @param transition a quadratic P matrix
     *  @param emission an E probability matrix, |states| x |E alphabet|
     *  @param start vector of starting probabilities.
     *  @param decodeMap map that codes states to kind of coding ability.
     */
    public Viterbi(double[][] transition, double[][] emission, double[] start, Map decodeMap){
        this.P = transition;
        this.E = emission;
        this.pi = start;
        this.decodeMap = decodeMap;
    }

    /**
     * Calculates the delta matrix, psi matrix and outputs it into files... stuff... more stuff happens.
     * @param filename Which file to read from.
     */
    public void viterbi(String filename){

        int states = P.length;
        String observed = reader(filename); //TODO Alternativt kunne man jo bare feed metoden strengen direkte... men...
        int inputLength = observed.length(); //length of input

        double [][] delta = new double[states][inputLength];
        int [][] psi = new int[states][inputLength];

        //Initilization step
        for (int i = 0; i <states ; i++) {
            delta[i][0] = log(pi[i]) + log(E[i][1]);
            psi[i][0] = 0;
        }

        //Recursion
        for (int k = 1; k < inputLength ; k++) {
            for (int i = 0; i <states ; i++) {    //1<=i<=N
                psi[i][k]=-1;
                double maxTransitionProbability = Double.NEGATIVE_INFINITY;

                for (int j = 0; j <states ; j++) {
                    double transitionProbability = log(P[j][i])+delta[j][k-1];
                    if(maxTransitionProbability < transitionProbability){
                        maxTransitionProbability = transitionProbability;
                        psi[i][k] = j;
                    }
                }

                delta[i][k]= maxTransitionProbability + log(emissionProbability(i,observed.charAt(k)));

                if(k % 10000 == 0 && i ==0){    //TODO debugging/progress tracker, delete at some point.
                    System.out.println("I'm alive, don't kill me yet!: "+ k);
                }
            }
        }


        //Termination
        int [] sk = new int[inputLength];

        double m = delta[0][inputLength-1];

        for (int j = 0; j <states ; j++) {
            if(m <= delta[j][inputLength-1]){
                m = delta[j][inputLength-1];    //Max
                sk[inputLength-1] = j;          //ArgMax
            }
        }

        //Backtracking
        for (int k = inputLength-2; k >-1 ; k--) {       //Backtracking through the most likely path. Remember to visit 0...
            sk[k] = psi[sk[k+1]][k+1];
        }


        this.delta = delta; //used for testing

        //writing to files
        writeDetltaToFile(filename,delta);
        writeStatesToFile(filename,sk);
        writeDecodingToFile(filename,sk,decodeMap);


        //TODO map print for debugging, delete at some point
        Map<Integer,Integer> stateCounter = new HashMap<>();
        for (int i = 0; i < states; i++) {
            stateCounter.put(i,0);
        }
        for (int j = 0; j < inputLength; j++) {
            stateCounter.put(sk[j],stateCounter.get(sk[j])+1);
        }
        System.out.println(stateCounter);
    }

    private void writeDecodingToFile(String filename, int[] sk, Map decodeMap) {
        StringBuilder decodeStringBuilder = new StringBuilder();
        for (int j = 0; j < sk.length; j++) {
            decodeStringBuilder.append(decodeMap.get(sk[j]));
        }
        try {
            FileWriter fw2 = new FileWriter("decoding_"+filename+".txt");   //Writing decoded states to file
            fw2.write(decodeStringBuilder.toString());
            fw2.close();
        }
        catch(Exception e){}

    }

    private void writeStatesToFile(String filename, int[] sk) {
        StringBuilder stateStringBuilder = new StringBuilder();    //Stringbuilder
        for (int j = 0; j < sk.length; j++) {
            stateStringBuilder.append(sk[j]);
        }
        try {
            FileWriter fw = new FileWriter("states_"+filename+".txt");      //Writing states to a file
            fw.write(stateStringBuilder.toString());
            fw.close();
        }
        catch(Exception e){}

    }


    /**
     * Our very own log function, in case of 0 input the method returns negative infinity
     * @param input double to perform log on
     * @return  ln(input)
     */
    private double log(double input){
        if(input == 0.0){
            return Double.NEGATIVE_INFINITY;
        }
        return Math.log(input);
    }



    public double emissionProbability(int state, char letter){
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

        return E[state][index];
    }


    public double[][] getDelta(){
        return delta;
    }



    /**
     * https://www.geeksforgeeks.org/different-ways-reading-text-file-java/
     * @param filename which file to read. Prerequisite the file is a .txt file.
     * @return string containing gene
     */

    public String reader(String filename) {
        File file = new File("C:\\Users\\thoma\\Bachelor\\"+filename+".txt");
        //File file = new File("E:\\Uni\\Dat\\HMM_bioinformatics\\genome1.txt");
        StringBuilder sb1 = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            br.readLine();  //First two lines are info in the file.
            br.readLine();
            String st;
            while ((st = br.readLine()) != null ){
                sb1.append(st);
            }
        }
        catch(Exception e){ }
        return sb1.toString();
    }


    public void writeDetltaToFile(String filename, double[][] delta){
        try{
            FileWriter outputFileWriter = new FileWriter("delta_"+filename+".txt");
            for (int j = 0; j <delta.length ; j++) {  // looper omvendt med j og i fordi pretty print TODO
                StringBuilder sb1 = new StringBuilder();
                for (int i = 0; i <delta[0].length  ; i++) {
                    sb1.append(" "+  delta[j][i]);
                }
                outputFileWriter.write(sb1.toString());
                outputFileWriter.write("\n");
            }
            outputFileWriter.close();
        }
        catch (Exception e){}
    }
}
