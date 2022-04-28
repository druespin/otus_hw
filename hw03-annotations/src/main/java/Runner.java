import annotations.After;
import annotations.Before;
import annotations.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;


public class Runner {

    public static void main(String[] args) throws Exception {

        Class<?> testClass = Class.forName("testing.AnnotationTest");
        Constructor<?> constructor = testClass.getConstructor();
        Object testInstance = constructor.newInstance();

        int passed = 0;
        if (runBeforeMethod(testInstance)) passed += 1;
        if (runTestMethod(testInstance)) passed += 1;
        if (runAfterMethod(testInstance)) passed += 1;

        int total = testClass.getDeclaredMethods().length;
        int failed = total - passed;
        System.out.println(
                "\nREPORT:" +
                "\nTests total: " + total +
                "\nTests passed: " + passed +
                "\nTests failed: " + failed);
    }

    private static boolean runBeforeMethod(Object test) {
        return getMethodByAnnotationAndInvoke(test, Before.class);
    }

    private static boolean runTestMethod(Object test) {
        return getMethodByAnnotationAndInvoke(test, Test.class);
    }

    private static boolean runAfterMethod(Object test) {
        return getMethodByAnnotationAndInvoke(test, After.class);
    }

    private static boolean getMethodByAnnotationAndInvoke(Object test, Class<? extends Annotation> annotation) {
        try {
            for (Method method : test.getClass().getDeclaredMethods()) {
                if (method.isAnnotationPresent(annotation)) {
                    method.invoke(test);
                }
            }
            return true;
        }
        catch (Exception e) {
            System.out.printf("Failure: %s in method %s\n", e.getCause(), annotation.getName());
            return false;
        }
    }
}
