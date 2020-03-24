public class Forward {
    private double[][] alpha;
    private double[][] emission;
    private double[][] transition;
    private double[] pi;
    private String observed;
    private int N;
    private int T;

    public Forward(String observed, double[] pi, double[][] transition, double[][] emission){
        this.N = transition[0].length;
        this.T = transition.length;
        this.alpha = new double[N][T];
        this.pi = pi;
        this.transition = transition;
        this.emission = emission;
        this.observed = observed;
    }

    /**
     * Forward algorithm, that returns the alpha values as a T x N matrix
     * @return alpha
     */
    public double[][] calculateAlpha(){
        char firstObserved = observed.charAt(0);
        for (int i = 0; i < N; i++) { //initialization
            alpha[i][1] = pi[i]*emissionProbability(i,firstObserved);
        }
        for (int t = 1; t < T-1; t++) {
            for (int j = 0; j < N; j++) {
                double sum = 0;
                for (int i = 0; i < N; i++) {
                    sum += alpha[i][t-1] * transition[i][j];
                }
                alpha[j][t] = sum*emissionProbability(j,observed.charAt(t));
            }
        }
        return alpha;
    }

    private double emissionProbability(int state, char letter){
        String match = String.valueOf(letter);
        int index = -1;
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
        return emission[state][index];
    }
}
