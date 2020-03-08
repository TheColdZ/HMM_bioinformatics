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

    public double[][] calculateAlpha(){
        char firstObserved = observed.charAt(0);
        for (int i = 0; i < N; i++) {
            alpha[i][1] = pi[i]*emissionProbability(i,firstObserved);
        }
        for (int t = 0; t < T-2; t++) {
            for (int j = 0; j < N; j++) {
                double sum = 0;
                for (int i = 0; i < N; i++) {
                    sum += alpha[i][t] * transition[i][j];
                }
                alpha[j][t+1] = sum*emissionProbability(j,observed.charAt(t+1));
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
