package Test;
import Main.*;
import Main.DNAConversion.DNAConversion14States;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class DNAConversion14StatesTest {
    private DNAConversion14States converter;

    @Before
    public void setUp() {
        DNAConversion14States converter = new DNAConversion14States();
    }

    @Test
    public void printAndSeeState5_6_7(){
        String[] genome1 = new String[1];
        genome1[0] = getStringEnd7890();

        String[] trueAnnotation1 = new String[1];
        trueAnnotation1[0] = getTrueStringEndCCCN();//This is a file containing N's, C's and R's

        converter = new DNAConversion14States();      //TODO here we choose model as well.
        ArrayList<int[]> observedConverted = converter.observables(genome1);
        ArrayList<int[]> statesConverted = converter.states(trueAnnotation1,genome1);   //This conversion should give us which states produced the true annotation
        //ArrayList<int[]> statesConverted = converter.states(trueAnnotation1);   //This conversion should give us which states produced the true annotation
        CountTraining trainer = new CountTraining(observedConverted,statesConverted,14,4); //TODO We train and choose model
        double[] pi = trainer.getPi();  //We retrieve the newly found parameters
        double[][] E = trainer.getE();
        double[][] P = trainer.getP();
        System.out.println("Only state 5,6 & 7 will be non NAN");
        for (int i = 0; i < 14; i++) {
            for (int j = 0; j < 14; j++) {
                System.out.print(P[i][j]);
                System.out.print(" ");
            }
            System.out.println();
        }
        for (int i = 0; i < 14; i++) {
            for (int j = 0; j < 4; j++) {
                System.out.print(E[i][j]);
                System.out.print(" ");
            }
            System.out.println();
        }

    }
    @Test
    public void printAndSeeState8_9_10(){
        String[] genome1 = new String[1];
        genome1[0] = getStringEnd1011120();

        String[] trueAnnotation1 = new String[1];
        trueAnnotation1[0] = getTrueStringEndCCCN();//This is a file containing N's, C's and R's

        converter = new DNAConversion14States();      //TODO here we choose model as well.
        ArrayList<int[]> observedConverted = converter.observables(genome1);
        ArrayList<int[]> statesConverted = converter.states(trueAnnotation1,genome1);   //This conversion should give us which states produced the true annotation
        //ArrayList<int[]> statesConverted = converter.states(trueAnnotation1);   //This conversion should give us which states produced the true annotation
        CountTraining trainer = new CountTraining(observedConverted,statesConverted,14,4); //TODO We train and choose model
        double[] pi = trainer.getPi();  //We retrieve the newly found parameters
        double[][] E = trainer.getE();
        double[][] P = trainer.getP();
        System.out.println("Only state 10, 11 & 12 will be non NAN, from the last outputting states");
        for (int i = 0; i < 14; i++) {
            for (int j = 0; j < 14; j++) {
                System.out.print(P[i][j]);
                System.out.print(" ");
            }
            System.out.println();
        }
        for (int i = 0; i < 14; i++) {
            for (int j = 0; j < 4; j++) {
                System.out.print(E[i][j]);
                System.out.print(" ");
            }
            System.out.println();
        }
    }
    @Test
    public void printAndSeeState11_12_13(){
        String[] genome1 = new String[1];
        genome1[0] = getStringEnd1314150();

        String[] trueAnnotation1 = new String[1];
        trueAnnotation1[0] = getTrueStringEndCCCN();//This is a file containing N's, C's and R's

        converter = new DNAConversion14States();      //TODO here we choose model as well.
        ArrayList<int[]> observedConverted = converter.observables(genome1);
        ArrayList<int[]> statesConverted = converter.states(trueAnnotation1,genome1);   //This conversion should give us which states produced the true annotation
        //ArrayList<int[]> statesConverted = converter.states(trueAnnotation1);   //This conversion should give us which states produced the true annotation
        CountTraining trainer = new CountTraining(observedConverted,statesConverted,14,4); //TODO We train and choose model
        double[] pi = trainer.getPi();  //We retrieve the newly found parameters
        double[][] E = trainer.getE();
        double[][] P = trainer.getP();
        System.out.println("Only state 13, 14 & 15 will be non NAN, from the last outputting states");
        for (int i = 0; i < 14; i++) {
            for (int j = 0; j < 14; j++) {
                System.out.print(P[i][j]);
                System.out.print(" ");
            }
            System.out.println();
        }
        for (int i = 0; i < 14; i++) {
            for (int j = 0; j < 4; j++) {
                System.out.print(E[i][j]);
                System.out.print(" ");
            }
            System.out.println();
        }

    }

    @Test
    public void testStates5_6_7(){
        String[] genome1 = new String[1];
        genome1[0] = getStringEnd7890();

        String[] trueAnnotation1 = new String[1];
        trueAnnotation1[0] = getTrueStringEndCCCN();//This is a file containing N's, C's and R's

        converter = new DNAConversion14States();      //TODO here we choose model as well.

        ArrayList<int[]> statesConverted = converter.states(trueAnnotation1,genome1);   //This conversion should give us which states produced the true annotation

        assert(statesConverted.get(0).length == trueAnnotation1[0].length());
        int length = statesConverted.get(0).length;
        assert(statesConverted.get(0)[length-1]==0);
        assert(statesConverted.get(0)[length-2]==7);
        assert(statesConverted.get(0)[length-3]==6);
        assert(statesConverted.get(0)[length-4]==5);

        assert(statesConverted.get(0)[0]==0);
        assert(statesConverted.get(0)[1]==1);
        assert(statesConverted.get(0)[2]==2);
        assert(statesConverted.get(0)[3]==3);
        for (int i = 0; i < length-4; i++) {
            assert(! (statesConverted.get(0)[i]==5));     //assert false doesn't work?
            assert(! (statesConverted.get(0)[i]==7));
            assert(! (statesConverted.get(0)[i]==7));
            assert(! (statesConverted.get(0)[i]==8));
            assert(! (statesConverted.get(0)[i]==9));
            assert(! (statesConverted.get(0)[i]==10));
            assert(! (statesConverted.get(0)[i]==11));
            assert(! (statesConverted.get(0)[i]==12));
            assert(! (statesConverted.get(0)[i]==13));
        }

    }

    @Test
    public void testStates8_9_10(){
        String[] genome1 = new String[1];
        genome1[0] = getStringEnd1011120();

        String[] trueAnnotation1 = new String[1];
        trueAnnotation1[0] = getTrueStringEndCCCN();//This is a file containing N's, C's and R's

        converter = new DNAConversion14States();      //TODO here we choose model as well.

        ArrayList<int[]> statesConverted = converter.states(trueAnnotation1,genome1);   //This conversion should give us which states produced the true annotation

        assert(statesConverted.get(0).length == trueAnnotation1[0].length());
        int length = statesConverted.get(0).length;
        assert(statesConverted.get(0)[length-1]==0);
        assert(statesConverted.get(0)[length-2]==10);
        assert(statesConverted.get(0)[length-3]==9);
        assert(statesConverted.get(0)[length-4]==8);

        assert(statesConverted.get(0)[0]==0);
        assert(statesConverted.get(0)[1]==1);
        assert(statesConverted.get(0)[2]==2);
        assert(statesConverted.get(0)[3]==3);
        for (int i = 0; i < length-4; i++) {
            assert(! (statesConverted.get(0)[i]==5));     //assert false doesn't work?
            assert(! (statesConverted.get(0)[i]==7));
            assert(! (statesConverted.get(0)[i]==7));
            assert(! (statesConverted.get(0)[i]==8));
            assert(! (statesConverted.get(0)[i]==9));
            assert(! (statesConverted.get(0)[i]==10));
            assert(! (statesConverted.get(0)[i]==11));
            assert(! (statesConverted.get(0)[i]==12));
            assert(! (statesConverted.get(0)[i]==13));
        }

    }

    @Test
    public void testStates11_12_13(){
        String[] genome1 = new String[1];
        genome1[0] = getStringEnd1314150();

        String[] trueAnnotation1 = new String[1];
        trueAnnotation1[0] = getTrueStringEndCCCN();//This is a file containing N's, C's and R's

        converter = new DNAConversion14States();      //TODO here we choose model as well.

        ArrayList<int[]> statesConverted = converter.states(trueAnnotation1,genome1);   //This conversion should give us which states produced the true annotation

        assert(statesConverted.get(0).length == trueAnnotation1[0].length());
        int length = statesConverted.get(0).length;
        assert(statesConverted.get(0)[length-1]==0);
        assert(statesConverted.get(0)[length-2]==13);
        assert(statesConverted.get(0)[length-3]==12);
        assert(statesConverted.get(0)[length-4]==11);

        assert(statesConverted.get(0)[0]==0);
        assert(statesConverted.get(0)[1]==1);
        assert(statesConverted.get(0)[2]==2);
        assert(statesConverted.get(0)[3]==3);
        for (int i = 0; i < length-4; i++) {
            assert(! (statesConverted.get(0)[i]==5));     //assert false doesn't work?
            assert(! (statesConverted.get(0)[i]==7));
            assert(! (statesConverted.get(0)[i]==7));
            assert(! (statesConverted.get(0)[i]==8));
            assert(! (statesConverted.get(0)[i]==9));
            assert(! (statesConverted.get(0)[i]==10));
            assert(! (statesConverted.get(0)[i]==11));
            assert(! (statesConverted.get(0)[i]==12));
            assert(! (statesConverted.get(0)[i]==13));
        }
    }

    @Test
    public void printAndSeeAllStates(){
        String[] genome1 = new String[1];
        genome1[0] = getStringEnd1314150()+getStringEnd7890()+getStringEnd1011120();

        String[] trueAnnotation1 = new String[1];
        trueAnnotation1[0] = getTrueStringEndCCCN()+getTrueStringEndCCCN()+getTrueStringEndCCCN();//This is a file containing N's, C's and R's

        converter = new DNAConversion14States();      //TODO here we choose model as well.
        ArrayList<int[]> observedConverted = converter.observables(genome1);
        ArrayList<int[]> statesConverted = converter.states(trueAnnotation1,genome1);   //This conversion should give us which states produced the true annotation
        //ArrayList<int[]> statesConverted = converter.states(trueAnnotation1);   //This conversion should give us which states produced the true annotation
        CountTraining trainer = new CountTraining(observedConverted,statesConverted,14,4); //TODO We train and choose model
        double[] pi = trainer.getPi();  //We retrieve the newly found parameters
        double[][] E = trainer.getE();
        double[][] P = trainer.getP();
        System.out.println("All states should be visited");
        for (int i = 0; i < 14; i++) {
            for (int j = 0; j < 14; j++) {
                System.out.print(P[i][j]);
                System.out.print(" ");
            }
            System.out.println();
        }
        for (int i = 0; i < 14; i++) {
            for (int j = 0; j < 4; j++) {
                System.out.print(E[i][j]);
                System.out.print(" ");
            }
            System.out.println();
        }

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
