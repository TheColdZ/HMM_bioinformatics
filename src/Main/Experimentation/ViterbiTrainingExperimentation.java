package Main.Experimentation;

import Main.Algorithms.Viterbi;
import Main.Algorithms.ViterbiTraining;
import Main.Conversions.Conversion;
import Main.FileInteraction.FileReader;
import Main.FileInteraction.FileWriter;

import java.util.ArrayList;

public class ViterbiTrainingExperimentation {
    public void viterbiTrainingCounting(Conversion converter){
        FileReader fr = new FileReader();

        String[] observedGenomes = new String[1];
        observedGenomes[0] = fr.readFile("genome2");                //This is a file containing A's, C's, T's and G's
        //observedGenomes[1] = fr.readFile("genome2");
        //observedGenomes[2] = fr.readFile("genome3");
        //observedGenomes[3] = fr.readFile("genome4");
        //observedGenomes[4] = fr.readFile("genome5");




        ArrayList<int[]> observedConverted = converter.observables(observedGenomes);
        System.out.println("Have read");
        ViterbiTraining vt = new ViterbiTraining(observedConverted, converter.getInitialP(), converter.getInitialE(), converter.getInitialPi());
        System.out.println("Have trained");
        String[] genomesForPrediction = new String[1];
        genomesForPrediction[0] = fr.readFile("genome6");
        //genomesForPrediction[1] = fr.readFile("genome7");
        //genomesForPrediction[2] = fr.readFile("genome8");
        //genomesForPrediction[3] = fr.readFile("genome9");
        //genomesForPrediction[4] = fr.readFile("genome10");
        ArrayList<int[]> genomesForPredictionConverted = converter.observables(genomesForPrediction);

        System.out.println("Have read again");
        Viterbi viterbi = new Viterbi(vt.getP(),vt.getP(),vt.getPi());  //We run viterbi with the new parameters.

        ArrayList<int[]> mostlikelyDecoding = new ArrayList<>();
        for (int i = 0; i <5 ; i++) {
            System.out.println("Is predicting: "+i);
            int[] mostLikelySequence = viterbi.calculate(genomesForPredictionConverted.get(i)); //We get the sequence of most likely states to have produced the observed
            mostlikelyDecoding.add(mostLikelySequence);             //We convert to an ArrayList<int[]> to get the states translated into what they code, I.e. N,C & R
        }

        System.out.println("Is converting");
        String[] convertedStatesFound = converter.states(mostlikelyDecoding);
        System.out.println(("Have converted, starting to write"));
        FileWriter fw = new FileWriter();
        fw.writePredictedStatesTofile(convertedStatesFound,"ViterbiTraining"+converter.getNameOfModel());
        System.out.println("Done"); //Todo

    }
}
