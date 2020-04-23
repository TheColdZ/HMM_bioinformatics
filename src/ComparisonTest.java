import org.junit.Before;
import org.junit.Test;

public class ComparisonTest {

    @Before
    public void setup(){};

    @Test
    public void test(){
        Comparison comparison = new Comparison();
        String trueAnnotation = "NNCCNNRRNN";
        String predictionAnnotation = "NCCNNRRNNN";
        comparison.compareSingle(trueAnnotation,predictionAnnotation,'N','C');
        comparison.compareSingle(trueAnnotation,predictionAnnotation,'N','R');
        comparison.compare(trueAnnotation,predictionAnnotation,'N','C','R');

    }


}
