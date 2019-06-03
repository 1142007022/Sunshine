package com.jiangdong.sunshine.mapper;

import com.jiangdong.entity.Test;
import com.jiangdong.sunshine.result.BaseRowMapper;

import java.util.List;

public interface BaseMapper {

    boolean insert(String name,String age);

    void insertBatch(String sql);

    List<Test> queryForObject(List<Object> params, BaseRowMapper<Test> baseRowMapper);

}
