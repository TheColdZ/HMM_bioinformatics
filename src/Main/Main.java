package Main;

import Main.Experimentation.Comparison;

/**
 * Main, from here we run some experiments.
 * @author Jens Kristian Refsgaard Nielsen & Thomas Damgaard Vinther
 */
public class Main {
    public static void main(String[] args) {


        /*
        BaumWelchExperiment BWE3 = new BaumWelchExperiment(new DNAConversion3States());
        BaumWelchExperiment BWE5 = new BaumWelchExperiment(new DNAConversion5States());
        BaumWelchExperiment BWE7 = new BaumWelchExperiment(new DNAConversion7States());
        BaumWelchExperiment BWE14 = new BaumWelchExperiment(new DNAConversion14States());
        BaumWelchExperiment BWE16 = new BaumWelchExperiment(new DNAConversion16States());
        BaumWelchExperiment BWE31 = new BaumWelchExperiment(new DNAConversion31States());
        */




        /*
        Comparison comparer = new Comparison();
        System.out.println("BW training 3 states");
        comparer.calculate(902216+922202+790866+962542+613617,576459+549021+1033937+1352522+328303,179516+370243+78765+30434+138025,469648+901065+142547+42937+490540);

        System.out.println("BW training 5 states");
        comparer.calculate(715698+862309+696545+853561+694521,430871+448614+519954+675546+236496,872565+1304802+722392+778140+594717,108705+126806+107224+81188+44751);

        System.out.println("BW training 7 states");
        comparer.calculate(1364702+1548576+1310899+1514850+1036560,359881+407070+460967+664451+282821,204497+433095+167496+135200+137469,198759+353790+106753+73934+113635);

        System.out.println("BW training 14 states");
        comparer.calculate(635809+775916+641943+823465+665969,295406+351851+383239+522213+184656,1008030+1401565+859107+931473+646557,188594+213199+161826+111284+73303);

        System.out.println("BW training 16 states");
        comparer.calculate(745512+887200+678386+708156+638462,237546+354950+154981+175245+126982,1065890+1398466+1087365+1278441+704231,78891+101915+125383+226593+100810);

        System.out.println("Bw training 31 states");
        */


        /*
        ViterbiTrainingExperimentation viterbiTrainingExperimentation = new ViterbiTrainingExperimentation();

        viterbiTrainingExperimentation.viterbiTrainingCounting(new DNAConversion5States());

        viterbiTrainingExperimentation.viterbiTrainingCounting(new DNAConversion3States());
        viterbiTrainingExperimentation.viterbiTrainingCounting(new DNAConversion7States());
        viterbiTrainingExperimentation.viterbiTrainingCounting(new DNAConversion14States());
        viterbiTrainingExperimentation.viterbiTrainingCounting(new DNAConversion16States());
        viterbiTrainingExperimentation.viterbiTrainingCounting(new DNAConversion31States());
        */

        /*
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


        /*
        TrainingByCountingExperimentation experiment = new TrainingByCountingExperimentation();
        experiment.trainingByCounting(new DNAConversion3States());
        experiment.trainingByCounting(new DNAConversion5States());
        experiment.trainingByCounting(new DNAConversion7States());
        experiment.trainingByCounting(new DNAConversion14States());
        experiment.trainingByCounting(new DNAConversion16States());
        experiment.trainingByCounting(new DNAConversion31States());
        */


        Comparison comparer = new Comparison();




        System.out.println("5 state model TBC:");
        comparer.calculate(635406+761716+635802+812551+658368,296651+365916+363773+489062+188421,1006785+1387500+878573+964624+642792,188997+227399+167967+122198+80904);




        System.out.println("14 state model TBC:");
        comparer.calculate(635809+775916+641943+823465+665969,295406+351851+383239+522213+184656,1008030+1401565+859107+931473+646557,188594+213199+161826+111284+73303);

        System.out.println("16 state model TBC:");
        comparer.calculate(745512+887200+678386+708156+638462,237546+354950+154981+175245+126982,1065890+1398466+1087365+1278441+704231,78891+101915+125383+226593+100810);

        System.out.println("7 state model TBC:");
        comparer.calculate(1424651+1613995+1273732+1491174+950810,302584+475845+306830+422964+257125,234292+418498+267179+232583+184356,166312+234193+198374+241714+178194);


        System.out.println("3 state model TBC:");
        comparer.calculate(1328750+1424616+1236111+1541919+1017682,653601+721448+742101+831652+415843,50486+214631+27405+4784+52684,95002+381836+40498+10080+84276);

        System.out.println("31 state model TBC:");
        comparer.calculate(1452537+1666431+1268360+1440955+952246,261462+436698+263611+324026+219830,271880+455692+309051+301955+219700,141960+183710+205093+321499+178709);



    }


}





