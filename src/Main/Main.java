package Main;

import Main.Experimentation.Comparison;

/**
 *
 * @author Jens Kristian Refsgaard Nielsen & Thomas Damgaard Vinther
 */
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

        /**
        BaumWelchExperiment BWE3 = new BaumWelchExperiment(new DNAConversion3States());
        BaumWelchExperiment BWE5 = new BaumWelchExperiment(new DNAConversion5States());
        BaumWelchExperiment BWE7 = new BaumWelchExperiment(new DNAConversion7States());
        BaumWelchExperiment BWE14 = new BaumWelchExperiment(new DNAConversion14States());
        BaumWelchExperiment BWE16 = new BaumWelchExperiment(new DNAConversion16States());
         BaumWelchExperiment BWE31 = new BaumWelchExperiment(new DNAConversion31States());
         */


        Comparison comprarer = new Comparison();
        System.out.println("BW training all 10 genes, 3 states");
        comprarer.calculate(766491+869324+739957+954331+473599,458590+528995+949615+1295990+277494,224216+377772+125382+62031+167860,67854+966440+231161+76083+651532);

        System.out.println("BW training all 10 genes, 5 states");
        comprarer.calculate(267380+80708+620569+876495+274465,220603+70781+868218+1276130+165766,1082833+1682635+374128+177556+665447,557023+908407+183200+58254+464807);

        System.out.println("BW training all 10 genes, 7 states");
        comprarer.calculate(1361804+1533066+1303001+1511025+1034603,363232+402558+467518+668195+285843,203889+440100+167680+136063+136095,198914+366807+107916+73152+113944);

        System.out.println("BW training all 10 genes, 14 states");
        comprarer.calculate(312289+118942+628111+873323+322223,268787+97289+853666+1250324+179843,1034649+1656127+388680+203362+651370,512114+870173+175658+61426+417049);

        System.out.println("BW training all 10 genes, 16 states");
        comprarer.calculate(636862+785911+639681+756539+602868,227262+246323+414416+714597+133997,1076174+1507093+827930+739089+697216,187541+203204+164088+178210+136404);

        System.out.println("BW training all 10 genes, 31 states");
        comprarer.calculate(1237060+1421454+1208125+1383649+928898,339601+384472+444700+696112+270639,227482+450290+198682+152979+155623,323696+486315+194608+155695+215325);

        /**
        Comparison comparer = new Comparison();
        System.out.println("BW training 3 states");
        comparer.calculate(902216+922202+790866+962542+613617,576459+549021+1033937+1352522+328303,179516+370243+78765+30434+138025,469648+901065+142547+42937+490540);

        System.out.println("BW training 5 states");
        comparer.calculate(715698+862309+696545+853561+694521,430871+448614+519954+675546+236496,872565+1304802+722392+778140+594717,108705+126806+107224+81188+44751);

        System.out.println("BW training 7 states");
        comparer.calculate(1364702+1548576+1310899+1514850+1036560,359881+407070+460967+664451+282821,204497+433095+167496+135200+137469,198759+353790+106753+73934+113635);

        System.out.println("BW training 14 states");
        comparer.calculate(710207+848000+699558+851427+691142,422568+431849+541038+730369+228376,880868+1321567+701308+723317+602837,114196+141115+104211+83322+48130);

        System.out.println("BW training 16 states");
        comparer.calculate(626782+656014+679696+855946+576222,553632+377261+837700+1176850+264869,749804+1376155+404646+276836+566344,197621+333101+124073+78803+163050);

        System.out.println("Bw training 31 states");
        comparer.calculate(1229376+1422435+1204068+1368522+924489,332735+382357+434003+692975+265967,231564+451902+206243+160203+159168,334164+485837+201801+166735+220861);
        */

        //ViterbiTrainingExperimentation viterbiTrainingExperimentation = new ViterbiTrainingExperimentation();

        //viterbiTrainingExperimentation.viterbiTrainingCounting(new DNAConversion5States());

        //viterbiTrainingExperimentation.viterbiTrainingCounting(new DNAConversion3States());
        //viterbiTrainingExperimentation.viterbiTrainingCounting(new DNAConversion7States());
        //viterbiTrainingExperimentation.viterbiTrainingCounting(new DNAConversion14States());
        //viterbiTrainingExperimentation.viterbiTrainingCounting(new DNAConversion16States());
        //viterbiTrainingExperimentation.viterbiTrainingCounting(new DNAConversion31States());

        /**
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
        */

        /**
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
        comparer.calculate(590637+727159+585422+644119+541618,131122+182042+179880+354568+95536,1172314+1571374+1062466+1099118+735677,233766+261956+218347+290630+197654);

        System.out.println("31 state model:");
        comparer.calculate(1112427+1366535+1082194+1229623+830874,226823+312758+256825+426709+192374,305756+499830+326060+295438+215246,482833+563408+381036+436665+331991);
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
        double[][] P = trainer.comparePExample2();
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





