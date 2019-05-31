package com.jiangdong.mapper;

import com.jiangdong.sunshine.annotation.Insert;
import com.jiangdong.sunshine.annotation.BatchInsertSql;
import com.jiangdong.sunshine.annotation.Operation;
import com.jiangdong.sunshine.annotation.Rollback;
import com.jiangdong.sunshine.enums.OperationTypes;

public interface TestMapper {

    @Rollback
    @Operation(OperationTypes.INSERT)
    @Insert(id = "233",sql = "insert into test (name) value (?)")
    void insert(String name);

    @Operation(OperationTypes.INSERT_BATCH)
    void insertBatch(@BatchInsertSql String sql);

}
