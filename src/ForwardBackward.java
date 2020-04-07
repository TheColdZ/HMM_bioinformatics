/**
 * Class to handle the alpha and beta algorithms of the forward backward procedure.
 */
public class ForwardBackward {
    private double[][] alpha;
    private double[][] beta;
    private EmissionProbability E;
    private double[][] P;
    private double[] pi;
    private String observed;
    private int N; //nr of states
    private int K; //nr of observations

    public ForwardBackward(String observed, double[] pi, double[][] P, EmissionProbability E){
        this.N = P.length; //nr of states
        this.K = observed.length();
        this.alpha = new double[N][K];
        this.beta = new double[N][K];
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
        // alpha_1 =
        for (int i = 0; i < N; i++) { //initialization
            alpha[i][0] = pi[i]*E.lookup(i,firstObserved);
        }
        // alpha_t =
        for (int t = 1; t < K; t++) {
            for (int j = 0; j < N; j++) {
                double sum = 0;
                for (int i = 0; i < N; i++) {
                    sum += alpha[i][t-1] * P[i][j];/*
                    if(t == 4){
                        System.out.println("alpha["+i+",4]="+alpha[i][t-1]);
                        System.out.println("P["+i+","+j+"]="+P[i][j]);
                        System.out.println("sum="+sum);
                    }
                    */
                }
                alpha[j][t] = sum * E.lookup(j,observed.charAt(t));
                /*
                if(t == 4) {
                    System.out.println("E[" + j + "," + observed.charAt(t) + "]=" + E.lookup(j, observed.charAt(t)));
                    System.out.println("Done alpha[j][t]="+alpha[j][t]);
                    System.out.println();
                }
                */
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
            beta[i][K -1] = 1;
        }
        //beta_t (i) = sum_j^N P[i,j] E[j,x_{t+1}] beta_{t+1} (j)
        for (int t = K -2; t >= 0; t--) {
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    beta[i][t] += P[i][j] * E.lookup(j,observed.charAt(t+1)) * beta[j][t+1];
                }
            }
        }
        return beta;
    }
}
