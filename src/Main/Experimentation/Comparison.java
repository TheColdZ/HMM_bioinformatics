package Main.Experimentation;

/**
 * This class handles comparison of two strings and calculations of Sensitivity, Specificity and Approximate Correlation, given TruePositive, FalsePositive,
 * TrueNegative & FalseNegative
 * @author Jens Kristian Refsgaard Nielsen & Thomas Damgaard Vinther
 */
public class Comparison {
    private double AC;
    private double ACP;
    private double correlationCoefficient;
    private double sensitivity;
    private double specificity;

    /**
     * Simple constructor to set up the desired variables.
     */
    public Comparison(){
        this.correlationCoefficient = 0;
        this.sensitivity = 0;
        this.specificity = 0;
        this.ACP = 0;
        this.AC = 0;
    }


    /**
     * This method compares a true annotation and a predicted annotation, having three kinds of encoding: non-coding and two other forms of encoding
     * If wanting to only compare with only on form of coding, set either comparisonChar1 or comparisonChar2 to a char such as '_' that is not found
     * in the string to do so.
     * @param trueAnnotation        The true annotation
     * @param predictionAnnotation  The predicted annotation
     * @param nonCodingChar         The char denoting non-coding
     * @param comparisonChar1       The first comparison char denoting a form of coding
     * @param comparisonChar2       The second comparison char denoting a form of coding, if only comparing with
     */
    public void compare(String trueAnnotation, String predictionAnnotation,char nonCodingChar, char comparisonChar1, char comparisonChar2){
        double truePositive,falsePositive,trueNegative,falseNegative;
        truePositive = 0;
        falsePositive = 0;
        trueNegative = 0;
        falseNegative = 0;
        for (int i = 0; i < predictionAnnotation.length(); i++) {
            if( predictionAnnotation.charAt(i) == comparisonChar1 || predictionAnnotation.charAt(i) == comparisonChar2){
                if(trueAnnotation.charAt(i) == comparisonChar1 || trueAnnotation.charAt(i) == comparisonChar2  ){
                    truePositive++;
                }
                else{
                    falsePositive++;
                }
            }
            else if(predictionAnnotation.charAt(i) == nonCodingChar){
                if(trueAnnotation.charAt(i)== nonCodingChar){
                    trueNegative++;
                }
                else{
                    falseNegative++;
                }
            }
        }
        printPositiveAndNegatives(comparisonChar1,comparisonChar2,truePositive,falsePositive,trueNegative,falseNegative);
        calculate(truePositive,falsePositive,trueNegative,falseNegative);

    }

    /**
     * This method is used to printing the calculated findings of the comparison. This is easier to modify.
     * @param comparisonChar1   The first char that was used to compare
     * @param comparisonChar2   The second char that was used to compare
     * @param tp                The number of true positive findings
     * @param fp                The number of false positive findings
     * @param tn                The number of true negative findings
     * @param fn                The number of false negative findings
     */
    private void printPositiveAndNegatives(char comparisonChar1, char comparisonChar2, double tp, double fp, double tn, double fn){
        System.out.print(comparisonChar1+""+comparisonChar2+"s: tp="+tp+", fp="+fp+", tn="+tn+",fn="+fn);

    }

    /**
     * This method calculates statistical data on the comparison
     * @param truePositive  The number of truepositive findings
     * @param falsePositive The number of falseositive findings
     * @param trueNegative  The number of truenegative findings
     * @param falseNegative The number of falsenegative findings
     */
    public void calculate(double truePositive, double falsePositive, double trueNegative, double falseNegative) {
        printPositiveAndNegatives('a','a',truePositive,falsePositive,trueNegative,falseNegative);
        double numerator = truePositive*trueNegative-falseNegative*falsePositive;
        double denominator = (truePositive+falseNegative)*(trueNegative+falsePositive)*(truePositive+falsePositive)*(trueNegative+falseNegative);
        double squareRootDenominator = Math.sqrt(denominator);
        correlationCoefficient = numerator/squareRootDenominator;

        sensitivity = truePositive/(truePositive+falseNegative);
        specificity = truePositive/(truePositive+falsePositive);
        ACP = 0.25*((truePositive/(truePositive+falseNegative)+(truePositive/(truePositive+falsePositive))+(trueNegative/(trueNegative+falsePositive))+(trueNegative/(trueNegative+falseNegative))));
        AC = (ACP - 0.5)*2;
        printStatistical(sensitivity,specificity,AC,ACP,correlationCoefficient);
    }

    /**
     * Printing function. Useful in the event that a specific form of formatted print is wanted, e.g. when inserting into a report.
     * @param sensitivity The sensitivity to print
     * @param specificity   The specificity to print
     * @param AC    The approximate correlation to print
     * @param ACP   Average Conditional Probability to print
     * @param correlationCoefficient The correlationCoefficient to print
     */
    private void printStatistical(double sensitivity,double specificity, double AC, double ACP, double correlationCoefficient){
        System.out.println(" Sensitivity ="+sensitivity+", specificity ="+specificity+", AC="+AC+", ACP:"+ACP+", cc:"+ correlationCoefficient);
    }



    public double getCorrelationCoefficient(){
        return correlationCoefficient;
    }

    public double getAC() {
        return AC;
    }

    public double getACP() {
        return ACP;
    }

    public double getSensitivity() {
        return sensitivity;
    }

    public double getSpecificity() {
        return specificity;
    }
}
