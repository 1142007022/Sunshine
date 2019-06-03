package com.jiangdong.mapper;

import com.jiangdong.entity.Test;
import com.jiangdong.sunshine.annotation.*;
import com.jiangdong.sunshine.enums.OperationTypes;
import com.jiangdong.sunshine.mapper.BaseMapper;
import com.jiangdong.sunshine.result.BaseRowMapper;

import java.util.List;

public interface TestMapper extends BaseMapper {

    @Rollback
    @Insert(id = "233", sql = "insert into test (name,age) value (?,?)")
    boolean insert(String name, String age);

    @Operation(OperationTypes.INSERT_BATCH)
    void insertBatch(@BatchInsertSql String sql);

    @Select(id = "23", sql = "select * from test where test_age = ?")
    List<Test> queryForObject(@Param List<Object> params, @RowMapper BaseRowMapper<Test> baseRowMapper);
}
