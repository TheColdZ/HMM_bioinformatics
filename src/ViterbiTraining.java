import java.util.ArrayList;

/**
 * Class to handle Viterbi training
 */
public class ViterbiTraining {
    private ArrayList<int[]> states;
    private double[][] E;
    private double[][] P;
    private double[] pi;
    private int N; //nr of states
    private int L; //nr of sequences
    private int M;

    public ViterbiTraining(int[][] observables,double[][] initial_P, double[][] initial_E, double[] initial_pi, int N, int M){
        this.L = observables.length;
        this.N = N; //Could read these from the data
        this.M = M;
        this.P = initial_P;
        this.E = initial_E;
        this.pi = initial_pi;
        this.states = new ArrayList<>();
        calculate(observables);
    }

    private void calculate(int[][] observables) {
        ArrayList<int[]> old_states = (ArrayList<int[]>) states.clone();
        do{
            Viterbi vit = new Viterbi(P,E,pi);
            for (int l = 0; l < L; l++) {
                //int[] state_as_int = vit.calculate(observables[l]);
                //this.states.set(l,state_as_int);
            }
            //CountTraining ct = new CountTraining(observables,states,N,M);
        } while(true);
    }

}
