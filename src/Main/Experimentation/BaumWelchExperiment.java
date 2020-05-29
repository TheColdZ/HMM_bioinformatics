package Main.Experimentation;

import Main.Algorithms.BaumWelchTraining;
import Main.Algorithms.Viterbi;
import Main.Conversions.Conversion;
import Main.FileInteraction.FileReader;
import Main.FileInteraction.FileWriter;

import java.util.ArrayList;

/**
 *
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

        for (int i = 0; i < converter.getNumberOfStates(); i++) {
            for (int j = 0; j < converter.getNumberOfStates(); j++) {
                System.out.print(P[i][j]);
                System.out.print(" ");
            }
            System.out.println();
        }
        for (int i = 0; i < converter.getNumberOfStates(); i++) {
            for (int j = 0; j < 4; j++) {
                System.out.print(E[i][j]);
                System.out.print(" ");
            }
            System.out.println();
        }

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
