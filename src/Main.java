import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class Main {

    public static void main(String[] args) {
        viterbi(7,transitions(),emission(),start());
        //reader();
    }

    /**
     *
     * @param states number of states
     * @param transition   a quadratic transistion matrix
     * @param emission  an emission probability matrix, |states| x |emission alphabet|
     * @param start vector of starting probabilities.
     * @return
     */

    public static double viterbi(int states, double[][] transition, double[][] emission, double[] start ){
        //String gene = "ACGATGCGCTAATATGTCCGATGACGTGAGCATAAGCGACAT";
        String gene = reader();
        int inputs = gene.length(); //length of input
        double [][] delta= new double[states][inputs];
        int [][] psi = new int[states][inputs];
        for (int i = 0; i <states ; i++) {
            delta[i][0] = log(start[i]) + log(emission[i][1]);
            //System.out.println(delta[i][0]);

        }
        for (int j = 0; j < inputs-1 ; j++) {
            for (int i = 0; i <states ; i++) {
                psi[i][j+1]=-1;
                //System.out.println("input:"+j);
                //System.out.println("state:"+i);

                double maxTrans = Double.NEGATIVE_INFINITY;
                for (int k = 0; k <states ; k++) {
                    double trans = log(transition[k][i])+delta[k][j];
                    if(maxTrans < trans){
                        psi[i][j+1] = k;
                        maxTrans = trans;
                    }
                }
                delta[i][j+1]= maxTrans+log(emissionProbability(i,gene.charAt(j)));
                //System.out.println(delta[i][j]);
                if(j % 10000 == 0 && i ==0){
                    System.out.println("I'm alive, don't kill me yet!: "+ j);
                }
            }
        }
        int [] qstar = new int[inputs];
        double possible = delta[0][inputs-1];
        int possibleIndex =0;
        for (int i = 0; i <states ; i++) {

            if(possible<delta[i][inputs-1]){
                possible = delta[i][inputs-1];
                possibleIndex = i;
            }
        }

        //Printing below
        System.out.println("Done John, starting print");
        try{
            FileWriter fw=new FileWriter("output.txt");

        //String builder2 = "";
        for (int j = 0; j <states ; j++) {  // looper omvendt med j og i fordi pretty print TODO
            StringBuilder sb1 = new StringBuilder();

            for (int i = 0; i <inputs  ; i++) {
                sb1.append(" "+ (int) delta[j][i]);
                //builder2 += "  "+psi[j][i];
            }
             fw.write("J"+j+" "+sb1.toString());
            fw.write("\n");
            //System.out.println(j+sb1.toString());
            //sb1.append("\n");
            //builder2 += "\n";

        }
        }
        catch (Exception e){}
        //System.out.println(builder2);
        qstar[inputs-1] = possibleIndex;
        for (int j = inputs-2; j >0 ; j--) {
            qstar[j] = psi[qstar[j+1]][j];
        }

        for (int j = 0; j < inputs; j++) {
            System.out.print(qstar[j]+" ");
        }


        return 0.1;
    }

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
        double[][] emissionProbabilities = {{0.3,0.25,0.25,0.2},
                                            {0.2,0.35,0.15,0.3},
                                            {0.4,0.15,0.2,0.25},
                                            {0.25,0.25,0.25,0.25},
                                            {0.2,0.4,0.3,0.1},
                                            {0.3,0.2,0.3,0.20},
                                            {0.15,0.3,0.2,0.35}};

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
         double[][] transitionMatrix = {{0,0,0.9,0.1,0,0,0},
                                        {1,0,0,0,0,0,0},
                                        {0,1,0,0,0,0,0},
                                        {0,0,0.05,0.9,0.05,0,0},
                                        {0,0,0,0,0,1,0},
                                        {0,0,0,0,0,0,1},
                                        {0,0,0,0.1,0.9,0,0} };
       return transitionMatrix;
    }

    /**
     * https://www.geeksforgeeks.org/different-ways-reading-text-file-java/
     * @return
     */

    public  static String reader() {
        File file = new File("C:\\Users\\thoma\\Bachelor\\genome1.txt");
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





