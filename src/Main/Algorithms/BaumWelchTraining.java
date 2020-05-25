package Main.Algorithms;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.function.Function;

public class BaumWelchTraining {
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
        for (int i = 0; i < L; i++) {
            int[] disposeable = new int[1];
            disposeable[0] = -1;
            states.add(disposeable);
        }
        //calculate(observables);
        //iterate(observables,500);
        converge(observables,compareFactor);
    }

    private void converge(ArrayList<int[]> observables, double compareFactor){
        double[][] oldP;
        double[][] oldE;
        double[] oldPi;
        int iterations = 0;
        do {
            iterations++;
            oldP = P;
            oldE = E;
            oldPi = pi;
            reestimateBishop(observables);
            /*
            System.out.println("Comparing");
            print_double(oldE);
            System.out.println("-----");
            print_double(E);
            */
        } while(!(compareMatrix(oldP,P,compareFactor) && compareMatrix(oldE,E,compareFactor) && compareVector(oldPi,pi,compareFactor)));
        System.out.println(iterations);
    }

    private boolean compareVector(double[] A, double[] B, double compareFactor){
        for (int j = 0; j < A.length; j++) {
            if(Math.abs(A[j]-B[j])>compareFactor){
                return false;
            }
        }
        return true;
    }

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

    private void iterate(ArrayList<int[]> observables, int n){
        for (int i = 0; i < n; i++) {
            reestimateBishop(observables);
        }
    }

    private double reestimateRabiner(ArrayList<int[]> observables) {
        double[] newPi = new double[N];
        double[][] newP = new double[N][N];
        double[] pDenominators = new double[N];
        double[][] newE = new double[N][M];
        double[] eDenominators = new double[N];

        double likelihood = 1;

        for (int l = 0; l < L; l++) {
            int[] obsl = observables.get(l);
            ForwardBackwardScaled fwdBck = new ForwardBackwardScaled(obsl, this.pi, this.P, this.E);
            double[][] alphal = fwdBck.getAlpha();
            double[][] betal = fwdBck.getBeta();
            int Kl = obsl.length;
            double Pl = 0;
            for (int i = 0; i < N; i++) {
                Pl += alphal[i][Kl - 1]; //this is just 1 :(
            }
            for (int k = 0; k < Kl - 2; k++) {
                int xlk = obsl[k];
                int xlkPlusPlus = obsl[k + 1];
                for (int i = 0; i < N; i++) {
                    for (int j = 0; j < N; j++) {
                        newP[i][j] += alphal[i][k] * this.P[i][j] * this.E[j][xlkPlusPlus] * betal[j][k + 1];
                    }
                    double sum = 0;
                    for (int j = 0; j < N; j++) {
                        sum += P[i][j] * E[j][xlkPlusPlus] * betal[j][k+1];
                    }
                    pDenominators[i] += alphal[i][k] * sum;
                    for (int a = 0; a < M; a++) {

                        if (xlk == a) {
                            newE[i][a] += alphal[i][k] * betal[i][k];
                        }
                    }
                    eDenominators[i] += alphal[i][k] * betal[i][k];
                }
            }
            for (int i = 0; i < N; i++) {
                double sum = 0;
                int x2 = obsl[1];
                for (int j = 0; j < N; j++) {
                    sum += P[i][j] * E[j][x2] * betal[j][1];
                }
                newPi[i] = alphal[i][0] * sum / Pl;
                //System.out.println(alphal[i][0] * betal[i][0] / Pl);
                //newPi[i] += alphal[i][0] * betal[i][0] / Pl;
                //newPi[i] += alphal[i][0] * betal[i][0] * fwdBck.calculateAlphaSum(0) / Pl;
                //                                               c1
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
            newPi[i] = newPi[i]; //strong line
        }

        this.P = newP;
        this.E = newE;
        this.pi = newPi;

        //proof that pi is the problem
        /*
        for (int i = 0; i < N; i++) {
            pi[i] = 1.0/N;
        }
        */
        return likelihood;
    }

    private void reestimateBishop(ArrayList<int[]> observables) {
        double[] newPi = new double[N];
        double[][] newP = new double[N][N];
        double[] denominators = new double[N];
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
                int xlkplus = obsl[k+1];
                for (int i = 0; i < N; i++) {
                    for (int j = 0; j < N; j++) {
                        newP[i][j] += alphal[i][k] * P[i][j] * E[j][xlkplus] *
                                betal[j][k+1] / cl[k+1];
                    }
                    //sum_l sum_k alphal[i,k] * betal[i,k]
                    denominators[i] += alphal[i][k] * betal[i][k];
                }
                for (int i = 0; i < N; i++) {
                    for (int a = 0; a < M; a++) {
                        if (xlk == a) {
                            newE[i][a] += alphal[i][k] * betal[i][k];
                            break; //we know that xlk hits exactly one a, so when it is found stop looking.
                        }
                    }
                }
            }
        }

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                newP[i][j] /= denominators[i];
            }
            for (int a = 0; a < M; a++) {
                newE[i][a] /= denominators[i];
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
    private void print_double(double[][] printee){
        DecimalFormat df = new DecimalFormat("#.###");
        for(double[] doubles : printee){
            for(double d : doubles){
                System.out.print(d+ " ");
                //System.out.print(df.format(d) + " ");
            }
            System.out.println();
        }
    }
}
