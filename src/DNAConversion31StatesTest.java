import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class DNAConversion31StatesTest {
    private DNAConversion31States converter;

    @Before
    public void setUp() {
        converter = new DNAConversion31States();
    }

    @Test
    public void printAndSeeState22_23_24(){
        String[] genome1 = new String[1];
        genome1[0] = getStringStart_22_23_24();

        String[] trueAnnotation1 = new String[1];
        trueAnnotation1[0] = getTrueStringStartNRRR();//This is a file containing N's, C's and R's

        converter = new DNAConversion31States();
        ArrayList<int[]> observedConverted = converter.observables(genome1);
        ArrayList<int[]> statesConverted = converter.statesFromTrueAnnotationAndObserved(trueAnnotation1,genome1);   //This conversion should give us which states produced the true annotation
        //ArrayList<int[]> statesConverted = converter.states(trueAnnotation1);   //This conversion should give us which states produced the true annotation
        CountTraining trainer = new CountTraining(observedConverted,statesConverted,31,4); //TODO We train and choose model
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

    }
    @Test
    public void printAndSeeState16_17_18(){
        String[] genome1 = new String[1];
        genome1[0] = getStringStart_16_17_18();

        String[] trueAnnotation1 = new String[1];
        trueAnnotation1[0] = getTrueStringStartNRRR();//This is a file containing N's, C's and R's

        converter = new DNAConversion31States();      //TODO here we choose model as well.
        ArrayList<int[]> observedConverted = converter.observables(genome1);
        ArrayList<int[]> statesConverted = converter.statesFromTrueAnnotationAndObserved(trueAnnotation1,genome1);   //This conversion should give us which states produced the true annotation
        //ArrayList<int[]> statesConverted = converter.states(trueAnnotation1);   //This conversion should give us which states produced the true annotation
        CountTraining trainer = new CountTraining(observedConverted,statesConverted,31,4); //TODO We train and choose model
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
    }
    @Test
    public void printAndSeeState19_20_21(){
        String[] genome1 = new String[1];
        genome1[0] = getStringStart0_19_20_21();

        String[] trueAnnotation1 = new String[1];
        trueAnnotation1[0] = getTrueStringStartNRRR();//This is a file containing N's, C's and R's

        converter = new DNAConversion31States();     //TODO here we choose model as well.
        ArrayList<int[]> observedConverted = converter.observables(genome1);
        ArrayList<int[]> statesConverted = converter.statesFromTrueAnnotationAndObserved(trueAnnotation1,genome1);   //This conversion should give us which states produced the true annotation
        //ArrayList<int[]> statesConverted = converter.states(trueAnnotation1);   //This conversion should give us which states produced the true annotation
        CountTraining trainer = new CountTraining(observedConverted,statesConverted,31,4); //TODO We train and choose model
        double[] pi = trainer.getPi();  //We retrieve the newly found parameters
        double[][] E = trainer.getE();
        double[][] P = trainer.getP();
        System.out.println("Only state 13, 14 & 15 will be non NAN, from the last outputting states");
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

    }

    @Test
    public void testStates22_23_24(){
        String[] genome1 = new String[1];
        genome1[0] = getStringStart_22_23_24();

        String[] trueAnnotation1 = new String[1];
        trueAnnotation1[0] = getTrueStringStartNRRR();//This is a file containing N's, C's and R's

        converter = new DNAConversion31States();     //TODO here we choose model as well.

        ArrayList<int[]> statesConverted = converter.statesFromTrueAnnotationAndObserved(trueAnnotation1,genome1);   //This conversion should give us which states produced the true annotation

        assert(statesConverted.get(0).length == trueAnnotation1[0].length());
        int length = statesConverted.get(0).length;
        assert(statesConverted.get(0)[0]==0);
        assert(statesConverted.get(0)[1]==22);
        assert(statesConverted.get(0)[2]==23);
        assert(statesConverted.get(0)[3]==24);

        assert(statesConverted.get(0)[length-1]==0);
        assert(statesConverted.get(0)[length-2]==30);
        assert(statesConverted.get(0)[length-3]==29);
        assert(statesConverted.get(0)[length-4]==28);
    }

    @Test
    public void testStates16_17_18(){
        String[] genome1 = new String[1];
        genome1[0] = getStringStart_16_17_18();

        String[] trueAnnotation1 = new String[1];
        trueAnnotation1[0] = getTrueStringStartNRRR();//This is a file containing N's, C's and R's

        converter = new DNAConversion31States();    //TODO here we choose model as well.

        ArrayList<int[]> statesConverted = converter.statesFromTrueAnnotationAndObserved(trueAnnotation1,genome1);   //This conversion should give us which states produced the true annotation

        assert(statesConverted.get(0).length == trueAnnotation1[0].length());
        int length = statesConverted.get(0).length;
        assert(statesConverted.get(0)[0]==0);
        assert(statesConverted.get(0)[1]==16);
        assert(statesConverted.get(0)[2]==17);
        assert(statesConverted.get(0)[3]==18);

        assert(statesConverted.get(0)[length-1]==0);
        assert(statesConverted.get(0)[length-2]==30);
        assert(statesConverted.get(0)[length-3]==29);
        assert(statesConverted.get(0)[length-4]==28);

    }

    @Test
    public void testStates19_20_21(){
        String[] genome1 = new String[1];
        genome1[0] = getStringStart0_19_20_21();

        String[] trueAnnotation1 = new String[1];
        trueAnnotation1[0] = getTrueStringStartNRRR();//This is a file containing N's, C's and R's

        converter = new DNAConversion31States();      //TODO here we choose model as well.

        ArrayList<int[]> statesConverted = converter.statesFromTrueAnnotationAndObserved(trueAnnotation1,genome1);   //This conversion should give us which states produced the true annotation

        assert(statesConverted.get(0).length == trueAnnotation1[0].length());
        int length = statesConverted.get(0).length;
        assert(statesConverted.get(0)[0]==0);
        assert(statesConverted.get(0)[1]==19);
        assert(statesConverted.get(0)[2]==20);
        assert(statesConverted.get(0)[3]==21);

        assert(statesConverted.get(0)[length-1]==0);
        assert(statesConverted.get(0)[length-2]==30);
        assert(statesConverted.get(0)[length-3]==29);
        assert(statesConverted.get(0)[length-4]==28);
    }

    @Test
    public void testNothingWrongInMiddle(){
        String[] genome1 = new String[1];
        genome1[0] = getStringStart0_19_20_21();

        String[] trueAnnotation1 = new String[1];
        trueAnnotation1[0] = getTrueStringStartNRRR();//This is a file containing N's, C's and R's

        converter = new DNAConversion31States();      //TODO here we choose model as well.

        ArrayList<int[]> statesConverted = converter.statesFromTrueAnnotationAndObserved(trueAnnotation1,genome1);   //This conversion should give us which states produced the true annotation

        assert(statesConverted.get(0).length == trueAnnotation1[0].length());
        int length = statesConverted.get(0).length;

        for (int i = 0; i < length-4; i++) {
            assert(! (statesConverted.get(0)[i]==30));     //assert false doesn't work?
            assert(! (statesConverted.get(0)[i]==29));
            assert(! (statesConverted.get(0)[i]==28));
        }
    }
    @Test
    public void testStates789(){
        String[] genome1 = new String[1];
        genome1[0] = getStringEnd7890();

        String[] trueAnnotation1 = new String[1];
        trueAnnotation1[0] = getTrueStringEndCCCN();//This is a file containing N's, C's and R's

        converter = new DNAConversion31States();      //TODO here we choose model as well.

        ArrayList<int[]> statesConverted = converter.statesFromTrueAnnotationAndObserved(trueAnnotation1,genome1);   //This conversion should give us which states produced the true annotation

        assert(statesConverted.get(0).length == trueAnnotation1[0].length());
        int length = statesConverted.get(0).length;
        assert(statesConverted.get(0)[length-1]==0);
        assert(statesConverted.get(0)[length-2]==9);
        assert(statesConverted.get(0)[length-3]==8);
        assert(statesConverted.get(0)[length-4]==7);

        assert(statesConverted.get(0)[0]==0);
        assert(statesConverted.get(0)[1]==1);
        assert(statesConverted.get(0)[2]==2);
        assert(statesConverted.get(0)[3]==3);
        for (int i = 0; i < length-4; i++) {
            assert(! (statesConverted.get(0)[i]==7));     //assert false doesn't work?
            assert(! (statesConverted.get(0)[i]==8));
            assert(! (statesConverted.get(0)[i]==9));
            assert(! (statesConverted.get(0)[i]==10));
            assert(! (statesConverted.get(0)[i]==11));
            assert(! (statesConverted.get(0)[i]==12));
            assert(! (statesConverted.get(0)[i]==13));
            assert(! (statesConverted.get(0)[i]==14));
            assert(! (statesConverted.get(0)[i]==15));
        }

    }
    @Test
    public void PrintAllPossible(){
        String[] genome1 = new String[1];
        genome1[0] = getStringStart_22_23_24()+getStringStart0_19_20_21()+getStringStart_16_17_18()+getStringEnd7890()+getStringEnd1011120()+getStringEnd1314150();

        String[] trueAnnotation1 = new String[1];
        trueAnnotation1[0] = getTrueStringStartNRRR()+getTrueStringStartNRRR()+getTrueStringStartNRRR()+getTrueStringEndCCCN()+getTrueStringEndCCCN()+getTrueStringEndCCCN();//This is a file containing N's, C's and R's

        converter = new DNAConversion31States();
        ArrayList<int[]> observedConverted = converter.observables(genome1);
        ArrayList<int[]> statesConverted = converter.statesFromTrueAnnotationAndObserved(trueAnnotation1,genome1);   //This conversion should give us which states produced the true annotation
        //ArrayList<int[]> statesConverted = converter.states(trueAnnotation1);   //This conversion should give us which states produced the true annotation
        CountTraining trainer = new CountTraining(observedConverted,statesConverted,31,4); //TODO We train and choose model
        double[] pi = trainer.getPi();  //We retrieve the newly found parameters
        double[][] E = trainer.getE();
        double[][] P = trainer.getP();
        for (int i = 0; i < 31; i++) {
            System.out.print("state"+i+": ");
            for (int j = 0; j < 31; j++) {
                System.out.print(P[i][j]);
                System.out.print(" ");
            }
            System.out.println();
        }
        for (int i = 0; i < 31; i++) {
            System.out.print("state"+i+": ");
            for (int j = 0; j < 4; j++) {
                System.out.print(E[i][j]);
                System.out.print(" ");
            }
            System.out.println();
        }

    }


    private String getStringStart_22_23_24(){
        return "ATCATAATTTGTTAGCTTTATCAGCATTTACCCATGTTCCACGAGATCCGATTTGAACATAAATACCATTACTTGGTTGATCAATTTTTAATACTTTATAACTACCAGTGACGATAAAATATTCACCCACACGGAGAATTTGATCTCCTAAAACTTTTCCTTGGTTATCTGTTTCATCTAATGGTGTGGGATCAATCCAGTTTAGTGGAGTTGGGTCTCCTCCGGCTAATTCTTTATTAACGATCAAATTATTAACAAGCTGATCTACACGAAAAACGCCAGGGAAGGTAACAGTATCACCGACCTTAATGGAAGGTTTTGTATTGTCATTTTGAACCACAGACTGTGCGGGCTCTTTAATGGGAATATAGCGTCGGTTTCCAGAAAAACTGAGGTAGCTAAGCCATGTATAACCTCCAGCAGTCACGACTTTATCGTAATAAACAGATTGACCTGCTTCATAGTAAGCAAGATCAGGGCTATCGATACTGGTTTGTCCTTTGACTGGTAATCTGGTTGTAAAGTGATAAGTTCCAGAGGGGTCAAATGAAGCTTGAGAAATGTGTTTTAGTTGTCTTGGGTAGGAATGACTTGTCTGAGATGATAAGTCTTTAAAATGGATATAACCACTTACCTGAGATTTTGGAATTTGACGCTTATGGTATCTTTCAGGGCCTTGTCCAGCGTTGTAATTATACTCTTCGATAGTGACAGTGTCTCCACGGATATCAGCTACCCATGCTACATGACCGTAAGCAGCATTTGACTGATAAGCGTTTTTATCAAACCAAGCGATAGCCCCTATGCTTGGTGTCTTATTCACAGGATAACCCTGATTTTTCGCGATATGTCCCCACGTGCAGGCATTACCGTAGCCTTTAGGTAACTGAAAACCATTAGCAGAGCTTAAACGAAAAGCTGCAAAAGAAGTGCATTGGCGGATATACATGTTCCACGAATCGATTCCATTGCCTTTTTTCCACTTACTCGGATAGTTATCGCCTAAAACAGCTGCATGAACAAGATTTTCCTGTTGCGAAATAAGTGTAGATGGCATAGTAGGTACTAGACCATTAAAACCTAAAAGGATTACTCCTGAGACCAAAAAACGATGAAATTTTTTCATT";
    }
    private String getStringStart_16_17_18(){
        return "ATTATAATTTGTTAGCTTTATCAGCATTTACCCATGTTCCACGAGATCCGATTTGAACATAAATACCATTACTTGGTTGATCAATTTTTAATACTTTATAACTACCAGTGACGATAAAATATTCACCCACACGGAGAATTTGATCTCCTAAAACTTTTCCTTGGTTATCTGTTTCATCTAATGGTGTGGGATCAATCCAGTTTAGTGGAGTTGGGTCTCCTCCGGCTAATTCTTTATTAACGATCAAATTATTAACAAGCTGATCTACACGAAAAACGCCAGGGAAGGTAACAGTATCACCGACCTTAATGGAAGGTTTTGTATTGTCATTTTGAACCACAGACTGTGCGGGCTCTTTAATGGGAATATAGCGTCGGTTTCCAGAAAAACTGAGGTAGCTAAGCCATGTATAACCTCCAGCAGTCACGACTTTATCGTAATAAACAGATTGACCTGCTTCATAGTAAGCAAGATCAGGGCTATCGATACTGGTTTGTCCTTTGACTGGTAATCTGGTTGTAAAGTGATAAGTTCCAGAGGGGTCAAATGAAGCTTGAGAAATGTGTTTTAGTTGTCTTGGGTAGGAATGACTTGTCTGAGATGATAAGTCTTTAAAATGGATATAACCACTTACCTGAGATTTTGGAATTTGACGCTTATGGTATCTTTCAGGGCCTTGTCCAGCGTTGTAATTATACTCTTCGATAGTGACAGTGTCTCCACGGATATCAGCTACCCATGCTACATGACCGTAAGCAGCATTTGACTGATAAGCGTTTTTATCAAACCAAGCGATAGCCCCTATGCTTGGTGTCTTATTCACAGGATAACCCTGATTTTTCGCGATATGTCCCCACGTGCAGGCATTACCGTAGCCTTTAGGTAACTGAAAACCATTAGCAGAGCTTAAACGAAAAGCTGCAAAAGAAGTGCATTGGCGGATATACATGTTCCACGAATCGATTCCATTGCCTTTTTTCCACTTACTCGGATAGTTATCGCCTAAAACAGCTGCATGAACAAGATTTTCCTGTTGCGAAATAAGTGTAGATGGCATAGTAGGTACTAGACCATTAAAACCTAAAAGGATTACTCCTGAGACCAAAAAACGATGAAATTTTTTCATT";
    }
    private String getStringStart0_19_20_21(){
        return "ACTATAATTTGTTAGCTTTATCAGCATTTACCCATGTTCCACGAGATCCGATTTGAACATAAATACCATTACTTGGTTGATCAATTTTTAATACTTTATAACTACCAGTGACGATAAAATATTCACCCACACGGAGAATTTGATCTCCTAAAACTTTTCCTTGGTTATCTGTTTCATCTAATGGTGTGGGATCAATCCAGTTTAGTGGAGTTGGGTCTCCTCCGGCTAATTCTTTATTAACGATCAAATTATTAACAAGCTGATCTACACGAAAAACGCCAGGGAAGGTAACAGTATCACCGACCTTAATGGAAGGTTTTGTATTGTCATTTTGAACCACAGACTGTGCGGGCTCTTTAATGGGAATATAGCGTCGGTTTCCAGAAAAACTGAGGTAGCTAAGCCATGTATAACCTCCAGCAGTCACGACTTTATCGTAATAAACAGATTGACCTGCTTCATAGTAAGCAAGATCAGGGCTATCGATACTGGTTTGTCCTTTGACTGGTAATCTGGTTGTAAAGTGATAAGTTCCAGAGGGGTCAAATGAAGCTTGAGAAATGTGTTTTAGTTGTCTTGGGTAGGAATGACTTGTCTGAGATGATAAGTCTTTAAAATGGATATAACCACTTACCTGAGATTTTGGAATTTGACGCTTATGGTATCTTTCAGGGCCTTGTCCAGCGTTGTAATTATACTCTTCGATAGTGACAGTGTCTCCACGGATATCAGCTACCCATGCTACATGACCGTAAGCAGCATTTGACTGATAAGCGTTTTTATCAAACCAAGCGATAGCCCCTATGCTTGGTGTCTTATTCACAGGATAACCCTGATTTTTCGCGATATGTCCCCACGTGCAGGCATTACCGTAGCCTTTAGGTAACTGAAAACCATTAGCAGAGCTTAAACGAAAAGCTGCAAAAGAAGTGCATTGGCGGATATACATGTTCCACGAATCGATTCCATTGCCTTTTTTCCACTTACTCGGATAGTTATCGCCTAAAACAGCTGCATGAACAAGATTTTCCTGTTGCGAAATAAGTGTAGATGGCATAGTAGGTACTAGACCATTAAAACCTAAAAGGATTACTCCTGAGACCAAAAAACGATGAAATTTTTTCATT";
    }
    private String getTrueStringStartNRRR(){
        return "NRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRN";
    }

    private String getStringEnd7890(){
        return "TATGACTGAAAATGAACAAATTTTTTGGAACAGGGTCTTGGAATTAGCTCAGAGTCAATTAAAACAGGCAACTTATGAATTTTTTGTTCATGATGCCCGTCTATTAAAGGTCGATAAGCATATTGCAACTATTTACTTAGATCAAATGAAAGAGCTCTTTTGGGAAAAAAATCTTAAAGATGTTATTCTTACTGCTGGTTTTGAAGTTTATAACGCTCAAATTTCTGTTGACTATGTTTTCGAAGAAGACCTAATGATTGAGCAAAATCAGACCAAAATCAACCAAAAACCTAAGCAGCAAGCCTTAAATTCTTTGCCTACTGTTACTTCAGATTTAAACTCGAAATATAGTTTTGAAAACTTTATTCAAGGAGATGAAAATCGTTGGGCTGTTGCTGCTTCAATAGCAGTAGCTAATACTCCTGGAACTACCTATAATCCTTTGTTTATTTGGGGTGGCCCTGGGCTTGGAAAAACCCATTTATTAAATGCTATTGGTAATTCTGTACTATTAGAAAATCCAAATGCTCGAATTAAATATATCACAGCTGAAAACTTTATTAATGAGTTTGTTATCCATATTCGCCTTGATACCATGGATGAATTGAAAGAAAAATTTCGTAATTTAGATTTACTCCTTATTGATGATATCCAATCTTTAGCTAAAAAAACGCTCTCTGGAACACAAGAAGAGTTCTTTAATACTTTTAATGCACTTCATAATAATAACAAACAAATTGTCCTAACAAGCGACCGTACACCAGATCATCTCAATGATTTAGAAGATCGATTAGTTACTCGTTTTAAATGGGGATTAACAGTCAATATCACACCTCCTGATTTTGAAACACGAGTGGCTATTTTGACAAATAAAATTCAAGAATATAACTTTATTTTTCCTCAAGATACCATTGAGTATTTGGCTGGTCAATTTGATTCTAATGTCAGAGATTTAGAAGGTGCCTTAAAAGATATTAGTCTGGTTGCTAATTTCAAACAAATTGACACGATTACTGTTGACATTGCTGCCGAAGCTATTCGCGCCAGAAAGCAAGATGGACCTAAAATGACAGTTATTCCCATCGAAGAAATTCAAGCGCAAGTTGGAAAATTTTACGGTGTTACCGTCAAAGAAATTAAAGCTACTAAACGAACACAAAATATTGTTTTAGCAAGACAAGTAGCTATGTTTTTAGCACGTGAAATGACAGATAACAGTCTTCCTAAAATTGGAAAAGAATTTGGTGGCAGAGACCATTCAACAGTACTCCATGCCTATAATAAAATCAAAAACATGATCAGCCAGGACGAAAGCCTTAGGATCGAAATTGAAACCATAAAAAACAAAATTAAATAAC";
    }
    private String getStringEnd1011120(){
        return "TATGACTGAAAATGAACAAATTTTTTGGAACAGGGTCTTGGAATTAGCTCAGAGTCAATTAAAACAGGCAACTTATGAATTTTTTGTTCATGATGCCCGTCTATTAAAGGTCGATAAGCATATTGCAACTATTTACTTAGATCAAATGAAAGAGCTCTTTTGGGAAAAAAATCTTAAAGATGTTATTCTTACTGCTGGTTTTGAAGTTTATAACGCTCAAATTTCTGTTGACTATGTTTTCGAAGAAGACCTAATGATTGAGCAAAATCAGACCAAAATCAACCAAAAACCTAAGCAGCAAGCCTTAAATTCTTTGCCTACTGTTACTTCAGATTTAAACTCGAAATATAGTTTTGAAAACTTTATTCAAGGAGATGAAAATCGTTGGGCTGTTGCTGCTTCAATAGCAGTAGCTAATACTCCTGGAACTACCTATAATCCTTTGTTTATTTGGGGTGGCCCTGGGCTTGGAAAAACCCATTTATTAAATGCTATTGGTAATTCTGTACTATTAGAAAATCCAAATGCTCGAATTAAATATATCACAGCTGAAAACTTTATTAATGAGTTTGTTATCCATATTCGCCTTGATACCATGGATGAATTGAAAGAAAAATTTCGTAATTTAGATTTACTCCTTATTGATGATATCCAATCTTTAGCTAAAAAAACGCTCTCTGGAACACAAGAAGAGTTCTTTAATACTTTTAATGCACTTCATAATAATAACAAACAAATTGTCCTAACAAGCGACCGTACACCAGATCATCTCAATGATTTAGAAGATCGATTAGTTACTCGTTTTAAATGGGGATTAACAGTCAATATCACACCTCCTGATTTTGAAACACGAGTGGCTATTTTGACAAATAAAATTCAAGAATATAACTTTATTTTTCCTCAAGATACCATTGAGTATTTGGCTGGTCAATTTGATTCTAATGTCAGAGATTTAGAAGGTGCCTTAAAAGATATTAGTCTGGTTGCTAATTTCAAACAAATTGACACGATTACTGTTGACATTGCTGCCGAAGCTATTCGCGCCAGAAAGCAAGATGGACCTAAAATGACAGTTATTCCCATCGAAGAAATTCAAGCGCAAGTTGGAAAATTTTACGGTGTTACCGTCAAAGAAATTAAAGCTACTAAACGAACACAAAATATTGTTTTAGCAAGACAAGTAGCTATGTTTTTAGCACGTGAAATGACAGATAACAGTCTTCCTAAAATTGGAAAAGAATTTGGTGGCAGAGACCATTCAACAGTACTCCATGCCTATAATAAAATCAAAAACATGATCAGCCAGGACGAAAGCCTTAGGATCGAAATTGAAACCATAAAAAACAAAATTAAATAGC";
    }
    private String getStringEnd1314150(){
        return "TATGACTGAAAATGAACAAATTTTTTGGAACAGGGTCTTGGAATTAGCTCAGAGTCAATTAAAACAGGCAACTTATGAATTTTTTGTTCATGATGCCCGTCTATTAAAGGTCGATAAGCATATTGCAACTATTTACTTAGATCAAATGAAAGAGCTCTTTTGGGAAAAAAATCTTAAAGATGTTATTCTTACTGCTGGTTTTGAAGTTTATAACGCTCAAATTTCTGTTGACTATGTTTTCGAAGAAGACCTAATGATTGAGCAAAATCAGACCAAAATCAACCAAAAACCTAAGCAGCAAGCCTTAAATTCTTTGCCTACTGTTACTTCAGATTTAAACTCGAAATATAGTTTTGAAAACTTTATTCAAGGAGATGAAAATCGTTGGGCTGTTGCTGCTTCAATAGCAGTAGCTAATACTCCTGGAACTACCTATAATCCTTTGTTTATTTGGGGTGGCCCTGGGCTTGGAAAAACCCATTTATTAAATGCTATTGGTAATTCTGTACTATTAGAAAATCCAAATGCTCGAATTAAATATATCACAGCTGAAAACTTTATTAATGAGTTTGTTATCCATATTCGCCTTGATACCATGGATGAATTGAAAGAAAAATTTCGTAATTTAGATTTACTCCTTATTGATGATATCCAATCTTTAGCTAAAAAAACGCTCTCTGGAACACAAGAAGAGTTCTTTAATACTTTTAATGCACTTCATAATAATAACAAACAAATTGTCCTAACAAGCGACCGTACACCAGATCATCTCAATGATTTAGAAGATCGATTAGTTACTCGTTTTAAATGGGGATTAACAGTCAATATCACACCTCCTGATTTTGAAACACGAGTGGCTATTTTGACAAATAAAATTCAAGAATATAACTTTATTTTTCCTCAAGATACCATTGAGTATTTGGCTGGTCAATTTGATTCTAATGTCAGAGATTTAGAAGGTGCCTTAAAAGATATTAGTCTGGTTGCTAATTTCAAACAAATTGACACGATTACTGTTGACATTGCTGCCGAAGCTATTCGCGCCAGAAAGCAAGATGGACCTAAAATGACAGTTATTCCCATCGAAGAAATTCAAGCGCAAGTTGGAAAATTTTACGGTGTTACCGTCAAAGAAATTAAAGCTACTAAACGAACACAAAATATTGTTTTAGCAAGACAAGTAGCTATGTTTTTAGCACGTGAAATGACAGATAACAGTCTTCCTAAAATTGGAAAAGAATTTGGTGGCAGAGACCATTCAACAGTACTCCATGCCTATAATAAAATCAAAAACATGATCAGCCAGGACGAAAGCCTTAGGATCGAAATTGAAACCATAAAAAACAAAATTAAATGAC";
    }
    private String getTrueStringEndCCCN(){
        return "NCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCN";
    }

}
