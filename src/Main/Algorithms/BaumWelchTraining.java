package Main.Algorithms;

import java.util.ArrayList;

/**
 *  This class handles Baum-Welch training
 *
 * @author Jens Kristian Refsgaard Nielsen & Thomas Damgaard Vinther
 */
public class BaumWelchTraining {
    private ArrayList<int[]> states;
    private double[][] E;
    private double[][] P;
    private double[] pi;
    private int N; //nr of states
    private int L; //nr of sequences
    private int M;  //Size of observable alphabet in observations

    /**
     * This constructor sets up and performs Baum-Welch on the provided observables with the provided initial parameters.
     * It will iterate until all entries in the new parameters have no greater change than the compareFactor
     * @param observables   Observables given as ints in int array in an ArrayList
     * @param initial_P Initial transition matrix.
     * @param initial_E Initial Emission matrix
     * @param initial_pi Initial state distribution vector.
     * @param compareFactor Comparison factor used to compare the newly generated parameters
     */
    public BaumWelchTraining(ArrayList<int[]> observables, double[][] initial_P, double[][] initial_E, double[] initial_pi, double compareFactor) {
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
        converge(observables,compareFactor);
    }

    /**
     * This method handles the iterations until the parameters converge within the CompareFactor
     * @param observables   The observables
     * @param compareFactor compareFactor used to compare the initial parameters and the generated parameters.
     */
    private void converge(ArrayList<int[]> observables, double compareFactor){
        double[][] oldP;
        double[][] oldE;
        double[] oldPi;
        do {
            oldP = P;
            oldE = E;
            oldPi = pi;
            reestimateBishop(observables);
        } while(!(compareMatrix(oldP,P,compareFactor) && compareMatrix(oldE,E,compareFactor) && compareVector(oldPi,pi,compareFactor)));
    }

    /**
     * This method compares every entry in two vectors. It returns true if the difference is less than the compareFactor and false if not.
     * @param A First vector
     * @param B Second vector
     * @param compareFactor ComparisonVector that every entry is compared to.
     * @return  Boolean value, if every corresponding value in the two vectors are within the comparison factor.
     */
    private boolean compareVector(double[] A, double[] B, double compareFactor){
        for (int j = 0; j < A.length; j++) {
            if(Math.abs(A[j]-B[j])>compareFactor){
                return false;
            }
        }
        return true;
    }

    /**
     * This method compares every entry in two matrix. It returns true if the difference is less than the compareFactor and false if not.
     * @param A First matrix
     * @param B Second matrix
     * @param compareFactor ComparisonVector that every entry is compared to.
     * @return Boolean value, if every corresponding value in the two matrix are within the comparison factor.
     */
    private boolean compareMatrix(double[][] A, double[][] B, double compareFactor){
        for (int i = 0; i < A.length; i++) {
            if(!compareVector(A[i],B[i],compareFactor)){
                return false;
            }
        }
        return true;
    }

    /**
     * Check if n1 in [n2*(1-factor) , n2*(1+factor)], for instance a deviation of 0.01 means within 1%
     * @param n1 first number
     * @param n2 second number
     * @param factor allowed deviation in %
     * @return true if they are close
     */
    private boolean compareDoubles(double n1, double n2, double factor){
        double standardDeviation = n2*factor;
        return n2-standardDeviation <= n1 && n1 <= n2+standardDeviation;
    }




    /**
     * This method handles the actual reestimating of parameters according to Bishop.
     * @param observables The observables to reestimate parameters on.
     */
    private void reestimateBishop(ArrayList<int[]> observables) {
        double[] newPi = new double[N];
        double[][] newP = new double[N][N];
        double[] denominatorsP = new double[N];
        double[] denominatorsE = new double[N];
        double[][] newE = new double[N][M];
        for (int l = 0; l < L; l++) {
            int[] obsl = observables.get(l);
            ForwardBackwardScaled fwdBck = new ForwardBackwardScaled(obsl, this.pi, this.P, this.E);
            double[][] alphal = fwdBck.getAlpha();

            double[][] betal = fwdBck.getBeta();

            double[] cl = fwdBck.getScalingFactors();

            int Kl = obsl.length;
            //pi[i] = 1/L sum_l alpha_l[i,1] * beta_l[i,1]
            for (int i = 0; i < N; i++) {
                newPi[i] += alphal[i][0] * betal[i][0];
            }
            for (int k = 0; k < Kl-1; k++) {
                int xlk = obsl[k];
                int xlkplus = obsl[k + 1];
                for (int i = 0; i < N; i++) {
                    for (int j = 0; j < N; j++) {
                        newP[i][j] += alphal[i][k] * P[i][j] * E[j][xlkplus] *
                                betal[j][k + 1] / cl[k + 1];
                    }
                    //sum_l sum_k alphal[i,k] * betal[i,k]
                    denominatorsP[i] += alphal[i][k] * betal[i][k];
                }
            }
            for (int k = 0; k < Kl; k++) {
                int xlk = obsl[k];
                for (int i = 0; i < N; i++) {
                    for (int a = 0; a < M; a++) {
                        if (xlk == a) {
                            newE[i][a] += alphal[i][k] * betal[i][k];
                            break; //we know that xlk hits exactly one a, so when it is found stop looking.
                        }
                    }
                    denominatorsE[i] += alphal[i][k] * betal[i][k];
                }
            }
        }

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if(denominatorsP[i] == 0 ){
                    newP[i][j] = 0;
                    if(i == j){
                        newP[i][j] = 1;
                    }
                } else {
                    newP[i][j] /= denominatorsP[i];
                }
            }
            for (int a = 0; a < M; a++) {
                if(denominatorsE[i] == 0 ){
                    newE[i][a] = 1.0/M;
                } else {
                    newE[i][a] /= denominatorsE[i];
                }
            }
            newPi[i] /= L;
        }
        this.P = newP;
        this.E = newE;
        this.pi = newPi;
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


}
