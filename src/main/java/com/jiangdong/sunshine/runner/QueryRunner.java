package com.jiangdong.sunshine.runner;

import com.jiangdong.sunshine.cache.CacheManager;
import com.jiangdong.sunshine.exception.SunshineSQLException;
import com.jiangdong.sunshine.result.BaseRowMapper;
import com.jiangdong.sunshine.sql.SqlOperation;
import com.jiangdong.sunshine.util.CollectionUtils;
import com.jiangdong.sunshine.util.DBUtils;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class QueryRunner implements SqlOperation {

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
        throw new SunshineSQLException("执行了错误的方法,请检查是否选错实现类.");
    }

    /**
     * @param useCache      是否使用缓存
     * @param sql
     * @param params
     * @param baseRowMapper
     * @param <T>
     * @return 查询统一返回list 单个为list(0)
     * @throws SQLException
     */
    @Override
    public <T> List<T> query(String sql, List<Object> params, BaseRowMapper<T> baseRowMapper, Boolean useCache) {
        if (useCache) {
            List<T> list = CacheManager.get(sql);
            if (CollectionUtils.isNotEmpty(list)) {
                return list;
            }
        }
        Connection connection = DBUtils.getConnection();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
        } catch (SQLException e) {
            throw new SunshineSQLException(e.getMessage(), e.getCause());
        }
        try {
            if (CollectionUtils.isNotEmpty(params)) {
                for (int i = 0; i < params.size(); i++) {
                    preparedStatement.setObject(i + 1, params.get(i));
                }
            }
            ResultSet resultSet = preparedStatement.executeQuery();
            List<T> list = baseRowMapper.mapRow(resultSet);
            if (useCache) {
                CacheManager.put(sql, list);
            }
            return list;
        } catch (Exception e) {
            throw new SunshineSQLException(e.getMessage(), e.getCause());
        } finally {
            try {
                DBUtils.closeConnection(connection);
            } catch (SQLException e) {
                throw new SunshineSQLException(e.getMessage(), e.getCause());
            }
        }
    }

}
