package com.jiangdong.sunshine.factory;

import com.jiangdong.sunshine.runner.ExecuteRunner;
import com.jiangdong.sunshine.runner.QueryRunner;
import com.jiangdong.sunshine.sql.SqlOperation;

public class SqlOperationFactory {

    private static ExecuteRunner executeRunner;
    private static QueryRunner queryRunner;

    public static SqlOperation getOperation(Class clazz) {
        if (clazz.equals(ExecuteRunner.class)) {
            if (executeRunner != null) {
                return executeRunner;
            } else {
                executeRunner = new ExecuteRunner();
                return executeRunner;
            }
        }
        if (clazz.equals(QueryRunner.class)) {
            if (queryRunner != null) {
                return queryRunner;
            } else {
                queryRunner = new QueryRunner();
                return queryRunner;
            }
        }
        return null;
    }

}
