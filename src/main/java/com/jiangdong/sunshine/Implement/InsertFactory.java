package com.jiangdong.sunshine.Implement;

import com.jiangdong.sunshine.annotation.Insert;
import com.jiangdong.sunshine.annotation.Rollback;
import com.jiangdong.sunshine.util.DBUtils;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class InsertFactory {

    public boolean insertOne(Object proxy, Method method, Object[] args) throws SQLException {
        Connection connection = DBUtils.getConnection();
        String sql = method.getAnnotation(Insert.class).sql();
        if (method.getAnnotation(Rollback.class) != null) {
            try {
                connection.setAutoCommit(false);
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                for (int i = 0; i < args.length; i++) {
                    preparedStatement.setObject(i + 1, args[i]);
                }
                preparedStatement.execute();
                connection.commit();
                return true;
            } catch (SQLException e) {
                connection.rollback();
                e.printStackTrace();
            } finally {
                DBUtils.closeConnection(connection);
            }
        } else {
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                for (int i = 0; i < args.length; i++) {
                    preparedStatement.setObject(i + 1, args[i]);
                }
                return preparedStatement.execute();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                DBUtils.closeConnection(connection);
            }
        }

        return true;
    }

    public Object insertBatch(Object proxy, Method method, Object[] args, String sql) throws SQLException {
        Connection connection = DBUtils.getConnection();
        if (method.getAnnotation(Rollback.class) != null) {
            try {
                connection.setAutoCommit(false);
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.execute(sql);
                connection.commit();
                return true;
            } catch (Exception e) {
                connection.rollback();
                return false;
            } finally {
                DBUtils.closeConnection(connection);
            }

        } else {
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                return preparedStatement.execute(sql);
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                DBUtils.closeConnection(connection);
            }
        }

        return true;
    }
}
