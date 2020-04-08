import java.util.HashMap;
import java.util.Map;

public class Viterbi {
    private double[][] P;
    private double[][] E;
    private double[] pi;
    private double[][] delta;
    private int[][] psi;
    private int[] sk;

    /**
     * Sets up the model for a run of the Viterbi algorithm
     *
     * @param transition a quadratic P matrix
     *  @param emission an E probability matrix, |states| x |E alphabet|
     *  @param start vector of starting probabilities.
     */
    public Viterbi(double[][] transition, double[][] emission, double[] start){
        this.P = transition;
        this.E = emission;
        this.pi = start;
    }

    /**
     * Calculates the delta matrix, psi matrix and outputs it into files... stuff... more stuff happens.
     * @param observed obervations as string.
     */
    public int[] calculate(int[] observed){

        int states = P.length;
        int inputLength = observed.length; //length of input

        delta = new double[states][inputLength];
        psi = new int[states][inputLength];

        //Initilization step
        int firstObserved = observed[0];
        for (int i = 0; i < states ; i++) {
            delta[i][0] = log(pi[i]) + log(E[i][firstObserved]);
            psi[i][0] = 0;
        }
        //Recursion
        for (int k = 1; k < inputLength ; k++) {
            for (int i = 0; i < states ; i++) {    //1<=i<=N
                psi[i][k]=-1;

                double maxTransitionProbability = Double.NEGATIVE_INFINITY;
                for (int j = 0; j <states ; j++) {
                    double transitionProbability = log(P[i][j])+delta[j][k-1];
                    if(maxTransitionProbability < transitionProbability){
                        maxTransitionProbability = transitionProbability;
                        psi[i][k] = j;
                    }
                    /*
                    System.out.println("k,i,j =      "+k+","+i+","+j);
                    System.out.println(" P =         "+P[i][j]);
                    System.out.println(" delta =     "+Math.exp(delta[j][k-1]));
                    System.out.println(" prod =      "+Math.exp(transitionProbability));
                    System.out.println(" E[i,k] =    "+E[i][observed[k]]);
                    System.out.println(" next =      "+Math.exp(transitionProbability) * E[i][observed[k]]);
                    */
                }
                /*
                System.out.println("Delta["+i+","+k+"] = "+Math.exp(maxTransitionProbability)*E[i][observed[k]]);
                System.out.println();
                */
                delta[i][k]= maxTransitionProbability + log(E[i][observed[k]]);

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
        this.sk = sk;

        //TODO map print for debugging, delete at some point
        Map<Integer,Integer> stateCounter = new HashMap<>();
        for (int i = 0; i < states; i++) {
            stateCounter.put(i,0);
        }
        for (int j = 0; j < inputLength; j++) {
            stateCounter.put(sk[j],stateCounter.get(sk[j])+1);
        }
        System.out.println(stateCounter);
        return sk;
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


    public double[][] getDelta(){
        return delta;
    }

    public int[] getSk(){
        return sk;
    }


}
