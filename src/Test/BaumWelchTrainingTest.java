package Test;

import Main.Algorithms.BaumWelchTraining;
import Main.Conversions.Conversion;
import Main.Conversions.DNAConversion.DNAConversion7States;
import Main.Conversions.WeatherConversion;
import org.junit.Before;
import org.junit.Test;

import java.text.DecimalFormat;

/**
 *
 * @author Jens Kristian Refsgaard Nielsen & Thomas Damgaard Vinther
 */
public class BaumWelchTrainingTest {
    @Before
    public void setup(){
        //setup4();
    }


    @Test
    public void BaumWelchTrainingExample() {
        double[][] P = {{0.9, 0.1},     // H -> H   H -> L
                {0.2, 0.8}};    // L -> H   L -> L
        double[] pi = {0.5, 0.5};
        double[][] E = {{0.9, 0.1},         // x = sun|H   x = rain|H
                {0.3, 0.7}};        // x = sun|L   x = rain|L
        String[] observed = new String[1];
        observed[0] = "RRRRS";
        Conversion converter = new WeatherConversion();

        BaumWelchTraining training = new BaumWelchTraining(converter.observables(observed), P, E, pi, 1); //we make sure it only runs one time with comparefator= 1;


        P = training.getP();
        pi = training.getPi();
        System.out.println("P");
        for (int i = 0; i < P.length; i++) {
            for (int j = 0; j < P[0].length; j++) {
                System.out.print(P[i][j]);
                System.out.print(" ");
            }
            System.out.println();
        }
        System.out.println("PI");
        for (int i = 0; i < pi.length; i++) {
            System.out.println(pi[i]);
        }
        E = training.getE();
        System.out.println("E:");
        for (int i = 0; i < E.length; i++) {
            for (int j = 0; j < E[i].length; j++) {
                System.out.print(E[i][j]);
                System.out.print(" ");
            }
            System.out.println();
        }


        boolean cmpPi1 = compareFactor(pi[0], (0.05 * 0.0124635) / 0.028838165, 0.01);
        assert (cmpPi1);
        boolean cmpPi2 = compareFactor(pi[1], (0.35 * 0.080745) / 0.028838165, 0.01);
        assert (cmpPi2);


        double numeratorP11 = (0.05 * 0.9 * 0.1 * 0.02709 + 0.0115 * 0.9 * 0.1 * 0.105 + 0.005025 * 0.9 * 0.1 * 0.84 + 0.00270275 * 0.9 * 0.9 * 1.0);
        double denominatorP11 = (0.05 * 0.0124635 + 0.0115 * 0.02709 + 0.005025 * 0.105 + 0.00270275 * 0.84);
        boolean cmpP11 = compareFactor(P[0][0], numeratorP11 / denominatorP11, 0.001);
        assert (cmpP11);
        double P12 = 1- (numeratorP11 / denominatorP11);

        double numeratorP21 = 0.35*0.2*0.1*0.02709+0.1995*0.2*0.1*0.105+0.112525*0.2*0.1*0.84+0.0633658*0.2*0.9*1;
        double denominatorP21 = 0.35*0.080745+0.1995*0.14322+0.112525*0.252+0.0633658*0.42;
        double P21 = numeratorP21/denominatorP21;
        assert (compareFactor(P[0][1], P12, 0.001));
        assert (compareFactor(P[1][0], P21, 0.01));
        double P22 = 1- P21;
        assert (compareFactor(P[1][1], P22 , 0.001));


        double numeratorE22 = 0.35 * 0.080745 * 1 + 0.1995 * 0.14322 * 1 + 0.112525 * 0.252 * 1 + 0.0633658 * 0.42 * 1+0.015288745 * 1 * 0;
        double numeratorE21 = 0.35 * 0.080745 * 0 + 0.1995 * 0.14322 * 0 + 0.112525 * 0.252 * 0 + 0.0633658 * 0.42 * 0+0.015288745 * 1 * 1;
        double denominatorE2 = 0.35 * 0.080745 + 0.1995 * 0.14322 + 0.112525 * 0.252 + 0.0633658 * 0.42 + 0.0152888625 * 1;
        assert(compareFactor(E[1][1], numeratorE22 / denominatorE2, 0.01));
        assert(compareFactor(E[1][0], numeratorE21 / denominatorE2, 0.01));
        double numeratorE11 = 0.05 * 0.0124635 * 0 + 0.0115 * 0.02709 * 0 + 0.005025 * 0.105 * 0 + 0.00270275 * 0.84 * 0 + 0.0135950715 * 1 * 1;
        double numeratorE12 = 0.05 * 0.0124635 * 1 + 0.0115 * 0.02709 * 1 + 0.005025 * 0.105 * 1 + 0.00270275 * 0.84 * 1 + 0.0135950715 * 1 * 0;
        double denominatorE1 = 0.05 * 0.0124635 + 0.0115 * 0.02709 + 0.005025 * 0.105 + 0.00270275 * 0.84 + 0.0135950715 * 1;

        assert (compareFactor(E[0][0], numeratorE11/denominatorE1, 0.001));
        assert (compareFactor(E[0][1],numeratorE12/denominatorE1 , 0.001));
    }

