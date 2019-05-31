package com.jiangdong;


import com.jiangdong.mapper.TestMapper;
import com.jiangdong.sunshine.factory.MapperFactory;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        TestMapper testMapper = MapperFactory.getMapper(TestMapper.class);
        testMapper.insert("jiang");
    }
}
