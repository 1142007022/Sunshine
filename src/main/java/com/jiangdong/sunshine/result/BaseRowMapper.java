package com.jiangdong.sunshine.result;

import java.sql.ResultSet;
import java.util.List;

public interface BaseRowMapper<T> {

    List<T> mapRow(ResultSet rs);

}
