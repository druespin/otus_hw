
import runner.TestRunner;

public class Main {

    public static void main(String[] args) throws Exception {

        Class<?> testClass = Class.forName("test_classes.AnnotationTest2");
        TestRunner.runTests(testClass);
    }
}
