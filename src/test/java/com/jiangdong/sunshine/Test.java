package com.jiangdong.sunshine;

import java.util.HashMap;
import java.util.Map;

public class Test {

    @org.junit.Test
    public void test(){
        Map<String,String> map = new HashMap<>();
        map.put("1", "a");
        map.put("2", "b");
        map.put("3", "c");
        map.remove("3");
        for (Map.Entry entry : map.entrySet()) {
            System.out.println(entry.getValue());
        }
    }
}
