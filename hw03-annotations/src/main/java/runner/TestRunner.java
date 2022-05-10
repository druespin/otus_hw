package runner;

import annotations.After;
import annotations.Before;
import annotations.Test;

import java.lang.reflect.Method;

public class TestRunner {

    public static boolean invokeBeforeMethod(Class<?> testClass) {
        for (Method method : testClass.getDeclaredMethods()) {
            if (method.isAnnotationPresent(Before.class)) {
                try {
                    method.invoke(testClass.getConstructor().newInstance());
                } catch (Exception e) {
                    System.out.println("Failed: " + String.format("%s in method %s\n", e.getCause(), method.getName()));
                    return false;
                }
            }
        }
        return true;
    }

    public static void invokeTestMethods(Class<?> testClass) {
        int passed = 0;
        int total = 0;

        for (Method method : testClass.getDeclaredMethods()) {
            if (method.isAnnotationPresent(Test.class)) {
                try {
                    total++;
                    method.invoke(testClass.getConstructor().newInstance());
                    System.out.println("Passed");
                    passed++;
                }
                catch (Exception e) {
                    System.out.println("Failed: " + String.format("%s in method %s", e.getCause(), method.getName()));
                }
            }
        }
        printStats(total, passed);
    }

    public static void invokeAfterMethod(Class<?> testClass) {
        for (Method method : testClass.getDeclaredMethods()) {
            if (method.isAnnotationPresent(After.class)) {
                try {
                    method.invoke(testClass.getConstructor().newInstance());
                } catch (Exception e) {
                    System.out.println("Failed: " + String.format("%s in method %s\n", e.getCause(), method.getName()));
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
    }}
