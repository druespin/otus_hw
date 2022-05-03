import annotations.After;
import annotations.Before;
import annotations.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;


public class Runner {

    private final Class<?> testClass;
    private final Object testInstance;

    Runner(String testName) throws Exception {
        testClass = Class.forName(testName);
        testInstance = testClass.getConstructor().newInstance();
    }

    public void runTestClass() {
        StringBuilder exceptions = new StringBuilder();

        int passed = isMethodPassed(testInstance, Before.class, exceptions) +
                    isMethodPassed(testInstance, Test.class, exceptions) +
                    isMethodPassed(testInstance, After.class, exceptions);

        int total = testClass.getDeclaredMethods().length;
        int failed = total - passed;

        printStats(total, passed, failed, exceptions.toString());

    }

    private static int isMethodPassed(Object test, Class<? extends Annotation> annotation, StringBuilder exception) {
        try {
            for (Method method : test.getClass().getDeclaredMethods()) {
                if (method.isAnnotationPresent(annotation)) {
                    method.invoke(test);
                }
            }
            System.out.println("Passed");
            return 1;
        }
        catch (Exception e) {
            System.out.println("Failed");
            exception.append(String.format("%s in method %s\n", e.getCause(), annotation.getName()));
            return 0;
        }
    }

    private static void printStats(int total, int passed, int failed, String exceptions) {
        System.out.println(
                "\nREPORT:" +
                "\nTests total: " + total +
                "\nTests passed: " + passed +
                "\nTests failed: " + failed
        );
        System.out.println("\nFailed tests:\n" + exceptions);
    }
}
