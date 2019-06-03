package com.jiangdong.sunshine.Implement;

import com.jiangdong.sunshine.annotation.Insert;
import com.jiangdong.sunshine.annotation.Rollback;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class InsertFactory {

    public static Connection connection;

    public boolean insertOne(Object proxy, Method method, Object[] args) {
        String sql = method.getAnnotation(Insert.class).sql();
        if (method.getAnnotation(Rollback.class) != null) {
            try {
                connection.setAutoCommit(false);
                PreparedStatement preparedStatement = this.connection.prepareStatement(sql);
                for (int i = 0; i < args.length; i++) {
                    preparedStatement.setObject(i + 1, args[i]);
                }
                Boolean result = preparedStatement.execute();
                if (result) {
                    connection.commit();
                    return true;
                } else {
                    connection.rollback();
                    return false;
                }
            } catch (SQLException e) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                e.printStackTrace();
            }
        } else {
            try {
                PreparedStatement preparedStatement = this.connection.prepareStatement(sql);
                for (int i = 0; i < args.length; i++) {
                    preparedStatement.setObject(i + 1, args[i]);
                }
                return preparedStatement.execute();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return true;
    }

    public Object insertBatch(Object proxy, Method method, Object[] args) {
        String sql = (String) args[0];
        if (method.getAnnotation(Rollback.class) != null) {
            try {
                connection.setAutoCommit(false);
                Statement statement = connection.createStatement();
                statement.execute(sql);
                connection.commit();
            } catch (Exception e) {
                try {
                    connection.rollback();
                    return false;
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }

        } else {
            try {
                Statement statement = connection.createStatement();
                return statement.execute(sql);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return true;
    }
}
