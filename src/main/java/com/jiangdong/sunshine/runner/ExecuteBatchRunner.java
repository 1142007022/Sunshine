package com.jiangdong.sunshine.runner;

import com.jiangdong.sunshine.exception.SunshineSQLException;
import com.jiangdong.sunshine.result.BaseRowMapper;
import com.jiangdong.sunshine.sql.SqlOperation;

import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.List;

public class ExecuteBatchRunner implements SqlOperation {
    @Override
    public Object execute(Object proxy, Method method, Object[] args, String sql) throws SQLException {
        throw new SunshineSQLException("执行了错误的方法,请检查是否选错实现类.");
    }

    @Override
    public Object executeBatch(Object proxy, Method method, Object[] args, String sql) throws SQLException {
        return null;
    }

    @Override
    public <T> List<T> query(String sql, List<Object> paramsList, BaseRowMapper baseRowMapper) throws SQLException {
        throw new SunshineSQLException("执行了错误的方法,请检查是否选错实现类.");
    }
}