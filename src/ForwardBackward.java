public class ForwardBackward {
    private double[][] alpha;
    private double[][] beta;
    private EmissionProbability E;
    private double[][] P;
    private double[] pi;
    private String observed;
    private int N; //nr of observations
    private int T; //nr of states

    public ForwardBackward(String observed, double[] pi, double[][] P, EmissionProbability E){
        this.N = P.length; //nr of states
        this.T = observed.length();
        this.alpha = new double[N][T];
        this.beta = new double[N][T];
        this.pi = pi;
        this.P = P;
        this.E = E;
        this.observed = observed;
    }

    /**
     * Forward algorithm, that returns the alpha values as a T x N matrix
     * @return alpha
     */
    public double[][] calculateAlpha(){
        char firstObserved = observed.charAt(0);
        for (int i = 0; i < N; i++) { //initialization
            alpha[i][0] = pi[i]*E.lookup(i,firstObserved);
        }
        for (int t = 1; t < T; t++) {
            for (int j = 0; j < N; j++) {
                double sum = 0;
                for (int i = 0; i < N; i++) {
                    sum += alpha[i][t-1] * P[i][j];
                }
                alpha[j][t] = sum * E.lookup(j,observed.charAt(t));
            }
        }
        return alpha;
    }

    /**
     * Backward algorithm, that returns the beta values as a T x N matrix
     * @return beta
     */
    public double[][] calculateBeta(){
        //initialize the last column
        for (int i = 0; i < N; i++) {
            beta[i][T-1] = 1;
        }
        //beta_t (i) = sum_j^N P[i,j] E[j,x_{t+1}] beta_{t+1} (j)
        for (int t = T-2; t > 0; t--) {
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    beta[i][t] += P[i][j] * E.lookup(j,observed.charAt(t+1)) * beta[j][t+1];
                }
            }
        }
        return beta;
    }
}
