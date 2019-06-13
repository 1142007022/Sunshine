package com.jiangdong.sunshine.Implement;

import com.jiangdong.sunshine.exception.SunshineSQLException;
import com.jiangdong.sunshine.result.BaseRowMapper;
import com.jiangdong.sunshine.util.CollectionUtils;
import com.jiangdong.sunshine.util.DBUtils;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class QueryFactory implements SqlOperation {

    @Override
    public Object execute(Object proxy, Method method, Object[] args, String sql) throws SQLException {
        throw new SunshineSQLException("执行了错误的错误的方法,请检查是否选错实现类.");
    }

    @Override
    public Object executeBatch(Object proxy, Method method, Object[] args, String sql) throws SQLException {
        throw new SunshineSQLException("执行了错误的错误的方法,请检查是否选错实现类.");
    }

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
