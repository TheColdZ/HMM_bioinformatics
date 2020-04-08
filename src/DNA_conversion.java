import java.util.ArrayList;

public class DNA_conversion implements Conversion{

    private ArrayList<int[]> observables;
    private ArrayList<int[]> states;

    public DNA_conversion(String[] observables, String[] states){
        int L = observables.length;
        int K = observables[0].length();
        this.observables = new ArrayList<>();
        this.states = new ArrayList<>();
        for (int i = 0; i < L; i++) {
            this.observables.add(new int[K]);
            this.states.add(new int[K]);
        }
        for (int l = 0; l < L; l++) {
            String obs = observables[l];
            String state = states[l];
            for (int k = 0; k < K; k++) {
                this.observables.get(l)[k] = emission_conversion(obs.charAt(k));
                this.states.get(l)[k] = state_conversion(state.charAt(k));
            }
        }
    }
    public DNA_conversion(String[] observables){
        int L = observables.length;
        int K = observables[0].length();
        this.observables = new ArrayList<>();
        this.states = new ArrayList<>();
        for (int i = 0; i < L; i++) {
            this.observables.add(new int[K]);
        }
        for (int l = 0; l < L; l++) {
            String obs = observables[l];
            for (int k = 0; k < K; k++) {
                this.observables.get(l)[k] = emission_conversion(obs.charAt(k));
            }
        }
    }

    private int emission_conversion(Character c){
        int res = -1;
        switch(c){
            case 'A': res = 0;
                break;
            case 'C': res = 1;
                break;
            case 'G': res = 2;
                break;
            case 'T': res = 3;
                break;
            default:
        }
        return res;
    }
    private int state_conversion(Character c){
        int res = -1;
        switch(c){
            case 'C': res = 0;
                break;
            case 'N': res = 1;
                break;
            case 'R': res = 2;
                break;
            default:
        }
        return res;
    }

    @Override
    public ArrayList<int[]> getObservables() {
        return observables;
    }

    @Override
    public ArrayList<int[]> getStates() {
        return states;
    }
}
