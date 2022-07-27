package ru.otus.jdbc.mapper;


import java.lang.reflect.Field;
import java.util.List;


public class EntitySQLMetaDataImpl<T> implements EntitySQLMetaData<T> {

    private final EntityClassMetaData<T> entityClassMetaData;
    private final String tableName;
    private final String idField;
    private final List<Field> fieldsWithoutId;


    public EntitySQLMetaDataImpl(EntityClassMetaData<T> entityClassMetaData) {
        this.entityClassMetaData = entityClassMetaData;
        this.tableName = entityClassMetaData.getName();
        this.idField = entityClassMetaData.getIdField().getName();
        this.fieldsWithoutId = entityClassMetaData.getFieldsWithoutId();
    }

    @Override
    public String getSelectAllSql() {
        return String.format("select * from %s", tableName);
    }

    @Override
    public String getSelectByIdSql() {
        return String.format("select * from %s where %s = ?", tableName, idField);
    }

    @Override
    public String getInsertSql(Object object) throws IllegalAccessException {
        StringBuilder columns = new StringBuilder();
        for (Field field : fieldsWithoutId) {
            field.setAccessible(true);
            if (field.get(object) != null) {
                columns.append(field.getName()).append(",");
            }
        }
        columns = new StringBuilder(columns.substring(0, columns.length() - 1));
        return String.format("insert into %s(%s) values (?)", tableName.toLowerCase(), columns);
    }

    @Override
    public String getUpdateSql() {
        StringBuilder setColumns = new StringBuilder();
        for (Field field : fieldsWithoutId) {
            setColumns.append(field.getName()).append("%s = ?,");
        }
        setColumns = new StringBuilder(setColumns.substring(0, setColumns.length() - 1));
        return String.format("update %s set %s where %s = ?",
                tableName.toLowerCase(), setColumns, idField);
    }
}
