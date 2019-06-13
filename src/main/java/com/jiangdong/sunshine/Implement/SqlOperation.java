package com.jiangdong.sunshine.Implement;

import com.jiangdong.sunshine.result.BaseRowMapper;

import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.List;

public interface SqlOperation {

    Object execute(Object proxy, Method method, Object[] args, String sql) throws SQLException;

    Object executeBatch(Object proxy, Method method, Object[] args, String sql) throws SQLException;

    <T> List<T> query(String sql, List<Object> paramsList, BaseRowMapper baseRowMapper) throws SQLException;

}
