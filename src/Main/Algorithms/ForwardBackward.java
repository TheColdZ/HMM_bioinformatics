package Main.Algorithms;

/**
 * Class to handle the alpha and beta algorithms of the forward backward procedure.
 * @author Jens Kristian Refsgaard Nielsen & Thomas Damgaard Vinther
 */
public class ForwardBackward {
    private double[][] alpha;
    private double[][] beta;
    private double[][] E;
    private double[][] P;
    private double[] pi;
    private int[] observed;
    private int N; //nr of states
    private int K; //nr of observations
    private boolean alphaRan;
    private boolean betaRan;

    /**
     * Sets up the parameters to be used when calculating the forward and backward algorithm.
     * @param observed The observables to perform forward or backward on.
     * @param pi Initial state distribution vector
     * @param P Initial transition matrix
     * @param E Initial Emission matrix
     */
    public ForwardBackward(int[] observed, double[] pi, double[][] P, double[][] E){
        this.N = P.length; //nr of states
        this.K = observed.length;
        this.alpha = new double[N][K];
        this.beta = new double[N][K];
        this.pi = pi;
        this.P = P;
        this.E = E;
        this.observed = observed;
        this.alphaRan = false;
        this.betaRan = false;
    }

    /**
     * Returns the forward/alpha algorithm. If it has not been run yet, it will first run then return the result.
     * @return  A N x K matrix with alpha values.
     */
    public double[][] getAlpha() {
        if (!alphaRan){
            calculateAlpha();
        }
        return alpha;
    }

    /**
     * Returns the backward/beta algorithm. If it has not been run yet, it will first run then return the result.
     * @return  A N x K matrix with beta values.
     */
    public double[][] getBeta() {
        if (!betaRan){
            calculateBeta();
        }
        return beta;
    }

    /**
     * Forward algorithm, that sets the alpha values as a N x K matrix
     *
     */
    private void calculateAlpha(){
        int firstObserved = observed[0];
        // alpha_1 =
        for (int i = 0; i < N; i++) { //initialization
            alpha[i][0] = pi[i]*E[i][firstObserved];
        }
        // alpha_t =
        for (int t = 1; t < K; t++) {
            for (int j = 0; j < N; j++) {
                double sum = 0;
                for (int i = 0; i < N; i++) {
                    sum += alpha[i][t-1] * P[i][j];
                }
                alpha[j][t] = sum * E[j][observed[t]];
            }
        }
        this.alphaRan = true;
    }

    /**
     * Backward algorithm, that sets the beta values as a N x K matrix
     *
     */
    private void calculateBeta(){
        //initialize the last column
        for (int i = 0; i < N; i++) {
            beta[i][K -1] = 1;
        }
        //beta_t (i) = sum_j^N P[i,j] E[j,x_{t+1}] beta_{t+1} (j)
        for (int t = K -2; t >= 0; t--) {
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    beta[i][t] += P[i][j] * E[j][observed[t + 1]] * beta[j][t + 1];
                }
            }
        }
        this.betaRan = true;
    }

    /**
     * Calculates P(X = x) based on the forward or backward algorithm
     * if none of them have run yet, it runs the forward/alpha algorithm.
     *
     * @return P(X=x)
     */
    public double calculateProbability(){
        double res = 0;
        if (this.alphaRan){
            for (int i = 0; i < this.N; i++) {
                res += this.alpha[i][this.K-1];
            }
        } else if (this.betaRan){
            for (int i = 0; i < this.N; i++) {
                res += this.pi[i] * this.E[i][0] * this.beta[i][0];
            }
        } else{
            calculateAlpha();
            res = calculateProbability();
        }
        return res;
    }
}
