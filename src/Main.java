import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        System.out.println("Trainning by counting, a try... \n We start with a 7 state model to see if this shizzle actually works");
        FileReader fr = new FileReader();
        String[] genome1 = new String[1];
        genome1[0] = fr.readFile("genome1");
        String[] trueAnnotation1 = new String[1];
        trueAnnotation1[0] = fr.readFile("trueann1");
        DNAConversion7States converter = new DNAConversion7States();
        ArrayList<int[]> observedConverted = converter.observables(genome1);
        ArrayList<int[]> statesConverted = converter.states(trueAnnotation1);

        CountTraining trainer = new CountTraining(observedConverted,statesConverted,7,4);
        double[] pi = trainer.getPi();
        double[][] E = trainer.getE();
        double[][] P = trainer.getP();
        Viterbi viterbi = new Viterbi(P,E,pi);
        int[] mostLikelySequence = viterbi.calculate(observedConverted.get(0));
        Comparison comparer = new Comparison();
        ArrayList<int[]> mostlikely = new ArrayList<>();
        mostlikely.add(mostLikelySequence);
        String[] convertedStatesFound = converter.states(mostlikely);
        comparer.compare(trueAnnotation1[0],convertedStatesFound[0],'N','C','R');




    }
}





