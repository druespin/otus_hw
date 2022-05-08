
import runner.TestRunner;

public class Main {

    public static void main(String[] args) throws Exception {

        TestRunner testRunner = new TestRunner("test_classes.AnnotationTest2");

        if (testRunner.invokeBeforeMethod()) {
            testRunner.invokeTestMethodAndGetPassedAmount();
            testRunner.invokeAfterMethod();
        }
    }
}
