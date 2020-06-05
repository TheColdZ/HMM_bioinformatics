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





        Comparison comparer = new Comparison();








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
        TrainingByCountingExperimentation experiment = new TrainingByCountingExperimentation();
        experiment.trainingByCounting(new DNAConversion3States());
        experiment.trainingByCounting(new DNAConversion5States());
        experiment.trainingByCounting(new DNAConversion7States());
        experiment.trainingByCounting(new DNAConversion14States());
        experiment.trainingByCounting(new DNAConversion16States());
        experiment.trainingByCounting(new DNAConversion31States());
        */







        System.out.println("5 state model TBC:");
        comparer.calculate(635406+761716+635802+812551+658368,296651+365916+363773+489062+188421,1006785+1387500+878573+964624+642792,188997+227399+167967+122198+80904);

        System.out.println("5 states viterbi training");
        comparer.calculate(704128+853848+679743+827511+696973,391895+475921+426077+508296+234458,911541+1277495+816269+945390+596755,120275+135267+124026+107238+42299);

        System.out.println("BW training 5 states");
        comparer.calculate(712310+851656+697070+854620+696073,420339+434083+521053+701186+230368,883097+1319333+721293+752500+600845,112093+137459+106699+80129+43199);

        System.out.println("14 state model TBC:");
        comparer.calculate(635809+775916+641943+823465+665969,295406+351851+383239+522213+184656,1008030+1401565+859107+931473+646557,188594+213199+161826+111284+73303);

        System.out.println("14 states viterbi training");
        comparer.calculate(717903+867198+692812+849679+695967,415914+446835+467432+569896+230140,887522+1306581+774914+883790+601073,106500+121917+110957+85070+43305);

        System.out.println("BW training 14 states");
        comparer.calculate(713440+848646+695033+850256+694371,422980+429232+531073+734801+224135,880456+1324184+711273+718885+607078,110963+140469+108736+84493+44901);

        System.out.println("16 state model TBC:");
        comparer.calculate(745512+887200+678386+708156+638462,237546+354950+154981+175245+126982,1065890+1398466+1087365+1278441+704231,78891+101915+125383+226593+100810);

        System.out.println("16 states viterbi training");
        comparer.calculate(761102+903992+705720+796581+665015,610456+722593+483615+608997+282742,692980+1030823+758731+844689+548471,63301+85123+98049+138168+74257);

        System.out.println("BW training 16 states");
        comparer.calculate(769720+894980+746998+887991+671194,868430+976852+886496+1142328+485594,435006+776564+355850+311358+345619,54683+94135+56771+46758+68078);

        System.out.println("3 state model TBC:");
        comparer.calculate(1328750+1424616+1236111+1541919+1017682,653601+721448+742101+831652+415843,50486+214631+27405+4784+52684,95002+381836+40498+10080+84276);

        System.out.println("3 states viterbi training");
        comparer.calculate(1114940+895593+1106669+1253549+719289,563643+422628+773372+1081560+314822,137451+446906+59902+17897+138013,311805+977404+106172+35429+398361);

        System.out.println("BW training 3 states");
        comparer.calculate(4187228,3840210,798601,2049366);


        System.out.println("7 state model TBC:");
        comparer.calculate(1424651+1613995+1273732+1491174+950810,302584+475845+306830+422964+257125,234292+418498+267179+232583+184356,166312+234193+198374+241714+178194);

        System.out.println("7 states viterbi training");
        comparer.calculate(1442042+1583812+1338908+1590503+993009,372538+509841+457657+599107+321564,185759+406431+167718+133069+141950,127500+242447+81832+65756+113962);

        System.out.println("BW training 7 states");
        comparer.calculate(1432369+1594176+1334908+1580436+1003024,399875+539104+474218+616500+332468,180638+395403+161984+130642+134978,114957+213848+75005+60857+100015);


        System.out.println("31 state model TBC:");
        comparer.calculate(1452537+1666431+1268360+1440955+952246,261462+436698+263611+324026+219830,271880+455692+309051+301955+219700,141960+183710+205093+321499+178709);

        System.out.println("31 states viterbi training");
        comparer.calculate(1453815+1626805+1335286+1622341+976726,330120+473612+385553+494720+275996,224962+431394+225327+196304+181704,118942+210720+99949+75070+136059);

        System.out.println("Bw training 31 states");
        comparer.calculate(1455480+1634315+1334259+1618759+983045,334128+481813+390714+497198+277267,224590+428189+223058+196565+179513,113641+198214+98084+75913+130660);

    }


}





