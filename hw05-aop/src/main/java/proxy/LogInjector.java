package proxy;

import annotation.Log;
import test.TestInterface;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class LogInjector {

    public static TestInterface createTest(TestInterface testInterface) {
        InvocationHandler handler = new LogInvocationHandler(testInterface);
        return (TestInterface) Proxy.newProxyInstance(LogInjector.class.getClassLoader(),
                new Class<?>[]{TestInterface.class}, handler);
    }

    static class LogInvocationHandler implements InvocationHandler {

        private final TestInterface testInterface;
        private final Set<String> testMethods = new HashSet<>();

        LogInvocationHandler(TestInterface testInterface) {
            this.testInterface = testInterface;
            for (Method method : testInterface.getClass().getMethods()) {
                if (method.isAnnotationPresent(Log.class)) {
                    this.testMethods.add(getSignature(method));
                }
            }
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (testMethods.contains(getSignature(method))) {
                System.out.print("executed method: " + method.getName() + ", params: ");
                if (args != null) {
                    for (Object arg : args) {
                        System.out.print(arg + " ");
                    }
                } else System.out.print("no params");
                System.out.println();
            }
            return method.invoke(testInterface, args);
        }

        private String getSignature(Method method) {
            return method.getName() + Arrays.toString(method.getParameterTypes());
        }
    }
}
