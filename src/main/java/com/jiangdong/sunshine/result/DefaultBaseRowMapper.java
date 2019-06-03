package com.jiangdong.sunshine.result;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DefaultBaseRowMapper<T> implements BaseRowMapper {

    private Class clazz;

    public DefaultBaseRowMapper(Class clazz){
        this.clazz = clazz;
    }

    @Override
    public List mapRow(ResultSet resultSet) {
        try {
            List<T> result = new ArrayList<>();
            while (resultSet.next() || resultSet.isLast()){
                T object = (T)clazz.newInstance();
                Field[] fields = clazz.getDeclaredFields();
                for (Field field : fields){
                    field.setAccessible(true);
                    Object param = resultSet.getObject(field.getName());
                    field.set(object,param);
                }
                result.add(object);
            }
            return result;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
