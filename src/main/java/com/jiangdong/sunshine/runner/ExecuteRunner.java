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
import java.util.Map;

public class ExecuteRunner implements SqlOperation {

    /**
     * @param proxy
     * @param method
     * @param args
     * @param sql
     * @return
     * @throws SQLException
     */
    @Override
    public Object execute(Object proxy, Method method, Object[] args, String sql, Boolean useCache) {
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
                e.printStackTrace();
                if (connection != null) {
                    try {
                        connection.rollback();
                    } catch (SQLException ex) {
                        throw new SunshineSQLException(e.getMessage() + ",事务回滚异常!", e.getCause());
                    }
                }
            } finally {
                try {
                    DBUtils.closeConnection(connection);
                } catch (SQLException e) {
                    throw new SunshineSQLException(e.getMessage() + ",关闭数据库连接异常!", e.getCause());
                }
            }
        } else {
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                for (int i = 0; i < args.length; i++) {
                    preparedStatement.setObject(i + 1, args[i]);
                }
                return preparedStatement.execute();
            } catch (Exception e) {
                throw new SunshineSQLException(e.getMessage(), e.getCause());
            } finally {
                try {
                    DBUtils.closeConnection(connection);
                } catch (SQLException e) {
                    throw new SunshineSQLException(e.getMessage() + ",关闭数据库连接异常!", e.getCause());
                }
            }
        }

        return true;
    }

    @Override
    public Object execute(Object proxy, Method method, Object[] args, String sql) {
        return execute(proxy, method, args, sql, false);
    }

    @Override
    public Object executeBatch(Object proxy, Method method, Object[] args, String sql, Map<String, Object> params) {
        throw new SunshineSQLException("执行了错误的方法,请检查是否选错实现类.");
    }

    @Override
    public <T> List<T> query(String sql, List<Object> paramsList, BaseRowMapper<T> baseRowMapper, Boolean useCache) {
        throw new SunshineSQLException("执行了错误的方法,请检查是否选错实现类.");
    }

}
