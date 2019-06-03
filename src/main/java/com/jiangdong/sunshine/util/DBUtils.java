package com.jiangdong.sunshine.util;

import com.jiangdong.sunshine.config.DBInit;

import java.sql.Connection;
import java.sql.SQLException;

public class DBUtils {

    public static DBInit dbInit = DBInit.getDBInit();

    public static void closeConnection(Connection connection) throws SQLException {
        connection.close();
    }
}
