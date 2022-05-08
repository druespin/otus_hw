package runner;

import annotations.After;
import annotations.Before;
import annotations.Test;

import java.lang.reflect.Method;

public class TestRunner {

    private final Class<?> testClass;
    private final Object testInstance;

    public TestRunner(String testName) throws Exception {
        testClass = Class.forName(testName);
        testInstance = testClass.getConstructor().newInstance();
    }

    public boolean invokeBeforeMethod() {
        for (Method method : testClass.getDeclaredMethods()) {
            if (method.isAnnotationPresent(Before.class)) {
                try {
                    method.invoke(testInstance);
                } catch (Exception e) {
                    System.out.println("Failed: " + String.format("%s in method %s\n", e.getCause(), method.getName()));
                    return false;
                }
            }
        }
        return true;
    }

    public void invokeTestMethodAndGetPassedAmount() {
        int passed = 0;
        int total = 0;
        StringBuilder exception = new StringBuilder();

        for (Method method : testClass.getDeclaredMethods()) {
            if (method.isAnnotationPresent(Test.class)) {
                try {
                    total++;
                    method.invoke(testInstance);
                    System.out.println("Passed");
                    passed++;
                }
                catch (Exception e) {
                    System.out.println("Failed");
                    exception.append(String.format("%s in method %s\n", e.getCause(), method.getName()));
                }
            }
        }
        printStats(total, passed, total - passed, exception.toString());
    }

    public void invokeAfterMethod() {
        for (Method method : testClass.getDeclaredMethods()) {
            if (method.isAnnotationPresent(After.class)) {
                try {
                    method.invoke(testInstance);
                } catch (Exception e) {
                    System.out.println("Failed: " + String.format("%s in method %s\n", e.getCause(), method.getName()));
                }
            }
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
    }}
