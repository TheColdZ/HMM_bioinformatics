import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        System.out.println("Trainning by counting, a try... \n");
        FileReader fr = new FileReader();
        String[] genome1 = new String[5];
        genome1[0] = fr.readFile("genome1");
        //genome1[0] = getStringObserved();
        genome1[1] = fr.readFile("genome2");
        genome1[2] = fr.readFile("genome3");
        genome1[3] = fr.readFile("genome4");
        genome1[4] = fr.readFile("genome5");
        String[] trueAnnotation1 = new String[5];
        //trueAnnotation1[0] = getTrueString();
        trueAnnotation1[0] = fr.readFile("trueann1");       //This is a file containing N's, C's and R's
        trueAnnotation1[1] = fr.readFile("trueann2");
        trueAnnotation1[2] = fr.readFile("true-ann3");
        trueAnnotation1[3] = fr.readFile("true-ann4");
        trueAnnotation1[4] = fr.readFile("true-ann5");
        Conversion converter = new DNAConversion31States();      //TODO here we choose model as well.
        ArrayList<int[]> observedConverted = converter.observables(genome1);
        ArrayList<int[]> statesConverted = ((DNAConversion31States) converter).statesFromTrueAnnotationAndObserved(trueAnnotation1,genome1);   //This conversion should give us which states produced the true annotation
        //ArrayList<int[]> statesConverted = converter.states(trueAnnotation1);   //This conversion should give us which states produced the true annotation
        FileWriter fw = new FileWriter();
        fw.writeStatesToFile("Please", Arrays.toString(statesConverted.get(0)));
        CountTraining trainer = new CountTraining(observedConverted,statesConverted,31,4); //TODO We train and choose model
        double[] pi = trainer.getPi();  //We retrieve the newly found parameters
        double[][] E = trainer.getE();
        double[][] P = trainer.getP();
        for (int i = 0; i < 31; i++) {
            for (int j = 0; j < 31; j++) {
                System.out.print(P[i][j]);
                System.out.print(" ");
            }
            System.out.println();
        }
        for (int i = 0; i < 31; i++) {
            for (int j = 0; j < 4; j++) {
                System.out.print(E[i][j]);
                System.out.print(" ");
            }
            System.out.println();
        }

        Viterbi viterbi = new Viterbi(P,E,pi);  //We run viterbi with the new parameters.
        int[] mostLikelySequence = viterbi.calculate(observedConverted.get(0)); //We get the sequence of most likely states to have produced the observed
        fw.writeStatesToFile("MainMethod7states", Arrays.toString(mostLikelySequence));
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

    public static String getStringObserved(){
        return "TATGACTGAAAATGAACAAATTTTTTGGAACAGGGTCTTGGAATTAGCTCAGAGTCAATTAAAACAGGCAACTTATGAATTTTTTGTTCATGATGCCCGTCTATTAAAGGTCGATAAGCATATTGCAACTATTTACTTAGATCAAATGAAAGAGCTCTTTTGGGAAAAAAATCTTAAAGATGTTATTCTTACTGCTGGTTTTGAAGTTTATAACGCTCAAATTTCTGTTGACTATGTTTTCGAAGAAGACCTAATGATTGAGCAAAATCAGACCAAAATCAACCAAAAACCTAAGCAGCAAGCCTTAAATTCTTTGCCTACTGTTACTTCAGATTTAAACTCGAAATATAGTTTTGAAAACTTTATTCAAGGAGATGAAAATCGTTGGGCTGTTGCTGCTTCAATAGCAGTAGCTAATACTCCTGGAACTACCTATAATCCTTTGTTTATTTGGGGTGGCCCTGGGCTTGGAAAAACCCATTTATTAAATGCTATTGGTAATTCTGTACTATTAGAAAATCCAAATGCTCGAATTAAATATATCACAGCTGAAAACTTTATTAATGAGTTTGTTATCCATATTCGCCTTGATACCATGGATGAATTGAAAGAAAAATTTCGTAATTTAGATTTACTCCTTATTGATGATATCCAATCTTTAGCTAAAAAAACGCTCTCTGGAACACAAGAAGAGTTCTTTAATACTTTTAATGCACTTCATAATAATAACAAACAAATTGTCCTAACAAGCGACCGTACACCAGATCATCTCAATGATTTAGAAGATCGATTAGTTACTCGTTTTAAATGGGGATTAACAGTCAATATCACACCTCCTGATTTTGAAACACGAGTGGCTATTTTGACAAATAAAATTCAAGAATATAACTTTATTTTTCCTCAAGATACCATTGAGTATTTGGCTGGTCAATTTGATTCTAATGTCAGAGATTTAGAAGGTGCCTTAAAAGATATTAGTCTGGTTGCTAATTTCAAACAAATTGACACGATTACTGTTGACATTGCTGCCGAAGCTATTCGCGCCAGAAAGCAAGATGGACCTAAAATGACAGTTATTCCCATCGAAGAAATTCAAGCGCAAGTTGGAAAATTTTACGGTGTTACCGTCAAAGAAATTAAAGCTACTAAACGAACACAAAATATTGTTTTAGCAAGACAAGTAGCTATGTTTTTAGCACGTGAAATGACAGATAACAGTCTTCCTAAAATTGGAAAAGAATTTGGTGGCAGAGACCATTCAACAGTACTCCATGCCTATAATAAAATCAAAAACATGATCAGCCAGGACGAAAGCCTTAGGATCGAAATTGAAACCATAAAAAACAAAATTAAATAAC";
    }
    public static String getTrueString(){
        return "NCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCN";
    }
}





