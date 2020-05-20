package Main.Experimentation;

import Main.Algorithms.CountTraining;
import Main.Algorithms.Viterbi;
import Main.Conversions.Conversion;
import Main.FileInteraction.FileReader;
import Main.FileInteraction.FileWriter;

import java.util.ArrayList;

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
        double[] pi = countTrainer.getPi();  //We retrieve the newly found parameters TODO used for debugging, remove when appropiate
        double[][] E = countTrainer.getE();
        double[][] P = countTrainer.getP();
        /**
         * 5 states pseudo counts:
        P[1][2] = 1;
        P[1][0] = 0;
        P[2][0] = 0;
        P[2][3] = 1;*/

        /**
        //          7 states pseudo counts
        P[0][3] = P[0][3] + P[0][4];
        P[0][4] = 0;
        P[6][4] = P[6][4] + P[6][2];
        P[6][2]= 0;
        */
        /**
        //14 state model pseudo counts
        P[1][2] = 1;
        P[1][0] = 0;
        P[2][3] = 1;
        P[2][0] = 0;
        P[4][5] += P[4][0]/4;       //Transitioned to neutral state 0, now splitted between the three ending states codons.
        P[4][8] += P[4][0]/4;
        P[4][11] += P[4][0]/4;
        P[4][4] += P[4][0]/4;
        P[4][0] = 0;
         */

        /**
        //16 states
        P[1][2] = 1;
        P[1][0] = 0;
        P[2][3] = 1;
        P[2][0] = 0;
        P[4][5] = 1;
        P[4][0] = 0;
        P[5][0] = 0;
        P[5][6] = 1;
        P[6][4] += P[6][0]/4;
        P[6][7] += P[6][0]/4;
        P[6][10] += P[6][0]/4;
        P[6][13] += P[6][0]/4;
        P[6][0] = 0;
        */

        //30 states pseudo counts
        P[1][2] = 1;
        P[1][0] = 0;
        P[2][3] = 1;
        P[2][0] = 0;
        P[4][5] = 1;
        P[4][0] = 0;
        P[5][0] = 0;
        P[5][6] = 1;
        P[6][4] += P[6][0]/4;
        P[6][7] += P[6][0]/4;
        P[6][10] += P[6][0]/4;
        P[6][13] += P[6][0]/4;
        P[6][0] = 0;

        P[9][0]=1;
        P[9][16] = 0;
        P[12][0] = 1;
        P[12][16] = 0;
        P[15][0] = 1;
        P[15][16] = 0;

        P[27][28] += P[27][0]/2;
        P[27][25] += P[27][0]/2;
        P[27][0] = 0;
        P[27][28] += P[27][1]/2;
        P[27][25] += P[27][1]/2;
        P[27][1] = 0;





        for (int i = 0; i < converter.getNumberOfStates(); i++) {
            System.out.print("state :"+i+":");
            for (int j = 0; j < converter.getNumberOfStates(); j++) {
                System.out.print(P[i][j]);
                System.out.print(" ");
            }
            System.out.println();
        }
        System.out.println();
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
        for (int i = 0; i <5 ; i++) {
            int[] mostLikelySequence = viterbi.calculate(genomesForPredictionConverted.get(i)); //We get the sequence of most likely states to have produced the observed
            mostlikelyDecoding.add(mostLikelySequence);             //We convert to an ArrayList<int[]> to get the states translated into what they code, I.e. N,C & R
        }


        String[] convertedStatesFound = converter.states(mostlikelyDecoding);

        FileWriter fw = new FileWriter();
        fw.writePredictedStatesTofile(convertedStatesFound,converter.getNameOfModel());
        System.out.println("Done"); //Todo

    }
}
