package com.jiangdong.sunshine.Implement;

import com.jiangdong.sunshine.result.BaseRowMapper;
import com.jiangdong.sunshine.util.CollectionUtils;
import com.jiangdong.sunshine.util.DBUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class SelectFactory {

    public <T> List<T> select(String sql, List<Object> params, BaseRowMapper baseRowMapper) {
        Connection connection = DBUtils.dbInit.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            if (CollectionUtils.isNotEmpty(params)) {
                for (int i = 0; i < params.size(); i++) {
                    preparedStatement.setObject(i + 1, params.get(i));
                }
            }
            ResultSet resultSet = preparedStatement.executeQuery();
            return baseRowMapper.mapRow(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
