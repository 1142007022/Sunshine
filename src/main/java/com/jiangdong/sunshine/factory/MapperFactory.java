package com.jiangdong.sunshine.factory;

import java.lang.reflect.Proxy;

public class MapperFactory {

    public static <T> T getMapper(Class<T> mapperClass) {
        ClassLoader classLoader = mapperClass.getClassLoader();
        Class<?>[] interfaces = new Class[]{mapperClass};
        ProxyFactory proxy = new ProxyFactory();
        return (T) Proxy.newProxyInstance(classLoader, interfaces, proxy);
    }

}
