package com.jiangdong.sunshine.Implement;

public class SqlOperationFactory {

    private static ExecuteFactory executeFactory;
    private static QueryFactory queryFactory;

    public static SqlOperation getOperation(Class clazz) {
        if (clazz.equals(ExecuteFactory.class)) {
            if (executeFactory != null) {
                return executeFactory;
            } else {
                executeFactory = new ExecuteFactory();
                return executeFactory;
            }
        }
        if (clazz.equals(QueryFactory.class)) {
            if (queryFactory != null) {
                return queryFactory;
            } else {
                queryFactory = new QueryFactory();
                return queryFactory;
            }
        }
        return null;
    }

}
