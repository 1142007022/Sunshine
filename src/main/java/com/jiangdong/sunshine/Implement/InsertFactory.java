package com.jiangdong.sunshine.Implement;

import com.jiangdong.sunshine.annotation.Insert;
import com.jiangdong.sunshine.annotation.Rollback;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InsertFactory {

    public static Connection connection;

    public boolean insertOne(Object proxy, Method method, Object[] args) {
        String sql = method.getAnnotation(Insert.class).sql();
        if (method.getAnnotation(Rollback.class) != null) {
            try {
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
                connection.setAutoCommit(true);
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
        return false;
    }
}
