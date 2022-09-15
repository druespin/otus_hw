package ru.otus.appcontainer;

import ru.otus.appcontainer.api.AppComponent;
import ru.otus.appcontainer.api.AppComponentsContainer;
import ru.otus.appcontainer.api.AppComponentsContainerConfig;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import ru.otus.config.AppConfig;
import ru.otus.exception.AppComponentException;

import java.util.*;

public class AppComponentsContainerImpl implements AppComponentsContainer {

    private final AppConfig appConfig = new AppConfig();
    private final Set<Object> appComponentInstanceSet = new HashSet<>();
    private final Map<String, Object> appComponentsMap = new HashMap<>();

    public AppComponentsContainerImpl(Class<?> initialConfigClass) {
        processConfig(initialConfigClass);
    }

    private void processConfig(Class<?> configClass) {
        checkConfigClass(configClass);
        try {
            var appConfigMethods = getAppConfigMethodsOrdered(configClass);
            for (Method method : appConfigMethods) {
                var instance = method.invoke(appConfig, new Object[method.getParameterCount()]);

                for (Field field : instance.getClass().getDeclaredFields()) {
                    if (appComponentsMap.containsKey(field.getName())) {
                        field.setAccessible(true);
                        field.set(instance, appComponentsMap.get(field.getName()));
                    }
                }
                appComponentInstanceSet.add(instance);
                appComponentsMap.put(method.getName(), instance);
            }
        }
        catch (IllegalAccessException | InvocationTargetException e) {
            throw new AppComponentException(e.getMessage());
        }
    }

    private void checkConfigClass(Class<?> configClass) {
        if (!configClass.isAnnotationPresent(AppComponentsContainerConfig.class)) {
            throw new IllegalArgumentException(String.format("Given class is not config %s", configClass.getName()));
        }
    }

    @Override
    public <C> C getAppComponent(Class<C> clazz) {
        for (Object component : appComponentInstanceSet) {
            if (clazz.equals(component.getClass()) ||
                    clazz.isAssignableFrom(component.getClass())) {
                return (C) component;
            }
        }
        return null;
    }

    @Override
    public <C> C getAppComponent(String componentName) {
        if (appComponentsMap.containsKey(componentName)) {
            return (C) appComponentsMap.get(componentName);
        }
        return null;
    }

    private Set<Method> getAppConfigMethodsOrdered(Class<?> configClass) {
        Set<Method> appConfigMethods = new TreeSet<>(
                (o1, o2) -> {
                    if (o1.getAnnotation(AppComponent.class).order()
                            <= o2.getAnnotation(AppComponent.class).order()) {
                        return -1;
                    }
                    else return 1;
                });
        Arrays.stream(configClass.getDeclaredMethods()).forEach(
                method -> {
                    if (method.isAnnotationPresent(AppComponent.class)) {
                        appConfigMethods.add(method);
                    }
                });
        return appConfigMethods;
    }
}