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
            int[] disposeable = new int[1];
            disposeable[0] = -1;
            states.add(disposeable);
        }
        calculate(observables);
    }

    private void calculate(ArrayList<int[]> observables) {
        ArrayList<int[]> old_states = new ArrayList<>();
        do{
            old_states = (ArrayList<int[]>) states.clone(); //suspicious TODO
            Viterbi vit = new Viterbi(P,E,pi);
            for (int l = 0; l < L; l++) {
                int[] state_as_int = vit.calculate(observables.get(l));
                this.states.set(l,state_as_int);
            }
            CountTraining ct = new CountTraining(observables,states,N,M);
            P = ct.getP();
            E = ct.getE();
            pi = ct.getPi();
            print_matr(states,"calc");
        } while(!compareStates(states,old_states) ); //does this test pointers or nested elements?
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

    private void print_matr(ArrayList<int[]> input, String name){
        System.out.println(name);
        for (int[] ints : input) {
            for (int anInt : ints) {
                System.out.print(anInt);
                System.out.print(" ");
            }
            System.out.println();
        }
    }

    private boolean compareStates(ArrayList<int[]> list1 ,ArrayList<int[]> list2 ){
        for (int l = 0; l < list1.size(); l++) {
            int[] sequence1 = list1.get(l);
            int[] sequence2 = list2.get(l);
            int K = Math.min(sequence1.length,sequence2.length);
            for (int k = 0; k < K; k++) {
                if (sequence1[k] != sequence2[k]){
                    return false;
                }
            }
        }
        return true;
    }
}
