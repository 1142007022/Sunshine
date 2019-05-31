package com.jiangdong.sunshine.factory;

import com.jiangdong.sunshine.Implement.InsertFactory;
import com.jiangdong.sunshine.annotation.Insert;
import com.jiangdong.sunshine.annotation.Operation;
import com.jiangdong.sunshine.config.DBInit;
import com.jiangdong.sunshine.enums.OperationTypes;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.sql.Connection;

public class ProxyFactory implements InvocationHandler {

    private static DBInit dbInit = DBInit.getDBInit();
    private static Connection connection;
    private static InsertFactory insertFactory = new InsertFactory();

    static {
        connection = dbInit.getConnection();
        InsertFactory.connection = connection;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        if (method.getAnnotation(Insert.class) != null) {
            return insertFactory.insertOne(proxy,method,args);
        }

        if (method.getAnnotation(Operation.class) != null){
            Operation operation = method.getAnnotation(Operation.class);
            if (operation.value().equals(OperationTypes.INSERT_BATCH)){
                return insertFactory.insertBatch(proxy,method,args);
            }
        }

        return null;
    }

}
