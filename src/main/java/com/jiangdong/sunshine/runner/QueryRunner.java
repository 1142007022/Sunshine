package com.jiangdong.sunshine.runner;

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
    public Object execute(Object proxy, Method method, Object[] args, String sql) throws SQLException {
        throw new SunshineSQLException("执行了错误的方法,请检查是否选错实现类.");
    }

    @Override
    public Object executeBatch(Object proxy, Method method, Object[] args, String sql, Map<String, Object> params) throws SQLException {
        throw new SunshineSQLException("执行了错误的方法,请检查是否选错实现类.");
    }

    /**
     * @param sql
     * @param paramsList
     * @param baseRowMapper
     * @param <T>
     * @return 查询统一返回list 单个为list(0)
     * @throws SQLException
     */
    @Override
    public <T> List<T> query(String sql, List<Object> paramsList, BaseRowMapper baseRowMapper) throws SQLException {
        return select(sql, paramsList, baseRowMapper);
    }

    private <T> List<T> select(String sql, List<Object> params, BaseRowMapper baseRowMapper) throws SQLException {
        Connection connection = DBUtils.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        try {
            if (CollectionUtils.isNotEmpty(params)) {
                for (int i = 0; i < params.size(); i++) {
                    preparedStatement.setObject(i + 1, params.get(i));
                }
            }
            ResultSet resultSet = preparedStatement.executeQuery();
            return baseRowMapper.mapRow(resultSet);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } finally {
            DBUtils.closeConnection(connection);
        }
        return null;
    }

}
