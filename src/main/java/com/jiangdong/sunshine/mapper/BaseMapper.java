package com.jiangdong.sunshine.mapper;

import com.jiangdong.entity.Test;
import com.jiangdong.sunshine.annotation.BatchInsertSql;
import com.jiangdong.sunshine.result.RowMapper;

import java.util.List;

public interface BaseMapper {

    void insert(String name);

    void insertBatch(@BatchInsertSql String sql);

    List<Test> queryForObject(List<Object> params, RowMapper<Test> rowMapper);
}
