package com.service.apiservice.utils;

import org.slf4j.Logger;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DBUtils {
    private DBUtils() {
    }

    public static void closeConnection(CallableStatement callableStatement, Connection connection, Logger log) {
        try {
            if (callableStatement != null) {
                callableStatement.close();
            }
        } catch (SQLException ex) {
            log.error(ex.getMessage(), ex);
        }
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException ex) {
            log.error(ex.getMessage(), ex);
        }
    }

    /**
     * NOTE: Cần chú ý đối với các trường kiểu Number(0,1) trong DataBase
     * Với những trường khác tên cần set bằng cách thông thường
     */
    public static <T> T passDBDataToObject(T object, ResultSet resultSet, Class<T> clazz, Logger log) {
        Field[] fields = clazz.getDeclaredFields();
        try {
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            List<String> columnNames = new ArrayList<>();
            for (int i = 1; i <= resultSetMetaData.getColumnCount(); i++) {
                columnNames.add(resultSetMetaData.getColumnName(i).toLowerCase());
            }
            for (Field field : fields) {
                field.setAccessible(true);
                if (columnNames.contains(field.getName().toLowerCase())) {
                    if (field.getType().equals(Integer.class)) {
                        field.set(object, resultSet.getInt(field.getName()));
                    } else if (field.getType().equals(Long.class)) {
                        field.set(object, resultSet.getLong(field.getName()));
                    } else if (field.getType().equals(BigDecimal.class)) {
                        field.set(object, resultSet.getBigDecimal(field.getName()));
                    } else if (field.getType().equals(String.class)) {
                        field.set(object, resultSet.getString(field.getName()) == null ? null : resultSet.getString(field.getName()).trim());
                    } else if (field.getType().equals(Date.class)) {
                        field.set(object, resultSet.getDate(field.getName()));
                    } else if (field.getType().equals(Boolean.class)) {
                        field.set(object, resultSet.getBoolean(field.getName()));
                    }
                }
            }
        } catch (Exception e) {
            log.error("Error when getting data column from db");
            throw new RuntimeException(e);
        }
        return object;
    }

    public static void rollback(Connection connection, Logger log) {
        if (connection != null) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                log.error(e.getMessage(), e);
            } finally {
                DBUtils.closeConnection(null, connection, log);
            }
        }
    }
}
