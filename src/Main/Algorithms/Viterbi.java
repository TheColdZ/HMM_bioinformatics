package Main.Algorithms;

/**
 *  This class handles the Viterbi Algorithm.
 * @author Jens Kristian Refsgaard Nielsen & Thomas Damgaard Vinther
 */
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
     *  @param transition a quadratic P matrix
     *  @param emission an E probability matrix, |states| x |E alphabet|
     *  @param start vector of starting probabilities.
     */
    public Viterbi(double[][] transition, double[][] emission, double[] start){
        this.P = transition;
        this.E = emission;
        this.pi = start;
    }

    /**
     * Calculates the delta matrix and psi matrix.
     * @param observed obervations as int array.
     * @return an array of the most likely states to have produced the observed
     */
    public int[] calculate(int[] observed){

        int N = P.length;
        int K = observed.length; //length of input

        delta = new double[N][K];
        psi = new int[N][K];

        //Initialization step
        int firstObserved = observed[0];
        for (int i = 0; i < N ; i++) {
            delta[i][0] = log(pi[i]) + log(E[i][firstObserved]);
            psi[i][0] = 0;
        }
        //Recursion
        for (int k = 1; k < K ; k++) {
            for (int i = 0; i < N ; i++) {    //1<=i<=N
                psi[i][k] = -1;

                double maxTransitionProbability = Double.NEGATIVE_INFINITY;
                for (int j = 0; j <N ; j++) {

                    double transitionProbability = log(P[j][i])+delta[j][k-1];

                    if(maxTransitionProbability < transitionProbability){
                        maxTransitionProbability = transitionProbability;
                        psi[i][k] = j;
                    }


                }
                delta[i][k]= maxTransitionProbability + log(E[i][observed[k]]);

            }
        }


        //Termination
        int [] sk = new int[K];

        double m = delta[0][K-1];

        for (int j = 0; j <N ; j++) {
            if(m <= delta[j][K-1]){
                m = delta[j][K-1];    //Max
                sk[K-1] = j;          //ArgMax
            }
        }

        //Backtracking
        for (int k = K-2; k >-1 ; k--) {       //Backtracking through the most likely path. Remember to visit 0...
            sk[k] = psi[sk[k+1]][k+1];
        }

        this.sk = sk;

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
