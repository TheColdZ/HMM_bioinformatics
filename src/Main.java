import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        System.out.println("Trainning by counting, a try... \n We start with a 7 state model to see if this shizzle actually works");
        FileReader fr = new FileReader();
        String[] genome1 = new String[2];
        genome1[0] = fr.readFile("genome1");
        genome1[1] = fr.readFile("genome2");
        String[] trueAnnotation1 = new String[2];
        trueAnnotation1[0] = fr.readFile("trueann1");       //This is a file containing N's, C's and R's
        trueAnnotation1[1] = fr.readFile("trueann2");
        DNAConversion7States converter = new DNAConversion7States();
        ArrayList<int[]> observedConverted = converter.observables(genome1);
        ArrayList<int[]> statesConverted = converter.states(trueAnnotation1);   //This conversion should give us which states produced the true annotation
        //FileWriter fw = new FileWriter();
        //fw.writeStatesToFile("Please", Arrays.toString(statesConverted.get(0)));
        CountTraining trainer = new CountTraining(observedConverted,statesConverted,7,4); //We train
        double[] pi = trainer.getPi();  //We retrieve the newly found parameters
        double[][] E = trainer.getE();
        double[][] P = trainer.getP();
        Viterbi viterbi = new Viterbi(P,E,pi);  //We run viterbi with the new parameters.
        int[] mostLikelySequence = viterbi.calculate(observedConverted.get(0)); //We get the sequence of most likely states to have produced the observed
        ArrayList<int[]> mostlikely = new ArrayList<>();
        mostlikely.add(mostLikelySequence);             //We convert to an ArrayList<int[]> to get the states translated into what they code, I.e. N,C & R
        String[] convertedStatesFound = converter.states(mostlikely);
        Comparison comparer = new Comparison();                     //We compare the true annotation, with the found annotation. This is kinda cheating...
        comparer.compare(trueAnnotation1[0],convertedStatesFound[0],'N','C','R');

        double[][] transition = {{0,0,0.9 ,0.1,0   ,0,0},
                {1,0,0   ,0  ,0   ,0,0},
                {0,1,0   ,0  ,0   ,0,0},
                {0,0,0.05,0.9,0.05,0,0},
                {0,0,0   ,0  ,0   ,1,0},
                {0,0,0   ,0  ,0   ,0,1},
                {0,0,0   ,0.1,0.9 ,0,0} };

        double[][] emission ={{0.3 ,0.25,0.25,0.2},
                {0.2 ,0.35,0.15,0.3},
                {0.4 ,0.15,0.2 ,0.25},
                {0.25,0.25,0.25,0.25},
                {0.2 ,0.4 ,0.3 ,0.1},
                {0.3 ,0.2 ,0.3 ,0.20},
                {0.15,0.3 ,0.2 ,0.35}};


        double[] start =  {0,0,0,1,0,0,0};

        Viterbi viterbi2 = new Viterbi(transition,emission,start);  //We run viterbi with the old paramters
        int[] likelySequenceNoTraining = viterbi2.calculate(observedConverted.get(0)); //We get the sequence of most likely states to have produced the observed
        ArrayList<int[]> noTrainingLikely = new ArrayList<>();
        noTrainingLikely.add(likelySequenceNoTraining);             //We convert to an ArrayList<int[]> to get the states translated into what they code, I.e. N,C & R
        String[] convertedStates = converter.states(noTrainingLikely);
        comparer.compare(trueAnnotation1[0],convertedStates[0],'N','C','R');



    }
}





