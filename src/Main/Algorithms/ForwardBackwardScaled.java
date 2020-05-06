package Main.Algorithms;

public class ForwardBackwardScaled {
    private double[][] alpha;
    private double[][] beta;
    private double[][] E;
    private double[][] P;
    private double[] pi;
    private int[] observed;
    private int N; //nr of states
    private int K; //nr of observations
    private boolean betaRan;
    private double Pk;

    public ForwardBackwardScaled(int[] observed, double[] pi, double[][] P, double[][] E){
        this.N = P.length; //nr of states
        this.K = observed.length;
        this.alpha = new double[N][K];
        this.beta = new double[N][K];
        this.pi = pi;
        this.P = P;
        this.E = E;
        this.observed = observed;
        calculateAlpha();
        this.betaRan = false;
    }

    public double[][] getAlpha() {
        return alpha;
    }

    public double[][] getBeta() {
        if (!betaRan){
            calculateBeta();
        }
        return beta;
    }

    /**
     * Forward algorithm, that returns the alpha values as a N x K matrix
     * @return alpha
     */
    private double[][] calculateAlpha(){
        int firstObserved = observed[0];
        // alpha_1 =
        for (int i = 0; i < N; i++) { //initialization
            alpha[i][0] = pi[i]*E[i][firstObserved];
        }
        // alpha_k =
        for (int k = 1; k < K; k++) {
            for (int j = 0; j < N; j++) {
                double sum = 0;
                for (int i = 0; i < N; i++) {
                    sum += alpha[i][k-1] * P[i][j];
                }
                alpha[j][k] = sum * E[j][observed[k]];
            }
            Pk = calculateAlphaSum(k);
            for (int j = 0; j < N; j++) {
                alpha[j][k] /= Pk;
            }
        }
        return alpha;
    }

    /**
     * Backward algorithm, that returns the beta values as a N x K matrix
     * @return beta
     */
    private double[][] calculateBeta(){
        //initialize the last column
        for (int i = 0; i < N; i++) {
            beta[i][K-1] = 1;
        }
        //beta_k (i) = sum_j^N P[i,j] E[j,x_{k+1}] beta_{k+1} (j)
        for (int k = K-2; k >= 0; k--) {
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    beta[i][k] += P[i][j] * E[j][observed[k + 1]] * beta[j][k + 1] / calculateAlphaSum(k);
                }
            }
            for (int i = 0; i < N; i++) {
                beta[i][k] /= calculateAlphaSum(k);
            }
        }

        return this.beta;
    }

    /**
     * Calculates P(X = x) based on the forward or backward algorithm
     * if none of them have run yet, runs forward
     *
     * @return P(X=x)
     */
    public double calculateProbability(){
        double res = 0;
        for (int i = 0; i < this.N; i++) {
            res += this.alpha[i][K-1]*Pk;
        }
        return res;
        //return calculateAlphaSum(K-1);
    }

    /**
     *
     */
    private double calculateAlphaSum(int k){
        double res = 0;
        for (int i = 0; i < this.N; i++) {
            res += this.alpha[i][k];
        }
        return res;
    }
}
