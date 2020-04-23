/**
 * This class will handle comparison of two strings. Using techniques descrribed
 */
public class Comparison {

    public void compareSingle(String trueAnnotation, String predictionAnnotation, char nonCodingChar, char comparisonChar){
        double truePositive,falsePositive,trueNegative,falseNegative;
        truePositive = 0;
        falsePositive = 0;
        trueNegative = 0;
        falseNegative = 0;
        for (int i = 0; i < predictionAnnotation.length(); i++) {
            if( predictionAnnotation.charAt(i) == comparisonChar){      //Char can be compared like this, it is fast TODO
                if(trueAnnotation.charAt(i) == comparisonChar){
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
        print(' ',comparisonChar,truePositive,falsePositive,trueNegative,falseNegative);
        calculate(truePositive,falsePositive,trueNegative,falseNegative);

    }
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
        print(comparisonChar1,comparisonChar2,truePositive,falsePositive,trueNegative,falseNegative);
        calculate(truePositive,falsePositive,trueNegative,falseNegative);

    }
    private void print(char comparisonChar1, char comparisonChar2,double tp, double fp, double tn, double fn){
        System.out.print(comparisonChar1+""+comparisonChar2+"s: tp="+tp+", fp="+fp+", tn="+tn+",fn="+fn);
    }


    private void calculate(double truePositive, double falsePositive, double trueNegative, double falseNegative) {
        double numerator = truePositive*trueNegative-falseNegative*falsePositive;
        double denominator = (truePositive+falseNegative)*(trueNegative+falsePositive)*(truePositive+falsePositive)*(trueNegative+falseNegative);
        double squareRootDenominator = Math.sqrt(denominator);
        double correlationCoefficient = numerator/squareRootDenominator;

        double sensitivity = truePositive/(truePositive+falseNegative);
        double specificity = truePositive/(truePositive+falsePositive);
        double ACP = 0.25*((truePositive/(truePositive+falseNegative)+(truePositive/(truePositive+falsePositive))+(trueNegative/(trueNegative+falsePositive))+(trueNegative/(trueNegative+falseNegative))));
        double AC = (ACP - 0.5)*2;
        System.out.println(" Sensitivity ="+sensitivity+", specificity ="+specificity+", AC="+AC+" ACP:"+ACP);
    }



}
