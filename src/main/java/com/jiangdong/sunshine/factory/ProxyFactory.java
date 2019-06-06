package com.jiangdong.sunshine.factory;

import com.jiangdong.sunshine.Implement.BaseFactory;
import com.jiangdong.sunshine.annotation.Param;
import com.jiangdong.sunshine.annotation.RowMapper;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.LinkedHashMap;
import java.util.Map;

public class ProxyFactory implements InvocationHandler {

    private static BaseFactory baseFactory = new BaseFactory();
    protected Map<String, Object> params = new LinkedHashMap();

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        //将被代理方法的参数缓存起来--->method对象可获取参数名 而args为参数实际值 两者长度一定相等
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

        return baseFactory.distribution(proxy, method, args, params);

    }

}