    @Test
    public void BaumWelchTrainingExample2() {
        double[][] P = {{0.9, 0.1},     // H -> H   H -> L
                {0.2, 0.8}};    // L -> H   L -> L
        double[] pi = {0.5, 0.5};
        double[][] E = {{0.9, 0.1},         // x = sun|H   x = rain|H
                {0.3, 0.7}};        // x = sun|L   x = rain|L
        String[] observed = new String[2];
        observed[0] = "RRRRS";
        observed[1] = "SRRSR";
        Conversion converter = new WeatherConversion();

        BaumWelchTraining training = new BaumWelchTraining(converter.observables(observed), P, E, pi, 1.0); //We make sure it only runs one time with comparefator= 1;


        P = training.getP();
        pi = training.getPi();
        E = training.getE();
        System.out.println("E:");
        for (int i = 0; i < E.length; i++) {
            for (int j = 0; j < E[i].length; j++) {
                System.out.print(E[i][j]);
                System.out.print(" ");
            }
            System.out.println();
        }
        System.out.println("P");
        for (int i = 0; i < P.length; i++) {
            for (int j = 0; j < P[0].length; j++) {
                System.out.print(P[i][j]);
                System.out.print(" ");
            }
            System.out.println();
        }
        System.out.println("PI");
        for (int i = 0; i < pi.length; i++) {
            System.out.println(pi[i]);
        }

        double L2NumeratorP11 = (0.45 * 0.9 * 0.1 * 0.02499 + 0.0435 * 0.9 * 0.1 * 0.147 + 0.006225 * 0.9 * 0.9 * 0.16 + 0.0172375 * 0.9 * 0.1 * 1) / 0.012292874999999998;
        double L1NumeratorP11 = (0.05 * 0.9 * 0.1 * 0.02709 + 0.0115 * 0.9 * 0.1 * 0.105 + 0.005025 * 0.9 * 0.1 * 0.84 + 0.00270275 * 0.9 * 0.9 * 1.0) * (1 / 0.0288838165);
        double numeratorP11 = L1NumeratorP11 + L2NumeratorP11;

        double L2DenominatorP11 = (0.45 * 0.0090405 + 0.0435 * 0.02499 + 0.006225 * 0.147 + 0.01723275 * 0.16) * (1 / 0.012292874999999998);
        double L1DenominatorP11 = (0.05 * 0.0124635 + 0.0115 * 0.02709 + 0.005025 * 0.105 + 0.00270275 * 0.84) * (1 / 0.028883925);
        double denominatorP11 = L2DenominatorP11 + L1DenominatorP11;

        boolean cmpP11 = compareFactor(P[0][0], numeratorP11 / denominatorP11, 0.001);
        assert (cmpP11);
        assert (compareFactor(P[0][1], 0.50684, 0.001));
        assert (compareFactor(P[1][0], 0.09795, 0.001));
        assert (compareFactor(P[1][1], 0.902059, 0.001));



        double L2NumeratorE22 = (0.15 * 0.054831 * 0 + 0.1155 * 0.09702 * 1 + 0.067725 * 0.168 * 1 + 0.01644075 * 0.58 * 0 + 0.0104131125 * 1 * 1) / 0.012292874999999998;
        double L2NumeratorE21 = (0.15 * 0.054831 * 1 + 0.1155 * 0.09702 * 0 + 0.067725 * 0.168 * 0 + 0.01644075 * 0.58 * 1 + 0.0104131125 * 1 * 0) / 0.012292874999999998;

        double L2DenominatorE2 = (0.15 * 0.054831 + 0.1155 * 0.09702 + 0.067725 * 0.168 + 0.01644075 * 0.58 + 0.0104131125 * 1) / 0.012292874999999998;

        double L1NumeratorE22 = (0.35 * 0.080745 * 1 + 0.1995 * 0.14322 * 1 + 0.112525 * 0.252 * 1 + 0.0633658 * 0.42 * 1 + 0.0152888625 * 1 * 0) / 0.028883925;
        double L1NumeratorE21 = (0.35 * 0.080745 * 0 + 0.1995 * 0.14322 * 0 + 0.112525 * 0.252 * 0 + 0.0633658 * 0.42 * 0 + 0.0152888625 * 1 * 1) / 0.028883925;

        double L1DenominatorE2 = (0.35 * 0.080745 + 0.1995 * 0.14322 + 0.112525 * 0.252 + 0.0633658 * 0.42 + 0.0152888625) / 0.028883925;

        double numeratorE22 = L2NumeratorE22 + L1NumeratorE22;
        double numeratorE21 = L2NumeratorE21 + L1NumeratorE21;

        double denominatorE2 = L2DenominatorE2 + L1DenominatorE2;

        double E22 = numeratorE22 / denominatorE2;
        double E21 = numeratorE21 / denominatorE2;

        assert (compareFactor(E[1][1], E22, 0.00001));
        assert (compareFactor(E[1][0], E21, 0.001));

        double L2NumeratorE11 = (0.45 * 0.0090405 * 1 + 0.0435 * 0.02499 * 0 + 0.006225 * 0.147 * 0 + 0.01723275 *  0.16 * 1 + 0.0018797625 * 1 * 0) / 0.012292874999999998;
        double L2NumeratorE12 = (0.45 * 0.0090405 * 0 + 0.0435 * 0.02499 * 1 + 0.006225 * 0.147 * 1 + 0.01723275 *  0.16 * 0 + 0.0018797625 * 1 * 1) / 0.012292874999999998;
        double L2DenominatorE1 = (0.45 * 0.0090405 + 0.0435 * 0.02499+ 0.006225 * 0.147+ 0.01723275 *  0.16 + 0.0018797625 * 1) / 0.012292874999999998;



        double L1NumeratorE11 = (0.05 * 0.0124635 * 0 + 0.0115 * 0.02709 * 0 + 0.005025 * 0.105 * 0 + 0.00270275 * 0.84 * 0 + 0.0135950715 * 1 * 1) / 0.028883925;
        double L1NumeratorE12 = (0.05 * 0.0124635 * 1 + 0.0115 * 0.02709 * 1 + 0.005025 * 0.105 * 1 + 0.00270275 * 0.84 * 1 + 0.0135950715 * 1 * 0) / 0.028883925;
        double L1denominatorE1 = (0.05 * 0.0124635 + 0.0115 * 0.02709 + 0.005025 * 0.105 + 0.00270275 * 0.84 + 0.0135950715 * 1) / 0.028883925;

        double numeratorE11 = L2NumeratorE11 + L1NumeratorE11;
        double numeratorE12 = L2NumeratorE12 + L1NumeratorE12;
        double denominatorE1 = L2DenominatorE1 + L1denominatorE1;

        double E11 = numeratorE11 / denominatorE1;
        double E12 = numeratorE12 / denominatorE1;
        assert (compareFactor(E[0][0], E11, 0.001));
        assert (compareFactor(E[0][1], E12, 0.001));


        double oldPi1 = (0.05 * 0.0124635) / 0.028838165;
        double newPi1 = (0.45 * 0.0090405) / 0.012292875;
        double pi1 = 0.5 * (oldPi1 + newPi1);

        boolean cmpPi1 = compareFactor(pi[0], pi1, 0.001);
        assert (cmpPi1);
        assert (compareFactor(pi[1], 1 - pi1, 0.001));
    }

