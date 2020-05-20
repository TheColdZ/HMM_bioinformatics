package Main.Algorithms;

/**
 * Implements a scaled version of the Forward and Backward algorithms, due to Bishop.
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

    public double[][] getAlpha() {
        return alpha;
    }

    public double[] getScalingFactors() {
        return c;
    }

    public double[][] getBeta() {
        if (!betaRan){
            calculateBetaBishop();
        }
        return beta;
    }

    /**
     * Forward algorithm, that returns the alpha values as a N x K matrix
     * Rabiner
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
     * Backward algorithm, that returns the beta values as a N x K matrix
     * Rabiner
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
        for (int i = 0; i < N; i++) {
            alpha[i][0] = pi[i] * E[i][observed[0]];
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
