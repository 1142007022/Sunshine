package com.jiangdong.sunshine.factory;

import com.jiangdong.sunshine.annotation.Insert;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class ProxyFactory implements InvocationHandler {

    @Override
    public Object invoke(Object proxy, Method method, Object[] args)throws Throwable {
        System.out.println("接口方法调用开始");
        //执行方法
        if (method.getAnnotation(Insert.class) != null){
            String sql = method.getAnnotation(Insert.class).sql();
            String id = method.getAnnotation(Insert.class).id();
            System.out.println("sql:" + sql);
            System.out.println("id:" + id);
        }

        System.out.println("接口方法调用结束");
        return "调用返回值";
    }

    /*public static void main(String[] args){
        TestMapper testMapper = MapperFactory.getMapper(TestMapper.class);
        testMapper.insert();
    }*/

}