    /**
     * Check if n1 in [n2*(1-factor) , n2*(1+factor)], for instance a deviation of 0.01 means within 1%
     *
     * @param n1     first number
     * @param n2     second number
     * @param factor allowed deviation in %
     * @return true if they are close
     */
    private boolean compareFactor(double n1, double n2, double factor) {
        double standardDeviation = factor*n2;
        return n2 - standardDeviation <= n1 && n1 <= n2 + standardDeviation;
    }


    private void setup4() {     //TODO kill?
        DNAConversion7States hej = new DNAConversion7States();
        String[] observed = new String[5];
        observed[0] = "ATCATAATTTGTTAGCTTTATCAGCATTTACCCATGTTCCACGAGATCCGATTTGAACATAAATACCATTACTTGGTTGATCAATTTTTAATACTTTATAACTACCAGTGACGATAAAATATTCACCCACACGGAGAATTTGATCTCCTAAAACTTTTCCTTGGTTATCTGTTTCATCTAATGGTGTGGGATCAATCCAGTTTAGTGGAGTTGGGTCTCCTCCGGCTAATTCTTTATTAACGATCAAATTATTAACAAGCTGATCTACACGAAAAACGCCAGGGAAGGTAACAGTATCACCGACCTTAATGGAAGGTTTTGTATTGTCATTTTGAACCACAGACTGTGCGGGCTCTTTAATGGGAATATAGCGTCGGTTTCCAGAAAAACTGAGGTAGCTAAGCCATGTATAACCTCCAGCAGTCACGACTTTATCGTAATAAACAGATTGACCTGCTTCATAGTAAGCAAGATCAGGGCTATCGATACTGGTTTGTCCTTTGACTGGTAATCTGGTTGTAAAGTGATAAGTTCCAGAGGGGTCAAATGAAGCTTGAGAAATGTGTTTTAGTTGTCTTGGGTAGGAATGACTTGTCTGAGATGATAAGTCTTTAAAATGGATATAACCACTTACCTGAGATTTTGGAATTTGACGCTTATGGTATCTTTCAGGGCCTTGTCCAGCGTTGTAATTATACTCTTCGATAGTGACAGTGTCTCCACGGATATCAGCTACCCATGCTACATGACCGTAAGCAGCATTTGACTGATAAGCGTTTTTATCAAACCAAGCGATAGCCCCTATGCTTGGTGTCTTATTCACAGGATAACCCTGATTTTTCGCGATATGTCCCCACGTGCAGGCATTACCGTAGCCTTTAGGTAACTGAAAACCATTAGCAGAGCTTAAACGAAAAGCTGCAAAAGAAGTGCATTGGCGGATATACATGTTCCACGAATCGATTCCATTGCCTTTTTTCCACTTACTCGGATAGTTATCGCCTAAAACAGCTGCATGAACAAGATTTTCCTGTTGCGAAATAAGTGTAGATGGCATAGTAGGTACTAGACCATTAAAACCTAAAAGGATTACTCCTGAGACCAAAAAACGATGAAATTTTTTCATTTA";
        observed[1] = "ATTATAATTTGTTAGCTTTATCAGCATTTACCCATGTTCCACGAGATCCGATTTGAACATAAATACCATTACTTGGTTGATCAATTTTTAATACTTTATAACTACCAGTGACGATAAAATATTCACCCACACGGAGAATTTGATCTCCTAAAACTTTTCCTTGGTTATCTGTTTCATCTAATGGTGTGGGATCAATCCAGTTTAGTGGAGTTGGGTCTCCTCCGGCTAATTCTTTATTAACGATCAAATTATTAACAAGCTGATCTACACGAAAAACGCCAGGGAAGGTAACAGTATCACCGACCTTAATGGAAGGTTTTGTATTGTCATTTTGAACCACAGACTGTGCGGGCTCTTTAATGGGAATATAGCGTCGGTTTCCAGAAAAACTGAGGTAGCTAAGCCATGTATAACCTCCAGCAGTCACGACTTTATCGTAATAAACAGATTGACCTGCTTCATAGTAAGCAAGATCAGGGCTATCGATACTGGTTTGTCCTTTGACTGGTAATCTGGTTGTAAAGTGATAAGTTCCAGAGGGGTCAAATGAAGCTTGAGAAATGTGTTTTAGTTGTCTTGGGTAGGAATGACTTGTCTGAGATGATAAGTCTTTAAAATGGATATAACCACTTACCTGAGATTTTGGAATTTGACGCTTATGGTATCTTTCAGGGCCTTGTCCAGCGTTGTAATTATACTCTTCGATAGTGACAGTGTCTCCACGGATATCAGCTACCCATGCTACATGACCGTAAGCAGCATTTGACTGATAAGCGTTTTTATCAAACCAAGCGATAGCCCCTATGCTTGGTGTCTTATTCACAGGATAACCCTGATTTTTCGCGATATGTCCCCACGTGCAGGCATTACCGTAGCCTTTAGGTAACTGAAAACCATTAGCAGAGCTTAAACGAAAAGCTGCAAAAGAAGTGCATTGGCGGATATACATGTTCCACGAATCGATTCCATTGCCTTTTTTCCACTTACTCGGATAGTTATCGCCTAAAACAGCTGCATGAACAAGATTTTCCTGTTGCGAAATAAGTGTAGATGGCATAGTAGGTACTAGACCATTAAAACCTAAAAGGATTACTCCTGAGACCAAAAAACGATGAAATTTTTTCATTTA";
        observed[2] = "ACTATAATTTGTTAGCTTTATCAGCATTTACCCATGTTCCACGAGATCCGATTTGAACATAAATACCATTACTTGGTTGATCAATTTTTAATACTTTATAACTACCAGTGACGATAAAATATTCACCCACACGGAGAATTTGATCTCCTAAAACTTTTCCTTGGTTATCTGTTTCATCTAATGGTGTGGGATCAATCCAGTTTAGTGGAGTTGGGTCTCCTCCGGCTAATTCTTTATTAACGATCAAATTATTAACAAGCTGATCTACACGAAAAACGCCAGGGAAGGTAACAGTATCACCGACCTTAATGGAAGGTTTTGTATTGTCATTTTGAACCACAGACTGTGCGGGCTCTTTAATGGGAATATAGCGTCGGTTTCCAGAAAAACTGAGGTAGCTAAGCCATGTATAACCTCCAGCAGTCACGACTTTATCGTAATAAACAGATTGACCTGCTTCATAGTAAGCAAGATCAGGGCTATCGATACTGGTTTGTCCTTTGACTGGTAATCTGGTTGTAAAGTGATAAGTTCCAGAGGGGTCAAATGAAGCTTGAGAAATGTGTTTTAGTTGTCTTGGGTAGGAATGACTTGTCTGAGATGATAAGTCTTTAAAATGGATATAACCACTTACCTGAGATTTTGGAATTTGACGCTTATGGTATCTTTCAGGGCCTTGTCCAGCGTTGTAATTATACTCTTCGATAGTGACAGTGTCTCCACGGATATCAGCTACCCATGCTACATGACCGTAAGCAGCATTTGACTGATAAGCGTTTTTATCAAACCAAGCGATAGCCCCTATGCTTGGTGTCTTATTCACAGGATAACCCTGATTTTTCGCGATATGTCCCCACGTGCAGGCATTACCGTAGCCTTTAGGTAACTGAAAACCATTAGCAGAGCTTAAACGAAAAGCTGCAAAAGAAGTGCATTGGCGGATATACATGTTCCACGAATCGATTCCATTGCCTTTTTTCCACTTACTCGGATAGTTATCGCCTAAAACAGCTGCATGAACAAGATTTTCCTGTTGCGAAATAAGTGTAGATGGCATAGTAGGTACTAGACCATTAAAACCTAAAAGGATTACTCCTGAGACCAAAAAACGATGAAATTTTTTCATTTA";
        observed[4] = "TATGACTGAAAATGAACAAATTTTTTGGAACAGGGTCTTGGAATTAGCTCAGAGTCAATTAAAACAGGCAACTTATGAATTTTTTGTTCATGATGCCCGTCTATTAAAGGTCGATAAGCATATTGCAACTATTTACTTAGATCAAATGAAAGAGCTCTTTTGGGAAAAAAATCTTAAAGATGTTATTCTTACTGCTGGTTTTGAAGTTTATAACGCTCAAATTTCTGTTGACTATGTTTTCGAAGAAGACCTAATGATTGAGCAAAATCAGACCAAAATCAACCAAAAACCTAAGCAGCAAGCCTTAAATTCTTTGCCTACTGTTACTTCAGATTTAAACTCGAAATATAGTTTTGAAAACTTTATTCAAGGAGATGAAAATCGTTGGGCTGTTGCTGCTTCAATAGCAGTAGCTAATACTCCTGGAACTACCTATAATCCTTTGTTTATTTGGGGTGGCCCTGGGCTTGGAAAAACCCATTTATTAAATGCTATTGGTAATTCTGTACTATTAGAAAATCCAAATGCTCGAATTAAATATATCACAGCTGAAAACTTTATTAATGAGTTTGTTATCCATATTCGCCTTGATACCATGGATGAATTGAAAGAAAAATTTCGTAATTTAGATTTACTCCTTATTGATGATATCCAATCTTTAGCTAAAAAAACGCTCTCTGGAACACAAGAAGAGTTCTTTAATACTTTTAATGCACTTCATAATAATAACAAACAAATTGTCCTAACAAGCGACCGTACACCAGATCATCTCAATGATTTAGAAGATCGATTAGTTACTCGTTTTAAATGGGGATTAACAGTCAATATCACACCTCCTGATTTTGAAACACGAGTGGCTATTTTGACAAATAAAATTCAAGAATATAACTTTATTTTTCCTCAAGATACCATTGAGTATTTGGCTGGTCAATTTGATTCTAATGTCAGAGATTTAGAAGGTGCCTTAAAAGATATTAGTCTGGTTGCTAATTTCAAACAAATTGACACGATTACTGTTGACATTGCTGCCGAAGCTATTCGCGCCAGAAAGCAAGATGGACCTAAAATGACAGTTATTCCCATCGAAGAAATTCAAGCGCAAGTTGGAAAATTTTACGGTGTTACCGTCAAAGAAATTAAAGCTACTAAACGAACACAAAATATTGTTTTAGCAAGACAAGTAGCTATGTTTTTAGCACGTGAAATGACAGATAACAGTCTTCCTAAAATTGGAAAAGAATTTGGTGGCAGAGACCATTCAACAGTACTCCATGCCTATAATAAAATCAAAAACATGATCAGCCAGGACGAAAGCCTTAGGATCGAAATTGAAACCATAAAAAACAAAATTAAATAAC";
        observed[3] = "TATGACTGAAAATGAACAAATTTTTTGGAACAGGGTCTTGGAATTAGCTCAGAGTCAATTAAAACAGGCAACTTATGAATTTTTTGTTCATGATGCCCGTCTATTAAAGGTCGATAAGCATATTGCAACTATTTACTTAGATCAAATGAAAGAGCTCTTTTGGGAAAAAAATCTTAAAGATGTTATTCTTACTGCTGGTTTTGAAGTTTATAACGCTCAAATTTCTGTTGACTATGTTTTCGAAGAAGACCTAATGATTGAGCAAAATCAGACCAAAATCAACCAAAAACCTAAGCAGCAAGCCTTAAATTCTTTGCCTACTGTTACTTCAGATTTAAACTCGAAATATAGTTTTGAAAACTTTATTCAAGGAGATGAAAATCGTTGGGCTGTTGCTGCTTCAATAGCAGTAGCTAATACTCCTGGAACTACCTATAATCCTTTGTTTATTTGGGGTGGCCCTGGGCTTGGAAAAACCCATTTATTAAATGCTATTGGTAATTCTGTACTATTAGAAAATCCAAATGCTCGAATTAAATATATCACAGCTGAAAACTTTATTAATGAGTTTGTTATCCATATTCGCCTTGATACCATGGATGAATTGAAAGAAAAATTTCGTAATTTAGATTTACTCCTTATTGATGATATCCAATCTTTAGCTAAAAAAACGCTCTCTGGAACACAAGAAGAGTTCTTTAATACTTTTAATGCACTTCATAATAATAACAAACAAATTGTCCTAACAAGCGACCGTACACCAGATCATCTCAATGATTTAGAAGATCGATTAGTTACTCGTTTTAAATGGGGATTAACAGTCAATATCACACCTCCTGATTTTGAAACACGAGTGGCTATTTTGACAAATAAAATTCAAGAATATAACTTTATTTTTCCTCAAGATACCATTGAGTATTTGGCTGGTCAATTTGATTCTAATGTCAGAGATTTAGAAGGTGCCTTAAAAGATATTAGTCTGGTTGCTAATTTCAAACAAATTGACACGATTACTGTTGACATTGCTGCCGAAGCTATTCGCGCCAGAAAGCAAGATGGACCTAAAATGACAGTTATTCCCATCGAAGAAATTCAAGCGCAAGTTGGAAAATTTTACGGTGTTACCGTCAAAGAAATTAAAGCTACTAAACGAACACAAAATATTGTTTTAGCAAGACAAGTAGCTATGTTTTTAGCACGTGAAATGACAGATAACAGTCTTCCTAAAATTGGAAAAGAATTTGGTGGCAGAGACCATTCAACAGTACTCCATGCCTATAATAAAATCAAAAACATGATCAGCCAGGACGAAAGCCTTAGGATCGAAATTGAAACCATAAAAAACAAAATTAAATAGC";
        BaumWelchTraining training = new BaumWelchTraining(hej.observables(observed), hej.getInitialP(), hej.getInitialE(), hej.getInitialPi(), 0.000000000001);


        double[][] P = training.getP();
        double[]pi = training.getPi();
        double[][]E = training.getE();
        System.out.println("E:");
        for (int i = 0; i < E.length; i++) {
            for (int j = 0; j < E[i].length; j++) {
                System.out.print(E[i][j]);
                System.out.print(" ");
            }
            System.out.println();
        }
        System.out.println("PI");
        for (int i = 0; i < pi.length; i++) {
            System.out.println(pi[i]);
        }
        System.out.println("P");
        for (int i = 0; i < P.length; i++) {
            for (int j = 0; j < P[0].length; j++) {
                System.out.print(P[i][j]);
                System.out.print(" ");
            }
            System.out.println();
        }
    }
    private void print_double(double[][] printee){
        DecimalFormat df = new DecimalFormat("#.###");
        for(double[] doubles : printee){
            for(double d : doubles){
                System.out.print(d+ " ");
                //System.out.print(df.format(d) + " ");
            }
            System.out.println();
        }
    }
}
