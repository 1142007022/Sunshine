package com.jiangdong.mapper;

import com.jiangdong.sunshine.annotation.Insert;

public interface TestMapper {

    @Insert(id = "233",sql = "test")
    void insert();

}
