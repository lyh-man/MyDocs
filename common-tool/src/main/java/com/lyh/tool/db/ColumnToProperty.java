package com.lyh.tool.db;

import com.lyh.tool.core.collection.map.ColumnToPropertyMap;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

/**
 * 通过 反射 实现 从 "DB 字段" 映射到 "Class 的 成员变量"
 *
 * 其中，类型转换支持
 *    基本类型、包装类型、java.util.Date、java.sql.Timestamp
 */
public class ColumnToProperty {
    /**
     * 从 "DB 字段" 映射到 "Class 的 成员变量"
     */
    public static <T> T mapColumnToProperty(ResultSet rs, Class<T> clazz, ColumnToPropertyMap columnToPropertyMap) {
        try {
            T result = clazz.getConstructor().newInstance();
            ResultSetMetaData metaData = rs.getMetaData();
            for (int index = 1; index <= metaData.getColumnCount(); index++) {
                String column = metaData.getColumnName(index);
                String property = columnToPropertyMap.getValue(column);
                if (property == null) {
                    continue;
                } else {
                    Field field = clazz.getDeclaredField(property);
                    field.setAccessible(true);
                    field.set(result, autoConversionType(rs, index, field.getType()));
                }
            }
            return result;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 类型转换 （基本类型、包装类型、java.util.Date、java.sql.Timestamp）
     */
    private static Object autoConversionType(ResultSet rs, int index, Class<?> type) throws SQLException {
        if (!type.isPrimitive() && rs.getObject(index) == null) {
            return null;
        }
        if (type.equals(Byte.class) || type.equals(Byte.TYPE)) {
            return rs.getByte(index);
        }
        if (type.equals(Short.class) || type.equals(Short.TYPE)) {
            return rs.getShort(index);
        }
        if (type.equals(Integer.class) || type.equals(Integer.TYPE)) {
            return rs.getInt(index);
        }
        if (type.equals(Float.class) || type.equals(Float.TYPE)) {
            return rs.getFloat(index);
        }
        if (type.equals(Double.class) || type.equals(Double.TYPE)) {
            return rs.getDouble(index);
        }
        if (type.equals(Long.class) || type.equals(Long.TYPE)) {
            return rs.getLong(index);
        }
        if (type.equals(Boolean.class) || type.equals(Boolean.TYPE)) {
            return rs.getBoolean(index);
        }
        if (type.equals(String.class)) {
            return rs.getString(index);
        }
        if (type.equals(Date.class)) {
            return rs.getDate(index);
        }
        if (type.equals(Timestamp.class)) {
            return rs.getTimestamp(index);
        }
        return null;
    }
}
