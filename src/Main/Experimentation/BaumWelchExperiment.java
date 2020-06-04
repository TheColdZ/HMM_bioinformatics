package Main.Experimentation;

import Main.Algorithms.BaumWelchTraining;
import Main.Algorithms.Viterbi;
import Main.Conversions.Conversion;
import Main.FileInteraction.FileReader;
import Main.FileInteraction.FileWriter;

import java.util.ArrayList;

/**
 *  This class handles Baum-Welch experimentation. It will read 5 files named genome1, genome2 etc. as the observables. It will then perform Baum-Welch training
 *  on these observables. With the newly found parameters it will predict the most likely states for 5 files named genome6, genome7 etc.
 *  using the Viterbi algorithm and write this prediction to a file called "BaumWelchTraining"+ the name of the used converter.
 * @author Jens Kristian Refsgaard Nielsen & Thomas Damgaard Vinther
 */
public class BaumWelchExperiment {
    public BaumWelchExperiment(Conversion converter) {
        FileReader fr = new FileReader();

        String[] observedGenomes = new String[5];
        observedGenomes[0] = fr.readFile("genome1");                //This is a file containing A's, C's, T's and G's
        observedGenomes[1] = fr.readFile("genome2");
        observedGenomes[2] = fr.readFile("genome3");
        observedGenomes[3] = fr.readFile("genome4");
        observedGenomes[4] = fr.readFile("genome5");
        ArrayList<int[]> observedConverted = converter.observables(observedGenomes);

        BaumWelchTraining baumWelchTraining = new BaumWelchTraining(observedConverted,converter.getInitialP(),converter.getInitialE(),converter.getInitialPi(),0.001);
        double[] pi = baumWelchTraining.getPi();  //We retrieve the newly found parameters
        double[][] E = baumWelchTraining.getE();
        double[][] P = baumWelchTraining.getP();


        String[] genomesForPrediction = new String[5];
        genomesForPrediction[0] = fr.readFile("genome6");
        genomesForPrediction[1] = fr.readFile("genome7");
        genomesForPrediction[2] = fr.readFile("genome8");
        genomesForPrediction[3] = fr.readFile("genome9");
        genomesForPrediction[4] = fr.readFile("genome10");
        ArrayList<int[]> genomesForPredictionConverted = converter.observables(genomesForPrediction);

        Viterbi viterbi = new Viterbi(P,E,pi);  //We run viterbi with the new parameters.

        ArrayList<int[]> mostlikelyDecoding = new ArrayList<>();
        for (int i = 0; i <genomesForPrediction.length ; i++) {
            int[] mostLikelySequence = viterbi.calculate(genomesForPredictionConverted.get(i)); //We get the sequence of most likely states to have produced the observed
            mostlikelyDecoding.add(mostLikelySequence);             //We convert to an ArrayList<int[]> to get the states translated into what they code, I.e. N,C & R
        }
        String[] convertedStatesFound = converter.states(mostlikelyDecoding);
        FileWriter fw = new FileWriter();
        fw.writePredictedStatesTofile(convertedStatesFound,"BaumWelchTraining"+converter.getNameOfModel());
    }
}
