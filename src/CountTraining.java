/**
 * Class to handle the training by counting algorithm
 */
public class CountTraining {
    private double[][] E;
    private double[][] P;
    private double[] pi;
    private int N; //nr of states
    private int L; //nr of sequences
    private int M;

    public CountTraining(String[] observables,String[] states, int N, int M){
        this.L = observables.length;
        this.N = N; //Could read these from the data
        this.M = M;
        this.P = new double[N][N];
        this.E = new double[N][M];
        this.pi = new double[N];
        calculate(observables,states);
    }
    public double[] getPi() {
        return pi;
    }
    public double[][] getE() {
        return E;
    }
    public double[][] getP() {
        return P;
    }

    /**
     * Calculates the training by counting steps.
     *
     * @param observables
     * @param states
     */
    private void calculate(String[] observables, String[] states){
        int[] state_counts = new int[N]; //denominator 2.32
        int[] emission_counts = new int[N]; //denominator 2.34
        for (int l = 0; l < L; l++) {
            String observable = observables[l];
            String state = states[l];
            pi[state_conversion(observable.charAt(0))] +=1;
            for (int i = 0; i < observable.length(); i++) {
                int start_state = state_conversion(state.charAt(i));
                if(i+1<observable.length()) {
                    int end_state = state_conversion(state.charAt(i + 1));
                    P[start_state][end_state] += 1;
                    state_counts[start_state] += 1;
                }

                int emitted = emission_conversion(observable.charAt(i));
                E[start_state][emitted] += 1;
                emission_counts[start_state] += 1;
            }
        }
        for (int i = 0; i < N; i++) {
            pi[i] = pi[i]/L;
            for (int j = 0; j < N; j++) {
                P[i][j] = P[i][j] / state_counts[i];
            }
            for (int a = 0; a < M; a++) {
                E[i][a] = E[i][a] / emission_counts[i];
            }
        }
    }

    private int emission_conversion(Character c){
        int res = -1;
        switch(c){
            case 'A': res = 0;
                break;
            case 'C': res = 1;
                break;
            case 'G': res = 2;
                break;
            case 'T': res = 3;
                break;
            default:
        }
        return res;
    }
    private int state_conversion(Character c){
        int res = -1;
        switch(c){
            case 'C': res = 0;
                break;
            case 'N': res = 1;
                break;
            case 'R': res = 2;
                break;
            default:
        }
        return res;
    }
}