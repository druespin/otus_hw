package ru.otus.jdbc.mapper;

import ru.otus.annotation.Id;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

public class EntityClassMetaDataImpl<T> implements EntityClassMetaData<T> {

    private final Class<T> tClass;
    private final Field idField;
    private final List<Field> fieldsList;

    public EntityClassMetaDataImpl(Class<T> tClass) {
        this.tClass = tClass;
        idField = Arrays.stream(tClass.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Id.class))
                .findFirst().get();
        fieldsList = List.of(tClass.getDeclaredFields());
    }

    @Override
    public String getName() {
        var pathName = tClass.getName();
        return pathName.substring(pathName.lastIndexOf(".") + 1).toLowerCase();
    }

    @Override
    public Constructor<T> getConstructor() throws NoSuchMethodException {
        var fieldClasses = fieldsList.stream().map(Field::getType).toArray(Class<?>[]::new);
        return tClass.getConstructor(fieldClasses);
    }

    @Override
    public Field getIdField() {
        return idField;
    }

    @Override
    public List<Field> getAllFields() {
        return fieldsList;
    }

    @Override
    public List<Field> getFieldsWithoutId() {
        return fieldsList.stream()
                .filter(field -> !field.equals(idField)).toList();
    }
}
