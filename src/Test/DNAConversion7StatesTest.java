package Test;
import Main.Conversions.DNAConversion.DNAConversion7States;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

/**
 *
 * @author Jens Kristian Refsgaard Nielsen & Thomas Damgaard Vinther
 */
public class DNAConversion7StatesTest {
    private DNAConversion7States converter;

    @Before
    public void setUp() {
        this.converter = new DNAConversion7States();
    }

    @Test
    public void testStates012(){
        String[] genome1 = new String[1];
        genome1[0] = getStringObservedCoding();

        String[] trueAnnotation1 = new String[1];
        trueAnnotation1[0] = getTrueStringCCCN();//This is a file containing N's, C's and R's

        ArrayList<int[]> statesConverted = converter.states(trueAnnotation1,genome1);   //This conversion should give us which states produced the true annotation

        assert(statesConverted.get(0).length == trueAnnotation1[0].length());
        int length = statesConverted.get(0).length;
        assert(statesConverted.get(0)[length-1]==3);
        assert(statesConverted.get(0)[length-2]==0);
        assert(statesConverted.get(0)[length-3]==1);
        assert(statesConverted.get(0)[length-4]==2);
        assert(statesConverted.get(0)[0]==3);
        assert(statesConverted.get(0)[1]==2);
        assert(statesConverted.get(0)[2]==1);
        assert(statesConverted.get(0)[3]==0);
        assert(statesConverted.get(0)[4]==2);
        assert(statesConverted.get(0)[5]==1);
        assert(statesConverted.get(0)[6]==0);
        for (int i = 1; i < length-1; i++) {
            assert(!(statesConverted.get(0)[i]==3));

        }
    }



    @Test
    public void testStates4_5_6(){
        String[] genome1 = new String[1];
        genome1[0] = getStringObservedReverseCoding();

        String[] trueAnnotation1 = new String[1];
        trueAnnotation1[0] = getTrueStringNRRR();//This is a file containing N's, C's and R's

        ArrayList<int[]> statesConverted = converter.states(trueAnnotation1,genome1);   //This conversion should give us which states produced the true annotation

        assert(statesConverted.get(0).length == trueAnnotation1[0].length());
        int length = statesConverted.get(0).length;
        assert(statesConverted.get(0)[length-1]==3);
        assert(statesConverted.get(0)[length-2]==6);
        assert(statesConverted.get(0)[length-3]==5);
        assert(statesConverted.get(0)[length-4]==4);
        assert(statesConverted.get(0)[0]==3);
        assert(statesConverted.get(0)[1]==4);
        assert(statesConverted.get(0)[2]==5);
        assert(statesConverted.get(0)[3]==6);
        assert(statesConverted.get(0)[4]==4);
        assert(statesConverted.get(0)[5]==5);
        assert(statesConverted.get(0)[6]==6);
        for (int i = 1; i < length-1; i++) {
            assert(!(statesConverted.get(0)[i]==3));

        }
    }

    @Test
    public void testMixedStatesRtoC(){
        String[] genome1 = new String[1];
        genome1[0] = getStringMixedRC();

        String[] trueAnnotation1 = new String[1];
        trueAnnotation1[0] = getTrueStringMixedRC();//This is a file containing N's, C's and R's
        ArrayList<int[]> statesConverted = converter.states(trueAnnotation1,genome1);   //This conversion should give us which states produced the true annotation

        assert(statesConverted.get(0).length == trueAnnotation1[0].length());
        int length = statesConverted.get(0).length;
        assert(length == getStringMixedRC().length());
        assert(statesConverted.get(0)[0]==3);
        assert(statesConverted.get(0)[1]==4);
        assert(statesConverted.get(0)[2]==5);
        assert(statesConverted.get(0)[3]==6);
        assert(statesConverted.get(0)[10]==2);
        assert(statesConverted.get(0)[11]==1);
        assert(statesConverted.get(0)[12]==0);
        assert(statesConverted.get(0)[13]==2);

    }

    @Test
    public void testMixedStatesCtoR(){
        String[] genome1 = new String[1];
        genome1[0] = getStringMixedCR();

        String[] trueAnnotation1 = new String[1];
        trueAnnotation1[0] = getTrueStringMixedCR();//This is a file containing N's, C's and R's

        ArrayList<int[]> statesConverted = converter.states(trueAnnotation1,genome1);   //This conversion should give us which states produced the true annotation

        assert(statesConverted.get(0).length == trueAnnotation1[0].length());
        int length = statesConverted.get(0).length;
        assert(length == getStringMixedCR().length());
        assert(statesConverted.get(0)[0]==3);
        assert(statesConverted.get(0)[1]==2);
        assert(statesConverted.get(0)[2]==1);
        assert(statesConverted.get(0)[3]==0);
        assert(statesConverted.get(0)[10]==4);
        assert(statesConverted.get(0)[11]==5);
        assert(statesConverted.get(0)[12]==6);
        assert(statesConverted.get(0)[13]==4);

    }





