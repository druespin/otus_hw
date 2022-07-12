package ru.otus.jdbc.mapper;

/**
 * Создает SQL - запросы
 */
public interface EntitySQLMetaData<T> {
    String getSelectAllSql();

    String getSelectByIdSql();

    String getInsertSql(T object) throws NoSuchMethodException, IllegalAccessException;

    String getUpdateSql();
}
