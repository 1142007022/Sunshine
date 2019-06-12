package com.jiangdong.sunshine.Implement;

import com.jiangdong.sunshine.annotation.*;
import com.jiangdong.sunshine.enums.OperationTypes;
import com.jiangdong.sunshine.result.BaseRowMapper;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BaseFactory {

    protected static InsertFactory insertFactory = new InsertFactory();
    protected static SelectFactory selectFactory = new SelectFactory();
    protected static DeleteFactory deleteFactory = new DeleteFactory();

    public Object distribution(Object proxy, Method method, Object[] args, Map<String, Object> params) throws SQLException {

        if (method.getAnnotation(Insert.class) != null) {
            return insertFactory.insertOne(proxy, method, args);
        }

        if (method.getAnnotation(Operation.class) != null) {
            Operation operation = method.getAnnotation(Operation.class);
            if (operation.value().equals(OperationTypes.INSERT_BATCH)) {
                String sql = (String) params.get("sql");
                return insertFactory.insertBatch(proxy, method, args, sql);
            }
        }

        if (method.getAnnotation(Select.class) != null) {
            Select select = method.getAnnotation(Select.class);
            String sql = select.sql();//sql
            List<Object> paramsList = new ArrayList<>();
            BaseRowMapper baseRowMapper = null;
            Parameter[] methodParameters = method.getParameters();
            for (Parameter parameter : methodParameters) {
                if (parameter.getAnnotation(Param.class) != null) {
                    Param param = parameter.getAnnotation(Param.class);
                    paramsList = (List<Object>) params.get(param.value());
                } else if (parameter.getAnnotation(RowMapper.class) != null) {
                    baseRowMapper = (BaseRowMapper) params.get("rowMapper");
                }
            }
            return selectFactory.select(sql, paramsList, baseRowMapper);
        }

        if (method.getAnnotation(Delete.class) != null) {
            return deleteFactory.delete(method, args);
        }

        return null;
    }

}
