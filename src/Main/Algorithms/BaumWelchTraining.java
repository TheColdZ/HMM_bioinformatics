package Main.Algorithms;

import java.util.ArrayList;

public class BaumWelchTraining {
    private final double compareFactor;
    private ArrayList<int[]> states;
    private double[][] E;
    private double[][] P;
    private double[] pi;
    private int N; //nr of states
    private int L; //nr of sequences
    private int M;

    public BaumWelchTraining(ArrayList<int[]> observables, double[][] initial_P, double[][] initial_E, double[] initial_pi, double compareFactor) {
        this.L = observables.size();
        this.N = initial_P.length;
        this.M = initial_E[0].length;
        this.P = initial_P;
        this.E = initial_E;
        this.pi = initial_pi;
        this.states = new ArrayList<>();
        this.compareFactor = compareFactor;
        for (int i = 0; i < L; i++) {
            int[] disposeable = new int[1];
            disposeable[0] = -1;
            states.add(disposeable);
        }
        calculate(observables);
    }

    private void calculate(ArrayList<int[]> observables) {
        double initialLikelihood = 1;
        for (int l = 0; l < L; l++) {
            ForwardBackward initialFwdBck = new ForwardBackward(observables.get(l), pi, P, E);
            initialLikelihood *= initialFwdBck.calculateProbability();
        }
        double newLikelihood = initialLikelihood;
        //System.out.println(newLikelihood);
        int i = 1;
        do {
            initialLikelihood = newLikelihood;
            newLikelihood = reestimate(observables);
            //System.out.println(i);
            i++;
            //run until reestimation grants only a small increase in likelihood
            //System.out.println(initialLikelihood + " == " + newLikelihood);
        } while (initialLikelihood <= newLikelihood * (1 - compareFactor));
    }

    private double reestimate(ArrayList<int[]> observables) {
        double[] newPi = new double[N];
        double[][] newP = new double[N][N];
        double[] pDenominators = new double[N];
        double[][] newE = new double[N][M];
        double[] eDenominators = new double[N];

        double likelihood = 1;

        for (int l = 0; l < L; l++) {
            int[] obsl = observables.get(l);
            ForwardBackward fwdBck = new ForwardBackward(obsl, this.pi, this.P, this.E);
            double[][] alphal = fwdBck.calculateAlpha();
            double[][] betal = fwdBck.calculateBeta();
            double Pl = fwdBck.calculateProbability();
            int Kl = obsl.length;
            for (int k = 0; k < Kl - 2; k++) {
                int xlk = obsl[k];
                int xlkPlusPlus = obsl[k + 1];
                for (int i = 0; i < N; i++) {
                    for (int j = 0; j < N; j++) {
                        newP[i][j] += alphal[i][k] * this.P[i][j] * this.E[j][xlkPlusPlus] * betal[j][k + 1] / Pl;
                    }
                    pDenominators[i] += alphal[i][k] * betal[i][k] / Pl;
                    for (int a = 0; a < M; a++) {

                        if (xlk == a) {
                            newE[i][a] += alphal[i][k] * betal[i][k] / Pl;
                        }
                    }
                    eDenominators[i] += alphal[i][k] * betal[i][k] / Pl;
                }
            }
            for (int i = 0; i < N; i++) {
                    newPi[i] += alphal[i][0]  * betal[i][0] / Pl;
            }
            likelihood *= Pl;
        }

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                newP[i][j] = newP[i][j] / pDenominators[i];
            }
            for (int j = 0; j < M; j++) {
                newE[i][j] = newE[i][j] / eDenominators[i];
            }
            newPi[i] = newPi[i] / L;
        }

        this.P = newP;
        this.E = newE;
        this.pi = newPi;

        return likelihood;
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
