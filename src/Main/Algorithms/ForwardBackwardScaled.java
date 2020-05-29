package Main.Algorithms;

/**
 * Implements a scaled version of the Forward and Backward algorithms, due to Bishop.
 * @author Jens Kristian Refsgaard Nielsen & Thomas Damgaard Vinther
 */
public class ForwardBackwardScaled {
    private double[][] alpha;
    private double[][] beta;
    private double[][] E;
    private double[][] P;
    private double[] pi;
    private double[] c;
    private int[] observed;
    private int N; //nr of states
    private int K; //nr of observations
    private boolean betaRan;

    /**
     * Sets up the parameters to be used when calculating the forward and backward algorithm.
     * @param observed The observables to perform forward or backward on.
     * @param pi Initial state distribution vector
     * @param P Initial transition matrix
     * @param E Initial Emission matrix
     */
    public ForwardBackwardScaled(int[] observed, double[] pi, double[][] P, double[][] E){
        this.N = P.length; //nr of states
        this.K = observed.length;
        this.alpha = new double[N][K];
        this.beta = new double[N][K];
        this.c = new double[K];
        this.pi = pi;
        this.P = P;
        this.E = E;
        this.observed = observed;
        calculateAlphaBishop();
        this.betaRan = false;
    }

    /**
     * Returns the forward/alpha algorithm. If it has not been run yet, it will first run then return the result.
     * @return  A N x K matrix with alpha values.
     */
    public double[][] getAlpha() {
        return alpha;
    }

    /**
     * Returns scaling factors used.
     * @return vector of scaling factors.
     */
    public double[] getScalingFactors() {
        return c;
    }

    /**
     * Returns the backward/beta algorithm. If it has not been run yet, it will first run then return the result.
     * @return  A N x K matrix with alpha values.
     */
    public double[][] getBeta() {
        if (!betaRan){
            calculateBetaBishop();
        }
        return beta;
    }

    /**
     * Forward algorithm, that returns the alpha values as a N x K matrix, implemented according to Rabiner
     */
    private void calculateAlphaRabiner(){
        int firstObserved = observed[0];
        // alpha_1 =
        for (int i = 0; i < N; i++) { //initialization
            alpha[i][0] = pi[i]*E[i][firstObserved];
        }
        for (int i = 0; i < N; i++) {
            c[0] += alpha[i][0];
        }
        for (int i = 0; i < N; i++) {
            alpha[i][0] /= c[0];
        }
        // alpha_k =
        for (int k = 1; k < K; k++) {
            //calculate alpha hat
            for (int j = 0; j < N; j++) {
                double sum = 0;
                for (int i = 0; i < N; i++) {
                    sum += alpha[i][k-1] * P[i][j];
                }
                alpha[j][k] = sum * E[j][observed[k]];
            }
            //calculate scaling factor c_k
            for (int i = 0; i < N; i++) {
                c[k] += alpha[i][k];
            }
            //calculate alpha tilde, by scaling
            for (int j = 0; j < N; j++) {
                alpha[j][k] /= c[k];
            }
        }
    }

    /**
     * Backward algorithm, sets the beta values as a N x K matrix, according to Rabiner
     */
    private void calculateBetaRabiner(){
        //initialize the last column
        for (int i = 0; i < N; i++) {
            beta[i][K-1] = 1.0 / c[K-1];
        }
        //beta_k (i) = sum_j^N P[i,j] E[j,x_{k+1}] beta_{k+1} (j)
        for (int k = K-2; k >= 0; k--) {
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    beta[i][k] += P[i][j] * E[j][observed[k + 1]] * beta[j][k + 1] / c[k];
                }
            }
        }
        betaRan = true;
    }

    /**
     * Forward algorithm, due to Bishop
     */
    private void calculateAlphaBishop() {
        //init, calculate first column
        int xzero = observed[0];
        for (int i = 0; i < N; i++) {
            alpha[i][0] = pi[i] * E[i][xzero];
        }
        scaleAlphaBishop(0);
        for (int k = 0; k < K-1; k++) {
            int xkPlus = observed[k+1];
            //calculate c[k+1] * \hat{\alpha}[j][k+1]
            for (int j = 0; j < N; j++) {
                double sum = 0;
                for (int i = 0; i < N; i++) {
                    sum += alpha[i][k] * P[i][j];
                }
                alpha[j][k+1] = sum * E[j][xkPlus];
            }
            scaleAlphaBishop(k+1);
        }
    }

    /**
     * Calculates the scaling factors according to Bishop
     * @param k Which scaling factor to calculate
     */
    private void scaleAlphaBishop(int k){
        //calculate c[k] = \sum_j c[k] * \hat{\alpha}[j][k]
        for (int i = 0; i < N; i++) {
            c[k] += alpha[i][k];
        }
        //calculate \hat{\alpha}[j][k] = c[k] * \hat{\alpha}[j][k] / c[k]
        for (int i = 0; i < N; i++) {
            alpha[i][k] /= c[k];
        }
    }

    /**
     * Calculates the backward/Beta algorithm according to Bishop.
     */
    private void calculateBetaBishop(){
        //initialize the last column
        for (int i = 0; i < N; i++) {
            beta[i][K-1] = 1.0;
        }
        //beta_k (i) = sum_j^N P[i,j] E[j,x_{k+1}] beta_{k+1} (j)
        for (int k = K-2; k >= 0; k--) {
            int xkPlus = observed[k + 1];
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    //System.out.println(P[i][j] +" * "+ E[j][xkPlus] +" * "+ beta[j][k + 1] +" / "+ c[k+1]);
                    beta[i][k] += P[i][j] * E[j][xkPlus] * beta[j][k + 1] / c[k+1];
                }
            }
        }
        betaRan = true;
    }
}
