package Main.Algorithms;

import java.util.ArrayList;

/**
 * Class to handle Viterbi training
 * @author Jens Kristian Refsgaard Nielsen & Thomas Damgaard Vinther
 */
public class ViterbiTraining {
    private ArrayList<int[]> states;
    private double[][] E;
    private double[][] P;
    private double[] pi;
    private int N; //nr of states
    private int L; //nr of sequences
    private int M;  //Size of observable alphabet

    /**
     * This sets up the Viterbi Training, by providing the observables and initial parameters.
     * @param observables   Observables given as ints in int array in an ArrayList
     * @param initialP  Initial transition matrix
     * @param initialE  Initial Emission matrix
     * @param initialPi Initial state distribution vector
     */
    public ViterbiTraining(ArrayList<int[]> observables, double[][] initialP, double[][] initialE, double[] initialPi){
        this.L = observables.size();
        this.N = initialP.length;
        this.M = initialE[0].length;
        this.P = initialP;
        this.E = initialE;
        this.pi = initialPi;
        this.states = new ArrayList<>();
        for (int i = 0; i < L; i++) {
            int[] disposeable = new int[1];
            disposeable[0] = -1;
            states.add(disposeable);
        }
        calculate(observables);
    }

    /**
     * This method handles the actual Viterbi training, by using the viterbi algorithm. When it is done, the new parameters are set.
     * @param observables  Observables given as ints in int array in an ArrayList
     */
    private void calculate(ArrayList<int[]> observables){
        for (int i = 0; i < 10 ; i++) {
            Viterbi vit = new Viterbi(P,E,pi);
            for (int l = 0; l < L; l++) {
                int[] stateAsInt = vit.calculate(observables.get(l));
                this.states.set(l,stateAsInt);
            }
            CountTraining ct = new CountTraining(observables,states,N,M);
            P = ct.getP();
            E = ct.getE();
            pi = ct.getPi();
        }
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
