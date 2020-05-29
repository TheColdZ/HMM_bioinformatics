package Test;
import Main.Experimentation.Comparison;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Jens Kristian Refsgaard Nielsen & Thomas Damgaard Vinther
 */
public class ComparisonTest {
    private Comparison comparison;
    private String trueAnnotation;
    private String predictionAnnotation;

    @Before
    public void setup(){
        comparison = new Comparison();
        trueAnnotation = "NNCCNNRRNN";
        predictionAnnotation = "NCCNNRRNNN";
    };

    private boolean compareFactor(double n1, double n2, double factor){
        double standardDeviation = factor;
        return n2-standardDeviation <= n1 && n1 <= n2+standardDeviation;
    }
    @Test
    public void testSingleCharC() {
        comparison.compareSingle(trueAnnotation, predictionAnnotation, 'N', 'C');
        assert (compareFactor(0.149, comparison.getCorrelationCoefficient(), 0.001));
        assert (compareFactor(0.575, comparison.getACP(), 0.001));
        assert (compareFactor(0.150, comparison.getAC(), 0.001));
        assert (compareFactor(0.333333, comparison.getSensitivity(), 0.001));
        assert (compareFactor(0.5, comparison.getSpecificity(), 0.001));
    }
    @Test
    public void testSinglecharR(){
        comparison.compareSingle(trueAnnotation, predictionAnnotation, 'N', 'R');
        assert (compareFactor(0.149, comparison.getCorrelationCoefficient(), 0.001));
        assert (compareFactor(0.575, comparison.getACP(), 0.001));
        assert (compareFactor(0.150, comparison.getAC(), 0.001));
        assert (compareFactor(0.333333, comparison.getSensitivity(), 0.001));
        assert (compareFactor(0.5, comparison.getSpecificity(), 0.001));
    }

    @Test
    public void testTwoChar(){
        comparison.compare(trueAnnotation,predictionAnnotation,'N','C','R');
        assert(compareFactor(0.166,comparison.getCorrelationCoefficient(),0.001));
        assert(compareFactor(0.583,comparison.getACP(),0.001));
        assert(compareFactor(0.166667,comparison.getAC(),0.001));
        assert(compareFactor(0.5,comparison.getSensitivity(),0.001));
        assert(compareFactor(0.5,comparison.getSpecificity(),0.001));
    }

    @Test
    public void testOneInTwo(){
        comparison.compare(trueAnnotation,predictionAnnotation,'N','_','C');
        assert (compareFactor(0.149, comparison.getCorrelationCoefficient(), 0.001));
        assert (compareFactor(0.575, comparison.getACP(), 0.001));
        assert (compareFactor(0.150, comparison.getAC(), 0.001));
        assert (compareFactor(0.333333, comparison.getSensitivity(), 0.001));
        assert (compareFactor(0.5, comparison.getSpecificity(), 0.001));
    }




}
