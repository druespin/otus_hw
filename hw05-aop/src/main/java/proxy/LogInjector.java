package proxy;

import annotation.Log;
import test.TestInterface;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class LogInjector {

    public static TestInterface createTest(TestInterface testInterface) {
        InvocationHandler handler = new LogInvocationHandler(testInterface);
        return (TestInterface) Proxy.newProxyInstance(LogInjector.class.getClassLoader(),
                new Class<?>[]{TestInterface.class}, handler);
    }

    static class LogInvocationHandler implements InvocationHandler {

        private final TestInterface testInterface;

        LogInvocationHandler(TestInterface testInterface) {
            this.testInterface = testInterface;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            var methodOfImpl = testInterface.getClass().getMethod(method.getName(), method.getParameterTypes());
            if (methodOfImpl.isAnnotationPresent(Log.class)) {
                System.out.print("executed method: " + method.getName() + ", params: ");
                if (args != null) {
                    for (Object arg : args) {
                        System.out.print(arg + " ");
                    }
                }
                else System.out.print("no params");
                System.out.println();
            }
            return method.invoke(testInterface, args);
        }
    }
}
