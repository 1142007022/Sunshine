package com.jiangdong.sunshine.sql;

import com.jiangdong.sunshine.result.BaseRowMapper;

import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface SqlOperation {

    Object execute(Object proxy, Method method, Object[] args, String sql, Boolean useCache) throws SQLException;

    Object execute(Object proxy, Method method, Object[] args, String sql) throws SQLException;

    Object executeBatch(Object proxy, Method method, Object[] args, String sql, Map<String, Object> params) throws SQLException;

    <T> List<T> query(String sql, List<Object> paramsList, BaseRowMapper<T> baseRowMapper, Boolean useCache) throws SQLException;

}
