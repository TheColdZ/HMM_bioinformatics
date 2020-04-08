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

    public ViterbiTraining(ArrayList<int[]> observables,double[][] initial_P, double[][] initial_E, double[] initial_pi){
        this.L = observables.size();
        this.N = initial_P.length;
        this.M = initial_E[0].length;
        this.P = initial_P;
        this.E = initial_E;
        this.pi = initial_pi;
        this.states = new ArrayList<>();
        for (int i = 0; i < L; i++) {
            states.add(new int[0]);
        }
        calculate(observables);
    }

    private void calculate(ArrayList<int[]> observables) {
        ArrayList<int[]> old_states = new ArrayList<>();
        do{
            old_states = (ArrayList<int[]>) states.clone(); //suspicious
            Viterbi vit = new Viterbi(P,E,pi);
            for (int l = 0; l < L; l++) {
                int[] state_as_int = vit.calculate(observables.get(l));
                this.states.set(l,state_as_int);
            }
            CountTraining ct = new CountTraining(observables,states,N,M);
            P = ct.getP();
            E = ct.getE();
            pi = ct.getPi();

        } while( states.containsAll(old_states) && old_states.containsAll(states) ); //does this test pointers or nested elements?
    }

    public double[][] getP() {
        return P;
    }

    public double[][] getE() {
        return E;
    }

    public double[] getPi() {
        return pi;
    }

    public ArrayList<int[]> getStates() {
        return states;
    }
}
