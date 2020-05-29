package Main.Experimentation;

import Main.Algorithms.CountTraining;
import Main.Algorithms.Viterbi;
import Main.Conversions.Conversion;
import Main.FileInteraction.FileReader;
import Main.FileInteraction.FileWriter;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 *
 * @author Jens Kristian Jensen & Thomas Damgaard Vinther
 */
public class TrainingByCountingExperimentation {

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


        CountTraining countTrainer = new CountTraining(observedConverted,statesConverted,converter.getNumberOfStates(),4);
        double[] pi = countTrainer.getPi();
        double[][] E = countTrainer.getE();
        double[][] P = countTrainer.getP();



        //TODO remove ugly prints
        DecimalFormat df = new DecimalFormat("#.#######");
        //System.out.print("{");    //TODO
        for (int i = 0; i < converter.getNumberOfStates(); i++) {
            System.out.print(pi[i]+",");
        }
        //System.out.println("}");

        for (int i = 0; i < converter.getNumberOfStates(); i++) {
            //System.out.print("{");
            for (int j = 0; j < converter.getNumberOfStates(); j++) {
                System.out.print(P[i][j]+",");
                //System.out.print(df.format(P[i][j]));       //TODO
                System.out.print(" ");
            }
            System.out.println();
        }
        System.out.println();
        for (int i = 0; i < converter.getNumberOfStates(); i++) {
            //System.out.print("{");
            for (int j = 0; j < 4; j++) {
                System.out.print(E[i][j]+",");
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
        for (int i = 0; i <5 ; i++) {
            int[] mostLikelySequence = viterbi.calculate(genomesForPredictionConverted.get(i)); //We get the sequence of most likely states to have produced the observed
            mostlikelyDecoding.add(mostLikelySequence);             //We convert to an ArrayList<int[]> to get the states translated into what they code, I.e. N,C & R
        }


        String[] convertedStatesFound = converter.states(mostlikelyDecoding);

        FileWriter fw = new FileWriter();
        fw.writePredictedStatesTofile(convertedStatesFound,"TrainingByCounting"+converter.getNameOfModel());
        System.out.println("Done"); //Todo

    }
}
