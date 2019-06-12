package com.jiangdong.sunshine.Implement;

import com.jiangdong.sunshine.annotation.Delete;
import com.jiangdong.sunshine.util.DBUtils;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteFactory {

    public Object delete(Method method, Object[] args) throws SQLException {
        Connection connection = DBUtils.getConnection();
        try {
            String sql = method.getAnnotation(Delete.class).sql();
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
        return null;
    }

}
