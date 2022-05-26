import proxy.LogInjector;
import test.TestInterface;

public class ClassDemo {

    public static void main(String[] args) {
        TestInterface test = LogInjector.createTest();
        test.calculation(4);
        test.calculation(3, 3);
        test.calculation(3, 3, "33");
    }
}
