package com.jiangdong.sunshine;

import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MyTest {

    public static boolean isNumeric(String str) {
        for (int i = 0; i < str.length(); i++) {
            int chr = str.charAt(i);
            if (chr < 48 || chr > 57) {
                return false;
            }
        }
        return true;
    }

    @Test
    public void attributeTest() {
        StringBuffer attributeSql = new StringBuffer(" @> '[");
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> param = new LinkedHashMap<>();
        param.put("name", "kobe");
        param.put("age", 25);

        Map<String, Object> param2 = new LinkedHashMap<>();
        param2.put("kill", "basketball");
        param2.put("address", "USA");
        list.add(param);
        list.add(param2);
        for (Map<String, Object> map : list) {
            attributeSql.append("{");
            for (Map.Entry entry : map.entrySet()) {
                attributeSql.append("\"").append(entry.getKey()).append("\"").append(":");
                if (isNumeric(entry.getValue().toString())) {
                    attributeSql.append(entry.getValue()).append(",");
                } else {
                    attributeSql.append("\"").append(entry.getValue()).append("\"").append(",");
                }
            }
            attributeSql.deleteCharAt(attributeSql.length() - 1);
            attributeSql.append("},");
        }
        attributeSql.deleteCharAt(attributeSql.length() - 1);
        attributeSql.append("]'").append(" ::jsonb");
        System.out.println(attributeSql.toString());
        System.out.println(attributeSql);
    }

}
