public class Backward {
    private double[][] beta;
    private double[][] emission;
    private double[][] transition;
    private double[] pi;
    private String observed;
    private int N;
    private int T;

    public Backward(String observed, double[] pi, double[][] transition, double[][] emission){
        this.N = transition[0].length;
        this.T = transition.length;
        this.beta = new double[T][N];
        this.pi = pi;
        this.transition = transition;
        this.emission = emission;
        this.observed = observed;
    }

    public double[][] calculateBeta(){

        return beta;
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
