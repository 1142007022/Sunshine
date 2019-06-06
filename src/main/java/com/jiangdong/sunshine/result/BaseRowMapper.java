package com.jiangdong.sunshine.result;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface BaseRowMapper<T> {

    List<T> mapRow(ResultSet rs) throws SQLException, IllegalAccessException, InstantiationException;

}
