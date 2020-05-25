package Main;

import Main.Experimentation.Comparison;
import Main.Experimentation.ViterbiTrainingExperimentation;

public class Main {
    public static void main(String[] args) {

        //need paramaters for initial ...
        double[][] P = {{0,0,0.9 ,0.1,0   ,0,0},
                {1,0,0   ,0  ,0   ,0,0},
                {0,1,0   ,0  ,0   ,0,0},
                {0,0,0.05,0.9,0.05,0,0},
                {0,0,0   ,0  ,0   ,1,0},
                {0,0,0   ,0  ,0   ,0,1},
                {0,0,0   ,0.1,0.9 ,0,0} };
        double[][] E ={{0.3 ,0.25,0.25,0.2},
                {0.2 ,0.35,0.15,0.3},
                {0.4 ,0.15,0.2 ,0.25},
                {0.25,0.25,0.25,0.25},
                {0.2 ,0.4 ,0.3 ,0.1},
                {0.3 ,0.2 ,0.3 ,0.20},
                {0.15,0.3 ,0.2 ,0.35}};
        double[] pi =  {0,0,0,1,0,0,0};

        //BaumWelchExperiment BWE = new BaumWelchExperiment(new DNAConversion7States(),P,E,pi);


        ViterbiTrainingExperimentation viterbiTrainingExperimentation = new ViterbiTrainingExperimentation();
        /**
        viterbiTrainingExperimentation.viterbiTrainingCounting(new DNAConversion5States());

        viterbiTrainingExperimentation.viterbiTrainingCounting(new DNAConversion3States());
        viterbiTrainingExperimentation.viterbiTrainingCounting(new DNAConversion7States());
        viterbiTrainingExperimentation.viterbiTrainingCounting(new DNAConversion14States());
        viterbiTrainingExperimentation.viterbiTrainingCounting(new DNAConversion16States());
        viterbiTrainingExperimentation.viterbiTrainingCounting(new DNAConversion31States());
         */
        Comparison comparer = new Comparison();
        System.out.println("5 states viterbi training");
        comparer.calculate(703182+852285+680960+834256+693844,399262+477652+431070+519917+234939,904174+1275764+811276+933769+596274,121221+136830+122809+100493+45428);

        System.out.println("3 states viterbi training");
        comparer.calculate(1114940+895593+1106669+1253549+719289,563643+422628+773372+1081560+314822,137451+446906+59902+17897+138013,311805+977404+106172+35429+398361);

        System.out.println("7 states viterbi training");
        comparer.calculate(1220845+1349526+1250029+1508964+917338,269600+302067+348197+550796+210352,266808+506776+246580+189931+194232,370586+584162+201309+138744+248563);

        System.out.println("14 states viterbi training");
        comparer.calculate(718989+867068+686011+848177+695182,407019+448349+457041+560515+231660,896417+1305067+785305+893171+599553,105414+122047+117758+86572+44090);

        System.out.println("16 states viterbi training");
        comparer.calculate(588808+715602+591097+682362+554449,156882+183417+210514+426248+100354,1146554+1569999+1031832+1027438+730859,235595+273513+212672+252387+184823);

        System.out.println("31 states viterbi training");
        comparer.calculate(1156721+1328218+1164177+1371703+868751,286092+321906+357515+606058+225291,258314+492920+254023+189611+190519,426712+599487+270400+221063+285924);

        /*
        TrainingByCountingExperimentation experiment = new TrainingByCountingExperimentation();
        experiment.trainingByCounting(new DNAConversion3States());
        experiment.trainingByCounting(new DNAConversion5States());
        experiment.trainingByCounting(new DNAConversion7States());
        experiment.trainingByCounting(new DNAConversion14States());
        experiment.trainingByCounting(new DNAConversion16States());
        experiment.trainingByCounting(new DNAConversion31States());
        */
        /**
        Comparison comparer = new Comparison();
        System.out.println("3 state model");
        comparer.calculate(1328750+1424616+1236111+1541919+1017682,653601+721448+742101+831652+415843,50486+214631+27405+4784+52684,95002+381836+40498+10080+84276);
        System.out.println("5 state model:");
        comparer.calculate(634360+763890+640297+812437+656494,301910+371670+366838+490715+189071,1001526+1381746+875508+962971+642142,190043+225225+163472+122312+82778);
        System.out.println("7 state model:");
        comparer.calculate(1216120+1428980+1197588+1410260+908129,239549+308058+287505+466818+196146,289970+498111+293357+251796+207235,382200+507382+267665+259561+258975);


        System.out.println("14 state model:");
        comparer.calculate(632580+775249+644812+825762+661417,291637+359890+377490+516739+186274,1011799+1393526+864856+936947+644939,191823+213866+158957+108987+77855);

        System.out.println("16 state model:");
        comparer.calculate(666813+828181+647619+734703+608854,227209+278249+303200+560567+137698,1076227+1475167+939146+893119+693515,157590+160934+156150+200046+130418);

        System.out.println("31 state model:");
        comparer.calculate(1336502+1621505+1231682+1363186+974399,382521+506058+427824+645541+297762,187770+349520+199351+174857+135569,221046+265448+187258+204851+162755);
        */

         /*  TODO delete when appropiate i.e. when experimentation with train by count is satisfacotry
        Main.FileInteraction.FileReader fr = new Main.FileInteraction.FileReader();
        String[] genome1 = new String[5];
        genome1[0] = fr.readFile("genome1");
        //genome1[0] = getStringObserved();
        genome1[1] = fr.readFile("genome2");
        genome1[2] = fr.readFile("genome3");
        genome1[3] = fr.readFile("genome4");
        genome1[4] = fr.readFile("genome5");
        String[] trueAnnotation1 = new String[5];
        //trueAnnotation1[0] = getTrueString();
        trueAnnotation1[0] = fr.readFile("true-ann1");       //This is a file containing N's, C's and R's
        trueAnnotation1[1] = fr.readFile("true-ann2");
        trueAnnotation1[2] = fr.readFile("true-ann3");
        trueAnnotation1[3] = fr.readFile("true-ann4");
        trueAnnotation1[4] = fr.readFile("true-ann5");
        Main.Conversion converter = new Main.Conversions.DNAConversion.DNAConversion31States();      //TODO here we choose model as well.
        ArrayList<int[]> observedConverted = converter.observables(genome1);
        ArrayList<int[]> statesConverted = ((Main.Conversions.DNAConversion.DNAConversion31States) converter).statesFromTrueAnnotationAndObserved(trueAnnotation1,genome1);   //This conversion should give us which states produced the true annotation
        //ArrayList<int[]> statesConverted = converter.states(trueAnnotation1);   //This conversion should give us which states produced the true annotation
        Main.FileInteraction.FileWriter fw = new Main.FileInteraction.FileWriter();
        fw.writeStatesToFile("Please", Arrays.toString(statesConverted.get(0)));
        Main.Algorithms.CountTraining trainer = new Main.Algorithms.CountTraining(observedConverted,statesConverted,31,4); //TODO We train and choose model
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

        String[] genome6prediction = new String[1];
        genome6prediction[0] = fr.readFile("genome6");
        ArrayList<int[]> observedConvertedPredictionGenome6 = converter.observables(genome6prediction);

        Main.Algorithms.Viterbi viterbi = new Main.Algorithms.Viterbi(P,E,pi);  //We run viterbi with the new parameters.
        int[] mostLikelySequence = viterbi.calculate(observedConvertedPredictionGenome6.get(0)); //We get the sequence of most likely states to have produced the observed
        fw.writeStatesToFile("MainMethodgenom6", Arrays.toString(mostLikelySequence));

        ArrayList<int[]> mostlikely = new ArrayList<>();
        mostlikely.add(mostLikelySequence);             //We convert to an ArrayList<int[]> to get the states translated into what they code, I.e. N,C & R
        String[] convertedStatesFound = converter.states(mostlikely);
        fw.writeStatesToFile("convertedGenome6",convertedStatesFound[0]);

        Main.Experimentation.Comparison comparer = new Main.Experimentation.Comparison();                     //We compare the true annotation, with the found annotation. This is kinda cheating...
        //comparer.compare(trueAnnotation1[0],convertedStatesFound[0],'N','C','R');

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

        Main.Algorithms.Viterbi viterbi2 = new Main.Algorithms.Viterbi(transition,emission,start);  //We run viterbi with the old paramters
        //int[] likelySequenceNoTraining = viterbi2.calculate(observedConverted.get(0)); //We get the sequence of most likely states to have produced the observed
        int[] likelySequenceNoTraining = viterbi2.calculate(observedConvertedPredictionGenome6.get(0)); //We get the sequence of most likely states to have produced the observed

        ArrayList<int[]> noTrainingLikely = new ArrayList<>();
        noTrainingLikely.add(likelySequenceNoTraining);             //We convert to an ArrayList<int[]> to get the states translated into what they code, I.e. N,C & R
        Main.Conversion converter7StateModel = new Main.Conversions.DNAConversion.DNAConversion7States();
        String[] convertedStates = converter7StateModel.states(noTrainingLikely);
        fw.writeStatesToFile("convertedGenome6_7model",convertedStates[0]);
        //comparer.compare(trueAnnotation1[0],convertedStates[0],'N','C','R');
        */


    }

