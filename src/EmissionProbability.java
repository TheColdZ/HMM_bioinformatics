/**
 * Takes care of calculating emission probabilities
 */
public class EmissionProbability {
    private double[][] emission;

    public EmissionProbability(double[][] emission){
        this.emission = emission;
    }
    public double lookup(int state, char letter){
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
