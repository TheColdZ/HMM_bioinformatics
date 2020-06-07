package Main.Experimentation;

import Main.Algorithms.TrainingByCounting;
import Main.Algorithms.Viterbi;
import Main.Conversions.Conversion;
import Main.FileInteraction.FileReader;
import Main.FileInteraction.FileWriter;

import java.util.ArrayList;

/**
 *  This class handles Training By Counting experimentation.
 * @author Jens Kristian Refsgaard Nielsen & Thomas Damgaard Vinther
 */
public class TrainingByCountingExperimentation {

    /**
     * This method will perform Training By Counting.
     * As a prerequisite, one should have the correct filepath in ones filereader, with 5 files named genome1,
     * genome2 etc. as the observables and 5 files named: true-ann1, true-ann2 etc. as the corresponding
     * true annotations of the genomes. It will then perform Training by counting.
     *  With the newly found parameters it will predict the most likely states for 5 files
     *  named genome6, genome7 etc. using the Viterbi algorithm and write this prediction to a file
     *  called "TrainingByCounting"+ the name of the used converter.
     * @param converter Converter to convert true-annotation to corresponding state.
     */
    public void trainingByCounting(Conversion converter){
        FileReader fr = new FileReader();

        String[] observedGenomes = new String[5];
        observedGenomes[0] = fr.readFile("genome1");                //This is a file containing A's, C's, T's and G's
        observedGenomes[1] = fr.readFile("genome2");
        observedGenomes[2] = fr.readFile("genome3");
        observedGenomes[3] = fr.readFile("genome4");
        observedGenomes[4] = fr.readFile("genome5");

        String[] trueAnnotationOfGenomes = new String[5];
        trueAnnotationOfGenomes[0] = fr.readFile("true-ann1");       //This is a file containing N's, C's and R's
        trueAnnotationOfGenomes[1] = fr.readFile("true-ann2");
        trueAnnotationOfGenomes[2] = fr.readFile("true-ann3");
        trueAnnotationOfGenomes[3] = fr.readFile("true-ann4");
        trueAnnotationOfGenomes[4] = fr.readFile("true-ann5");

        ArrayList<int[]> observedConverted = converter.observables(observedGenomes);
        ArrayList<int[]> statesConverted =  converter.states(trueAnnotationOfGenomes,observedGenomes);   //This conversion should give us which states produced the true annotation


        TrainingByCounting countTrainer = new TrainingByCounting(observedConverted,statesConverted,converter.getNumberOfStates(),4);
        double[] pi = countTrainer.getPi();
        double[][] E = countTrainer.getE();
        double[][] P = countTrainer.getP();


        String[] genomesForPrediction = new String[5];
        genomesForPrediction[0] = fr.readFile("genome6");
        genomesForPrediction[1] = fr.readFile("genome7");
        genomesForPrediction[2] = fr.readFile("genome8");
        genomesForPrediction[3] = fr.readFile("genome9");
        genomesForPrediction[4] = fr.readFile("genome10");
        ArrayList<int[]> genomesForPredictionConverted = converter.observables(genomesForPrediction);

        Viterbi viterbi = new Viterbi(P,E,pi);  //We run viterbi with the new parameters.


        ArrayList<int[]> mostlikelyDecoding = new ArrayList<>();
        for (int i = 0; i <5 ; i++) {
            int[] mostLikelySequence = viterbi.calculate(genomesForPredictionConverted.get(i)); //We get the sequence of most likely states to have produced the observed
            mostlikelyDecoding.add(mostLikelySequence);             //We convert to an ArrayList<int[]> to get the states translated into what they code, I.e. N,C & R
        }

        String[] convertedStatesFound = converter.states(mostlikelyDecoding);

        FileWriter fw = new FileWriter();
        fw.writePredictedStatesTofile(convertedStatesFound,"TrainingByCounting"+converter.getNameOfModel());

    }
}
