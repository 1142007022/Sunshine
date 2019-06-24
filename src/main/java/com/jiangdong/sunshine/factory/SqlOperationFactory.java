package com.jiangdong.sunshine.factory;

import com.jiangdong.sunshine.runner.ExecuteBatchRunner;
import com.jiangdong.sunshine.runner.ExecuteRunner;
import com.jiangdong.sunshine.runner.QueryRunner;
import com.jiangdong.sunshine.sql.SqlOperation;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SqlOperationFactory {

    private static ExecuteRunner executeRunner;
    private static QueryRunner queryRunner;
    private static ExecuteBatchRunner executeBatchRunner;
    private static Lock lock = new ReentrantLock();

    public static SqlOperation getOperation(Class clazz) {
        lock.lock();
        try {
            if (clazz.equals(ExecuteRunner.class)) {
                if (executeRunner == null) {
                    executeRunner = new ExecuteRunner();
                }
                return executeRunner;
            }
            if (clazz.equals(QueryRunner.class)) {
                if (queryRunner == null) {
                    queryRunner = new QueryRunner();
                }
                return queryRunner;
            }
            if (clazz.equals(ExecuteBatchRunner.class)) {
                if (executeBatchRunner == null) {
                    executeBatchRunner = new ExecuteBatchRunner();
                }
                return executeBatchRunner;
            }
        } catch (Exception e) {
            throw e;
        } finally {
            lock.unlock();
        }
       
        return null;
    }

}
