package com.jiangdong.sunshine.runner;

import com.jiangdong.sunshine.annotation.Rollback;
import com.jiangdong.sunshine.exception.SunshineSQLException;
import com.jiangdong.sunshine.result.BaseRowMapper;
import com.jiangdong.sunshine.sql.SqlOperation;
import com.jiangdong.sunshine.util.DBUtils;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class ExecuteRunner implements SqlOperation {

    /**
     * @param proxy
     * @param method
     * @param args   代理方法的所有参数 目前这么写明显死板且功能不全 后应加入批量操作  可改成将批量数据放到二维数组
     * @param sql
     * @return
     * @throws SQLException
     */
    @Override
    public Object execute(Object proxy, Method method, Object[] args, String sql) throws SQLException {
        Connection connection = DBUtils.getConnection();
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
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                DBUtils.closeConnection(connection);
            }
        }

        return true;
    }

    @Override
    public Object executeBatch(Object proxy, Method method, Object[] args, String sql) throws SQLException {
        throw new SunshineSQLException("执行了错误的错误的方法,请检查是否选错实现类.");
    }

    @Override
    public <T> List<T> query(String sql, List<Object> paramsList, BaseRowMapper baseRowMapper) {
        throw new SunshineSQLException("执行了错误的错误的方法,请检查是否选错实现类.");
    }

}
