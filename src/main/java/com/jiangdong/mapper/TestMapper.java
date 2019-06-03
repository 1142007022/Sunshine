package com.jiangdong.mapper;

import com.jiangdong.entity.Test;
import com.jiangdong.sunshine.annotation.*;
import com.jiangdong.sunshine.enums.OperationTypes;
import com.jiangdong.sunshine.mapper.BaseMapper;
import com.jiangdong.sunshine.result.RowMapper;

import java.util.List;

public interface TestMapper extends BaseMapper {

    @Rollback
    @Insert(id = "233",sql = "insert into test (name) value (?)")
    void insert(String name);

    @Operation(OperationTypes.INSERT_BATCH)
    void insertBatch(@BatchInsertSql String sql);

    /*@Select(id = "234", sql = "select * from test")
    <T> List<T> queryForObject(List<Object> params, RowMapper<T> rowMapper);*/

    @Select(id = "23",sql = "select * from test where age = ?")
    List<Test> queryForObject(List<Object> params,RowMapper<Test> rowMapper);
}
