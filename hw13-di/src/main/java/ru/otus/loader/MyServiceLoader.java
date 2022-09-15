package ru.otus.loader;

import ru.otus.exception.AppComponentException;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class MyServiceLoader {

    private static final List<String> serviceList = new ArrayList<>();

    public static Class<?> loadImplClass(Class<?> clazz) throws ClassNotFoundException {
        for (String serviceName : getServicesList()) {
            var serviceClass = Class.forName(getServicePath(serviceName));
            if (!serviceClass.isInterface()
                    && !serviceName.equals(clazz.getName())
                    && clazz.isAssignableFrom(serviceClass)) {
                return serviceClass;
            }
        }
        throw new AppComponentException("No services found");
    }

    private static List<String> getServicesList() {
        var servicesDir = new File("hw13-di/src/main/java/ru/otus/services");
        var servicesNames = Objects.requireNonNull(servicesDir.list());
        serviceList.addAll(Arrays.asList(servicesNames));
        return serviceList;
    }

    private static String getServicePath(String serviceName) {
        return  "ru.otus.services." + serviceName.substring(0, serviceName.indexOf(".java"));
    }
}
