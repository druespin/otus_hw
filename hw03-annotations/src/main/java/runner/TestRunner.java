package runner;

import annotations.After;
import annotations.Before;
import annotations.Test;

import java.lang.reflect.Method;

public class TestRunner {

    private static boolean invokeBeforeMethod(Object testInstance) {
        for (Method method : testInstance.getClass().getDeclaredMethods()) {
            if (method.isAnnotationPresent(Before.class)) {
                try {
                    method.invoke(testInstance);
                } catch (Exception e) {
                    printException(e, method);
                    return false;
                }
            }
        }
        return true;
    }

    public static void runTests(Class<?> testClass) throws Exception {
        int passed = 0;
        int total = 0;

        for (Method method : testClass.getDeclaredMethods()) {
            if (method.isAnnotationPresent(Test.class)) {
                Object testInstance = testClass.getConstructor().newInstance();
                total++;
                if (invokeBeforeMethod(testInstance)) {
                    try {
                        method.invoke(testInstance);
                        System.out.println("Passed");
                        passed++;
                    }
                    catch (Exception e) {
                        printException(e, method);
                    }
                }
                invokeAfterMethod(testInstance);
            }
        }
        printStats(total, passed);
    }

    private static void invokeAfterMethod(Object testInstance) {
        for (Method method : testInstance.getClass().getDeclaredMethods()) {
            if (method.isAnnotationPresent(After.class)) {
                try {
                    method.invoke(testInstance);
                } catch (Exception e) {
                    printException(e, method);
                }
            }
        }
    }

    private static void printStats(int total, int passed) {
        System.out.println(
                "\nREPORT:" +
                        "\nTests total: " + total +
                        "\nTests passed: " + passed +
                        "\nTests failed: " + (total - passed)
        );
    }

    private static void printException(Exception e, Method method) {
        System.out.println("Failed: " + String.format("%s in method %s", e.getCause(), method.getName()));
    }
}
