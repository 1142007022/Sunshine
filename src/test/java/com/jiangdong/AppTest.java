package com.jiangdong;

import com.jiangdong.mapper.TestMapper;
import com.jiangdong.sunshine.factory.MapperFactory;
import com.jiangdong.sunshine.result.DefaultBaseRowMapper;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Unit test for simple App.
 */
public class AppTest {
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue() {
        TestMapper testMapper = MapperFactory.getMapper(TestMapper.class);
        List<Object> params = new ArrayList();
        params.add(34);
        List<com.jiangdong.entity.Test> testList = testMapper.queryForObject(params,new DefaultBaseRowMapper<com.jiangdong.entity.Test>(com.jiangdong.entity.Test.class));
        for (com.jiangdong.entity.Test test : testList){
            System.out.println(test.getName());
        }
    }

    @Test
    public void insertTest(){
        TestMapper testMapper = MapperFactory.getMapper(TestMapper.class);
        boolean res = testMapper.insert("test_insert","23");
        System.out.println(res);
    }
}
