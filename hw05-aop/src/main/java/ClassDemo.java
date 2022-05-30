import proxy.LogInjector;
import test.TestInterface;
import test.TestLogging;

public class ClassDemo {

    public static void main(String[] args) {
        TestInterface test = LogInjector.createTest(new TestLogging());
        test.calculation();
        test.calculation(1);
        test.calculation(2, 2);
        test.calculation(3, 3, "33");
    }
}
