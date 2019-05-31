package com.jiangdong.sunshine.Implement;

import com.jiangdong.sunshine.annotation.Insert;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InsertFactory {

    public static Connection connection;

    public boolean insertOne(Object proxy, Method method, Object[] args) throws SQLException {
        String sql = method.getAnnotation(Insert.class).sql();
        PreparedStatement preparedStatement = this.connection.prepareStatement(sql);
        for (int i = 0; i < args.length; i++) {
            preparedStatement.setObject(i + 1, args[i]);
        }
        return preparedStatement.execute();
    }


    public Object insertBatch(Object proxy, Method method, Object[] args) {
        return false;
    }
}
