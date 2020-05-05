package Main.Experimentation;

import Main.Algorithms.BaumWelchTraining;
import Main.Conversions.Conversion;
import Main.FileInteraction.FileReader;

import java.util.ArrayList;

public class BaumWelchExperiment {
    public BaumWelchExperiment(Conversion converter, double[][] initialP, double[][] initialE, double[] initialPi) {
        FileReader fr = new FileReader();

        String[] observedGenomes = new String[5];
        observedGenomes[0] = fr.readFile("genome1");                //This is a file containing A's, C's, T's and G's
        observedGenomes[1] = fr.readFile("genome2");
        observedGenomes[2] = fr.readFile("genome3");
        observedGenomes[3] = fr.readFile("genome4");
        observedGenomes[4] = fr.readFile("genome5");

        ArrayList<int[]> observedConverted = converter.observables(observedGenomes);

        BaumWelchTraining baumWelchTraining = new BaumWelchTraining(observedConverted,initialP,initialE,initialPi,0.1);
    }
}
