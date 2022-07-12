package ru.otus.jdbc.mapper;

import ru.otus.core.repository.DataTemplate;
import ru.otus.core.repository.DataTemplateException;
import ru.otus.core.repository.executor.DbExecutor;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Сохратяет объект в базу, читает объект из базы
 */
public class DataTemplateJdbc<T> implements DataTemplate<T> {

    private final DbExecutor dbExecutor;
    private final EntitySQLMetaData<T> entitySQLMetaData;
    private final EntityClassMetaData<T> entityClassMetaData;

    public DataTemplateJdbc(DbExecutor dbExecutor, EntitySQLMetaData<T> entitySQLMetaData, EntityClassMetaData<T> entityClassMetaData) {
        this.dbExecutor = dbExecutor;
        this.entitySQLMetaData = entitySQLMetaData;
        this.entityClassMetaData = entityClassMetaData;
    }

    @Override
    public Optional<T> findById(Connection connection, long id) {
        return dbExecutor.executeSelect(connection, entitySQLMetaData.getSelectByIdSql(), List.of(id),
                rs -> {
                    try {
                        if (rs.next()) {
                            var columnValues = new ArrayList<>();
                            for (Field field : entityClassMetaData.getAllFields()) {
                                var columnIndex = rs.findColumn(field.getName());
                                var value = rs.getObject(columnIndex);
                                columnValues.add(value);
                            }
                            return entityClassMetaData.getConstructor().newInstance(columnValues.toArray());
                        }
                    } catch (SQLException | InstantiationException | IllegalAccessException
                            | InvocationTargetException | NoSuchMethodException e) {
                        throw new DataTemplateException(e);
                    }
                    return null;
                });
    }

    @Override
    public List<T> findAll(Connection connection) {
        return dbExecutor.executeSelect(connection, entitySQLMetaData.getSelectAllSql(), Collections.emptyList(),
                rs -> {
                    var rowList = new ArrayList<T>();
                    try {
                        while (rs.next()) {
                            T row;
                            var fields = new ArrayList<>();
                            for (Field field : entityClassMetaData.getAllFields()) {
                                var columnIndex = rs.findColumn(field.toString());
                                var columnValue = rs.getObject(columnIndex);
                                fields.add(columnValue);
                            }
                            row = entityClassMetaData.getConstructor().newInstance(fields);
                            rowList.add(row);
                        }
                        return rowList;
                    } catch (SQLException | IllegalAccessException | InstantiationException
                            | InvocationTargetException | NoSuchMethodException e) {
                        throw new DataTemplateException(e);
                    }
                }).orElseThrow(() -> new RuntimeException("Unexpected error"));
    }

    @Override
    public long insert(Connection connection, T object) {
        try {
            List<Object> params = new ArrayList<>();
            for (Field field : entityClassMetaData.getFieldsWithoutId()) {
                field.setAccessible(true);
                if (field.get(object) != null) {
                    params.add(field.get(object));
                }
            }
            return dbExecutor.executeStatement(connection, entitySQLMetaData.getInsertSql(object), params);
        } catch (Exception e) {
            throw new DataTemplateException(e);
        }
    }

    @Override
    public void update(Connection connection, T object) {
        try {
            var params = new ArrayList<>();
            for (Field field : entityClassMetaData.getFieldsWithoutId()) {
                params.add(field.get(object));
            }
            dbExecutor.executeStatement(connection, entitySQLMetaData.getUpdateSql(), params);
        } catch (Exception e) {
            throw new DataTemplateException(e);
        }
    }
}