    public static String getStringObserved(){
        //TODO delete when appropiate i.e. when experimentation with train by count is satisfacotry
        return "TATGACTGAAAATGAACAAATTTTTTGGAACAGGGTCTTGGAATTAGCTCAGAGTCAATTAAAACAGGCAACTTATGAATTTTTTGTTCATGATGCCCGTCTATTAAAGGTCGATAAGCATATTGCAACTATTTACTTAGATCAAATGAAAGAGCTCTTTTGGGAAAAAAATCTTAAAGATGTTATTCTTACTGCTGGTTTTGAAGTTTATAACGCTCAAATTTCTGTTGACTATGTTTTCGAAGAAGACCTAATGATTGAGCAAAATCAGACCAAAATCAACCAAAAACCTAAGCAGCAAGCCTTAAATTCTTTGCCTACTGTTACTTCAGATTTAAACTCGAAATATAGTTTTGAAAACTTTATTCAAGGAGATGAAAATCGTTGGGCTGTTGCTGCTTCAATAGCAGTAGCTAATACTCCTGGAACTACCTATAATCCTTTGTTTATTTGGGGTGGCCCTGGGCTTGGAAAAACCCATTTATTAAATGCTATTGGTAATTCTGTACTATTAGAAAATCCAAATGCTCGAATTAAATATATCACAGCTGAAAACTTTATTAATGAGTTTGTTATCCATATTCGCCTTGATACCATGGATGAATTGAAAGAAAAATTTCGTAATTTAGATTTACTCCTTATTGATGATATCCAATCTTTAGCTAAAAAAACGCTCTCTGGAACACAAGAAGAGTTCTTTAATACTTTTAATGCACTTCATAATAATAACAAACAAATTGTCCTAACAAGCGACCGTACACCAGATCATCTCAATGATTTAGAAGATCGATTAGTTACTCGTTTTAAATGGGGATTAACAGTCAATATCACACCTCCTGATTTTGAAACACGAGTGGCTATTTTGACAAATAAAATTCAAGAATATAACTTTATTTTTCCTCAAGATACCATTGAGTATTTGGCTGGTCAATTTGATTCTAATGTCAGAGATTTAGAAGGTGCCTTAAAAGATATTAGTCTGGTTGCTAATTTCAAACAAATTGACACGATTACTGTTGACATTGCTGCCGAAGCTATTCGCGCCAGAAAGCAAGATGGACCTAAAATGACAGTTATTCCCATCGAAGAAATTCAAGCGCAAGTTGGAAAATTTTACGGTGTTACCGTCAAAGAAATTAAAGCTACTAAACGAACACAAAATATTGTTTTAGCAAGACAAGTAGCTATGTTTTTAGCACGTGAAATGACAGATAACAGTCTTCCTAAAATTGGAAAAGAATTTGGTGGCAGAGACCATTCAACAGTACTCCATGCCTATAATAAAATCAAAAACATGATCAGCCAGGACGAAAGCCTTAGGATCGAAATTGAAACCATAAAAAACAAAATTAAATAAC";
    }
    public static String getTrueString(){
        //TODO delete when appropiate i.e. when experimentation with train by count is satisfacotry
        return "NCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCN";
    }
}





