package com.jiangdong.sunshine.factory;

import com.jiangdong.sunshine.annotation.Param;
import com.jiangdong.sunshine.annotation.RowMapper;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Proxy;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProxyFactory implements InvocationHandler {

    private static BaseFactory baseFactory = new BaseFactory();
    private Map<String, Object> params = new LinkedHashMap();
    public static Logger log = Logger.getLogger(ProxyFactory.class.toString());
    private Object target;//被代理的真实对象

    public ProxyFactory(Object object) {
        this.target = object;
    }

    public static <T> T getProxyWithImpl(Class<T> implClazz) throws IllegalAccessException, InstantiationException {
        Object object = implClazz.newInstance();
        ProxyFactory proxy = new ProxyFactory(object);
        return (T) Proxy.newProxyInstance(implClazz.getClassLoader(), implClazz.getInterfaces(), proxy);
    }

    /**
     * @param proxy 代理的真实对象 注意和target区分
     * @param method
     * @param args
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        if (target != null) {//被代理真实对象不为null 即有具体实现类
            Level level = Level.INFO;
            log.logp(level, target.getClass().getName(), method.getName(), method.getName() + "()" + "()" + "执行,参数:" + args + ",被代理对象类:" + target.getClass().getName());
            return method.invoke(target, args);
        } else {
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

}
