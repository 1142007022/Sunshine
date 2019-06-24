package com.jiangdong.sunshine.factory;

import java.lang.reflect.Proxy;

public class MapperFactory {

    /**
     * 代理纯接口 无实现类  interfaces传包含这个接口类对象的数组即可
     * @param mapperClass
     * @param <T>
     * @return
     */
    public static <T> T getMapper(Class<T> mapperClass) {
        ClassLoader classLoader = mapperClass.getClassLoader();
        Class<?>[] interfaces = new Class[]{mapperClass};
        ProxyFactory proxy = new ProxyFactory(null);
        return (T) Proxy.newProxyInstance(classLoader, interfaces, proxy);
    }

}
