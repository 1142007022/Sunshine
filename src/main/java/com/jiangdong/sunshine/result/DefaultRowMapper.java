package com.jiangdong.sunshine.result;

import java.sql.ResultSet;
import java.util.List;

public class DefaultRowMapper<T> implements RowMapper {

    private Class clazz;

    public DefaultRowMapper(Class clazz){
        this.clazz = clazz;
    }

    @Override
    public List mapRow(ResultSet rs) {
        try {
            Object object = clazz.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}