    private String getStringObservedReverseCoding(){
        return "ACTATAATTTGTTAGCTTTATCAGCATTTACCCATGTTCCACGAGATCCGATTTGAACATAAATACCATTACTTGGTTGATCAATTTTTAATACTTTATAACTACCAGTGACGATAAAATATTCACCCACACGGAGAATTTGATCTCCTAAAACTTTTCCTTGGTTATCTGTTTCATCTAATGGTGTGGGATCAATCCAGTTTAGTGGAGTTGGGTCTCCTCCGGCTAATTCTTTATTAACGATCAAATTATTAACAAGCTGATCTACACGAAAAACGCCAGGGAAGGTAACAGTATCACCGACCTTAATGGAAGGTTTTGTATTGTCATTTTGAACCACAGACTGTGCGGGCTCTTTAATGGGAATATAGCGTCGGTTTCCAGAAAAACTGAGGTAGCTAAGCCATGTATAACCTCCAGCAGTCACGACTTTATCGTAATAAACAGATTGACCTGCTTCATAGTAAGCAAGATCAGGGCTATCGATACTGGTTTGTCCTTTGACTGGTAATCTGGTTGTAAAGTGATAAGTTCCAGAGGGGTCAAATGAAGCTTGAGAAATGTGTTTTAGTTGTCTTGGGTAGGAATGACTTGTCTGAGATGATAAGTCTTTAAAATGGATATAACCACTTACCTGAGATTTTGGAATTTGACGCTTATGGTATCTTTCAGGGCCTTGTCCAGCGTTGTAATTATACTCTTCGATAGTGACAGTGTCTCCACGGATATCAGCTACCCATGCTACATGACCGTAAGCAGCATTTGACTGATAAGCGTTTTTATCAAACCAAGCGATAGCCCCTATGCTTGGTGTCTTATTCACAGGATAACCCTGATTTTTCGCGATATGTCCCCACGTGCAGGCATTACCGTAGCCTTTAGGTAACTGAAAACCATTAGCAGAGCTTAAACGAAAAGCTGCAAAAGAAGTGCATTGGCGGATATACATGTTCCACGAATCGATTCCATTGCCTTTTTTCCACTTACTCGGATAGTTATCGCCTAAAACAGCTGCATGAACAAGATTTTCCTGTTGCGAAATAAGTGTAGATGGCATAGTAGGTACTAGACCATTAAAACCTAAAAGGATTACTCCTGAGACCAAAAAACGATGAAATTTTTTCATT";
    }
    private String getTrueStringNRRR(){
        return "NRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRN";
    }


    private String getStringObservedCoding(){
        return "TATGACTGAAAATGAACAAATTTTTTGGAACAGGGTCTTGGAATTAGCTCAGAGTCAATTAAAACAGGCAACTTATGAATTTTTTGTTCATGATGCCCGTCTATTAAAGGTCGATAAGCATATTGCAACTATTTACTTAGATCAAATGAAAGAGCTCTTTTGGGAAAAAAATCTTAAAGATGTTATTCTTACTGCTGGTTTTGAAGTTTATAACGCTCAAATTTCTGTTGACTATGTTTTCGAAGAAGACCTAATGATTGAGCAAAATCAGACCAAAATCAACCAAAAACCTAAGCAGCAAGCCTTAAATTCTTTGCCTACTGTTACTTCAGATTTAAACTCGAAATATAGTTTTGAAAACTTTATTCAAGGAGATGAAAATCGTTGGGCTGTTGCTGCTTCAATAGCAGTAGCTAATACTCCTGGAACTACCTATAATCCTTTGTTTATTTGGGGTGGCCCTGGGCTTGGAAAAACCCATTTATTAAATGCTATTGGTAATTCTGTACTATTAGAAAATCCAAATGCTCGAATTAAATATATCACAGCTGAAAACTTTATTAATGAGTTTGTTATCCATATTCGCCTTGATACCATGGATGAATTGAAAGAAAAATTTCGTAATTTAGATTTACTCCTTATTGATGATATCCAATCTTTAGCTAAAAAAACGCTCTCTGGAACACAAGAAGAGTTCTTTAATACTTTTAATGCACTTCATAATAATAACAAACAAATTGTCCTAACAAGCGACCGTACACCAGATCATCTCAATGATTTAGAAGATCGATTAGTTACTCGTTTTAAATGGGGATTAACAGTCAATATCACACCTCCTGATTTTGAAACACGAGTGGCTATTTTGACAAATAAAATTCAAGAATATAACTTTATTTTTCCTCAAGATACCATTGAGTATTTGGCTGGTCAATTTGATTCTAATGTCAGAGATTTAGAAGGTGCCTTAAAAGATATTAGTCTGGTTGCTAATTTCAAACAAATTGACACGATTACTGTTGACATTGCTGCCGAAGCTATTCGCGCCAGAAAGCAAGATGGACCTAAAATGACAGTTATTCCCATCGAAGAAATTCAAGCGCAAGTTGGAAAATTTTACGGTGTTACCGTCAAAGAAATTAAAGCTACTAAACGAACACAAAATATTGTTTTAGCAAGACAAGTAGCTATGTTTTTAGCACGTGAAATGACAGATAACAGTCTTCCTAAAATTGGAAAAGAATTTGGTGGCAGAGACCATTCAACAGTACTCCATGCCTATAATAAAATCAAAAACATGATCAGCCAGGACGAAAGCCTTAGGATCGAAATTGAAACCATAAAAAACAAAATTAAATGAC";
    }
    private String getTrueStringCCCN(){
        return "NCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCN";
    }
    private String getTrueStringMixedCR(){
        return "NCCCCCCCCCRRRRRRRRRN";
    }
    private String getTrueStringMixedRC(){
        return "NRRRRRRRRRCCCCCCCCCN";
    }
    private String getStringMixedCR(){
        return "TATGGATTGATTACGTCATG";
    }
    private String getStringMixedRC(){
        return "TTTAGATCATATGACGTAAG";
    }

}
