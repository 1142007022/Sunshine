package com.jiangdong.sunshine.factory;

import com.jiangdong.sunshine.config.DBInit;
import com.jiangdong.sunshine.runner.ExecuteBatchRunner;
import com.jiangdong.sunshine.runner.ExecuteRunner;
import com.jiangdong.sunshine.runner.QueryRunner;
import com.jiangdong.sunshine.annotation.*;
import com.jiangdong.sunshine.result.BaseRowMapper;
import com.jiangdong.sunshine.sql.SqlOperation;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 负责分发执行类
 */
public class BaseFactory {

    private static SqlOperation executeRunner = SqlOperationFactory.getOperation(ExecuteRunner.class);
    private static SqlOperation queryRunner = SqlOperationFactory.getOperation(QueryRunner.class);
    private static SqlOperation executeBatchRunner = SqlOperationFactory.getOperation(ExecuteBatchRunner.class);

    public Object distribution(Object proxy, Method method, Object[] args, Map<String, Object> params) throws SQLException, IllegalAccessException, InstantiationException {

        if (method.getAnnotation(Insert.class) != null) {
            String sql = method.getAnnotation(Insert.class).sql();
            return executeRunner.execute(proxy, method, args, sql);
        }

        if (method.getAnnotation(Delete.class) != null) {
            String sql = method.getAnnotation(Delete.class).sql();
            return executeRunner.execute(proxy, method, args, sql);
        }

        if (method.getAnnotation(Update.class) != null) {
            String sql = method.getAnnotation(Update.class).sql();
            return executeRunner.execute(proxy, method, args, sql);
        }

        if (method.getAnnotation(Select.class) != null) {
            Select select = method.getAnnotation(Select.class);
            String sql = select.sql();
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
            Boolean useCache = DBInit.USE_CACHE;
            return queryRunner.query(sql, paramsList, baseRowMapper, useCache);
        }

        if (method.getAnnotation(InsertBatch.class) != null) {
            String sql = method.getAnnotation(InsertBatch.class).sql();
            return executeBatchRunner.executeBatch(proxy, method, args, sql, params);
        }

        return null;
    }

}
