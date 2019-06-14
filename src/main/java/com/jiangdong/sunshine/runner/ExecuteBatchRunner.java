package com.jiangdong.sunshine.runner;

import com.jiangdong.sunshine.annotation.Param;
import com.jiangdong.sunshine.annotation.Rollback;
import com.jiangdong.sunshine.exception.SunshineParameterException;
import com.jiangdong.sunshine.exception.SunshineSQLException;
import com.jiangdong.sunshine.result.BaseRowMapper;
import com.jiangdong.sunshine.sql.SqlOperation;
import com.jiangdong.sunshine.util.DBUtils;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.sql.*;
import java.util.List;
import java.util.Map;

public class ExecuteBatchRunner implements SqlOperation {

    @Override
    public Object execute(Object proxy, Method method, Object[] args, String sql) {
        throw new SunshineSQLException("执行了错误的方法,请检查是否选错实现类.");
    }

    @Override
    public Object executeBatch(Object proxy, Method method, Object[] args, String sql, Map<String, Object> params) throws SQLException {
        Parameter[] methodParameters = method.getParameters();
        Connection connection = DBUtils.getConnection();
        Object[][] batchParam = (Object[][]) params.get("batchParam");//实际参数值
        PreparedStatement prepareStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);//获取自增主键
        if (method.getAnnotation(Rollback.class) != null) {
            connection.setAutoCommit(false);
            for (int i = 0; i < batchParam.length; i++) {
                this.fillStatement(prepareStatement, batchParam[i]);
                prepareStatement.addBatch();
            }
            prepareStatement.executeBatch();
            connection.commit();
            return true;
        } else {
            for (Parameter parameter : methodParameters) {
                if (parameter.getAnnotation(Param.class) != null) {
                    for (int i = 0; i < batchParam.length; i++) {
                        this.fillStatement(prepareStatement, batchParam[i]);
                        prepareStatement.addBatch();
                    }
                    return prepareStatement.executeBatch();
                } else {
                    throw new SunshineSQLException("'batchParam' can not be empty!");
                }
            }
        }

        return null;
    }

    private void fillStatement(PreparedStatement prepareStatement, Object[] params) throws SQLException {
        for (int i = 0; i < params.length; i++) {
            if (params[i] != null) {
                prepareStatement.setObject(i + 1, params[i]);
            } else {
                throw new SunshineParameterException("insert batch fail!batchParam can not have empty value!");
            }
        }
    }

    @Override
    public <T> List<T> query(String sql, List<Object> paramsList, BaseRowMapper baseRowMapper) {
        throw new SunshineSQLException("执行了错误的方法,请检查是否选错实现类.");
    }
}
