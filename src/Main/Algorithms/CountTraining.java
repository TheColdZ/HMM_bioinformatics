package Main.Algorithms;

import java.util.ArrayList;

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

    /**
     * This method calculates the training by counting
     * @param observables   The observable X's
     * @param states        The true annotated gene in states
     * @param N             number of states
     * @param M             size of observable alphabet
     */
    public CountTraining(ArrayList<int[]> observables, ArrayList<int[]> states, int N, int M){
        this.L = observables.size();
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
    private void calculate(ArrayList<int[]> observables,ArrayList<int[]> states){
        int[] state_counts = new int[N]; //denominator (2.39)
        int[] emission_counts = new int[N]; //denominator (2.40)
        for (int l = 0; l < L; l++) {
            int[] observable = observables.get(l);
            int[] state = states.get(l);
            pi[state[0]] +=1;
            for (int k = 0; k < observable.length; k++) {
                int start_state = state[k];

                if(k+1 < observable.length) {
                    int end_state = state[k+1];
                    P[start_state][end_state] += 1;
                    state_counts[start_state] += 1;
                }

                int emitted = observable[k];
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
}