package ru.otus.jdbc.mapper;


import java.lang.reflect.Field;


public class EntitySQLMetaDataImpl<T> implements EntitySQLMetaData<T> {

    private final EntityClassMetaData<T> entityClassMetaData;

    public EntitySQLMetaDataImpl(EntityClassMetaData<T> entityClassMetaData) {
        this.entityClassMetaData = entityClassMetaData;
    }

    @Override
    public String getSelectAllSql() {
        return String.format("select * from %s", entityClassMetaData.getName());
    }

    @Override
    public String getSelectByIdSql() {
        return String.format("select * from %s where %s = ?",
                entityClassMetaData.getName(), entityClassMetaData.getIdField().getName());
    }

    @Override
    public String getInsertSql(Object object) throws IllegalAccessException {
        StringBuilder columns = new StringBuilder();
        for (Field field : entityClassMetaData.getFieldsWithoutId()) {
            field.setAccessible(true);
            if (field.get(object) != null) {
                columns.append(field.getName()).append(",");
            }
        }
        columns = new StringBuilder(columns.substring(0, columns.length() - 1));
        return String.format("insert into %s(%s) values (?)",
                entityClassMetaData.getName().toLowerCase(), columns);
    }

    @Override
    public String getUpdateSql() {
        StringBuilder setColumns = new StringBuilder();
        for (Field field : entityClassMetaData.getFieldsWithoutId()) {
            setColumns.append(field.getName()).append("%s = ?,");
        }
        setColumns = new StringBuilder(setColumns.substring(0, setColumns.length() - 1));
        return String.format("update %s set %s where %s = ?",
                entityClassMetaData.getName().toLowerCase(), setColumns, entityClassMetaData.getIdField().getName());
    }
}
