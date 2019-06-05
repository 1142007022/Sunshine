package com.jiangdong.sunshine.factory;

import com.jiangdong.sunshine.Implement.InsertFactory;
import com.jiangdong.sunshine.Implement.SelectFactory;
import com.jiangdong.sunshine.annotation.*;
import com.jiangdong.sunshine.enums.OperationTypes;
import com.jiangdong.sunshine.result.BaseRowMapper;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ProxyFactory implements InvocationHandler {

    private static InsertFactory insertFactory = new InsertFactory();
    private static SelectFactory selectFactory = new SelectFactory();
    protected Map<String, Object> params = new LinkedHashMap();

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        //将被代理方法的参数缓存起来--->method对象可获取参数名 尔args为参数实际值 两者长度一定相等
        Parameter[] parameters = method.getParameters();
        for (int i = 0; i < parameters.length; i++) {
            Parameter param = parameters[i];
            if (param.getAnnotation(Param.class) != null) {
                Param paramName = param.getAnnotation(Param.class);
                params.put(paramName.value(), args[i]);
            } else if (param.getAnnotation(RowMapper.class) != null) {
                params.put("rowMapper", args[i]);
            } else {
                params.put(param.getName(), args[i]);
            }
        }

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
        return null;
    }

}
