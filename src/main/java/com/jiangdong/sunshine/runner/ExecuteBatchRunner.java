package com.jiangdong.sunshine.runner;

import com.jiangdong.sunshine.annotation.Rollback;
import com.jiangdong.sunshine.exception.SunshineParameterException;
import com.jiangdong.sunshine.exception.SunshineSQLException;
import com.jiangdong.sunshine.result.BaseRowMapper;
import com.jiangdong.sunshine.sql.SqlOperation;
import com.jiangdong.sunshine.util.DBUtils;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

public class ExecuteBatchRunner implements SqlOperation {

    @Override
    public Object execute(Object proxy, Method method, Object[] args, String sql, Boolean useCache) {
        throw new SunshineSQLException("执行了错误的方法,请检查是否选错实现类.");
    }

    @Override
    public Object execute(Object proxy, Method method, Object[] args, String sql) {
        throw new SunshineSQLException("执行了错误的方法,请检查是否选错实现类.");
    }

    @Override
    public Object executeBatch(Object proxy, Method method, Object[] args, String sql, Map<String, Object> params) {
        Connection connection = DBUtils.getConnection();
        Object[][] batchParam = (Object[][]) params.get("batchParam");//实际参数值
        PreparedStatement prepareStatement = null;//获取自增主键
        try {
            prepareStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        } catch (SQLException e) {
            throw new SunshineSQLException(e.getMessage(), e.getCause());
        }
        if (method.getAnnotation(Rollback.class) != null) {
            try {
                connection.setAutoCommit(false);
                executeBatch(batchParam, prepareStatement);
                connection.commit();
                return true;
            } catch (Exception e) {
                Rollback rollback = method.getAnnotation(Rollback.class);
                if (connection != null && rollback.rollBackFor().isAssignableFrom(e.getClass())) {
                    try {
                        connection.rollback();
                    } catch (SQLException ex) {
                        throw new SunshineSQLException(e.getMessage() + "事务回滚异常!", e.getCause());
                    }
                }
            } finally {
                try {
                    DBUtils.closeConnection(connection);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            return true;
        } else {
            try {
                return executeBatch(batchParam, prepareStatement);
            } catch (SQLException e) {
                throw new SunshineSQLException(e.getMessage(), e.getCause());
            } finally {
                try {
                    DBUtils.closeConnection(connection);
                } catch (SQLException e) {
                    throw new SunshineSQLException(e.getMessage() + ",关闭数据库连接异常!", e.getCause());
                }
            }
        }

    }

    private Object executeBatch(Object[][] batchParam, PreparedStatement prepareStatement) throws SQLException {
        for (int i = 0; i < batchParam.length; i++) {
            this.fillStatement(prepareStatement, batchParam[i]);
            prepareStatement.addBatch();
        }
        return prepareStatement.executeBatch();
    }

    private void fillStatement(PreparedStatement prepareStatement, Object[] params) throws SQLException {
        if (params != null) {
            for (int i = 0; i < params.length; i++) {
                prepareStatement.setObject(i + 1, params[i]);
            }
        } else {
            throw new SunshineParameterException("insert batch fail!'batchParam' can not have empty value!");
        }
    }


    @Override
    public <T> List<T> query(String sql, List<Object> paramsList, BaseRowMapper<T> baseRowMapper, Boolean useCache) {
        throw new SunshineSQLException("执行了错误的方法,请检查是否选错实现类.");
    }
}
