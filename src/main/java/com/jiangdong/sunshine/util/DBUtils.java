package com.jiangdong.sunshine.util;

import com.jiangdong.sunshine.config.DBInit;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBUtils {

    private static DBInit dbInit = DBInit.getDBInit();

    public static Connection getConnection() {
        return dbInit.getConnection();
    }

    public static void closeConnection(Connection connection) throws SQLException {
        connection.close();
    }

    public static void closeResultSet(ResultSet resultSet) throws SQLException {
        if (resultSet != null) {
            resultSet.close();
        }
    }

    public static void closeStatement(Statement statement) throws SQLException {
        if (statement != null) {
            statement.close();
        }
    }

    public static void connectionCommitAndClose(Connection connection) throws SQLException {
        if (connection != null) {
            try {
                connection.commit();
            } finally {
                connection.close();
            }
        }
    }

}
