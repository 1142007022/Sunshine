package com.jiangdong.mapper;

import com.jiangdong.sunshine.annotation.*;
import com.jiangdong.sunshine.enums.OperationTypes;
import com.jiangdong.sunshine.result.RowMapper;

public interface TestMapper {

    @Rollback
    @Insert(id = "233",sql = "insert into test (name) value (?)")
    void insert(String name);

    @Operation(OperationTypes.INSERT_BATCH)
    void insertBatch(@BatchInsertSql String sql);

    @Select(id = "234", sql = "")
    <T> T queryForObject(String sql, RowMapper<T> rowMapper);

}
