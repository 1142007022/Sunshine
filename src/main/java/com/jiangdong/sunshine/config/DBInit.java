package com.jiangdong.sunshine.config;

import com.jiangdong.sunshine.exception.SunShineBaseException;
import com.jiangdong.sunshine.exception.SunshineConfigException;
import com.jiangdong.sunshine.exception.SunshineSQLException;
import com.jiangdong.sunshine.util.StringUtils;
import org.apache.commons.dbcp2.BasicDataSource;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * 使用dbcp2数据库连接池
 */
public class DBInit {

    public static Boolean USE_CACHE;

    private static String DRIVER;

    private static String URL;

    private static String NAME;

    private static String PASSWORD;

    private static Integer maxIdle;

    private static Integer minIdle;

    private static Integer maxWaitMillis;

    private static Integer initialSize;

    private static BasicDataSource dataSource = new BasicDataSource();

    private static Properties prop = new Properties();

    static {

        try {

            File config = new File("src/main/resources/config.properties");
            File sunshine = new File("src/main/resources/sunshine.properties");

            if (config.exists()) {
                FileInputStream fileInputStream = new FileInputStream(config);
                prop.load(fileInputStream);
            } else if (sunshine.exists()) {
                FileInputStream fileInputStream = new FileInputStream(config);
                prop.load(fileInputStream);
            } else {
                throw new SunshineConfigException();
            }

        } catch (SunshineConfigException e) {
            throw new SunshineConfigException("配置文件不存在或命名有误," + e.getMessage(), e);
        } catch (IOException e) {
            throw new SunShineBaseException("配置文件加载异常," + e.getMessage(), e);
        }

        DRIVER = prop.getProperty("jdbc.driver");
        if (StringUtils.isEmpty(DRIVER)) {
            throw new SunshineConfigException("'jdbc.driver' can not be empty!");
        }

        URL = prop.getProperty("jdbc.url");
        if (StringUtils.isEmpty(URL)) {
            throw new SunshineConfigException("'jdbc.url' can not be empty!");
        }

        NAME = prop.getProperty("jdbc.username");
        if (StringUtils.isEmpty(NAME)) {
            throw new SunshineConfigException("'jdbc.username' can not be empty!");
        }

        PASSWORD = prop.getProperty("jdbc.password");
        if (StringUtils.isEmpty(PASSWORD)) {
            throw new SunshineConfigException("'jdbc.password' can not be empty!");
        }

        String useCache = prop.getProperty("jdbc.cache");
        if (StringUtils.isEmpty(useCache) || useCache.equals("false")) {
            USE_CACHE = false;
        }else if (useCache.equals("true")){
            USE_CACHE = true;
        }else {
            throw new SunshineConfigException("'jdbc.cache'的值只允许是true或false,请检查你的格式.");
        }

        maxIdle = prop.get("jdbc.maxIdle") != null ? (Integer) prop.get("jdbc.maxIdle") : 20;
        minIdle = prop.get("jdbc.minIdle") != null ? (Integer) prop.get("jdbc.minIdle") : 5;
        maxWaitMillis = prop.get("jdbc.maxWaitMillis") != null ? (Integer) prop.get("jdbc.maxWaitMillis") : 5000;
        initialSize = prop.get("jdbc.initialSize") != null ? (Integer) prop.get("jdbc.initialSize") : 5;

        dataSource.setDriverClassName(DRIVER);
        dataSource.setUrl(URL);
        dataSource.setUsername(NAME);
        dataSource.setPassword(PASSWORD);
        dataSource.setInitialSize(initialSize);
        dataSource.setMaxIdle(maxIdle);
        dataSource.setMinIdle(minIdle);
        dataSource.setMaxWaitMillis(maxWaitMillis);

    }

    private DBInit() {
    }

    private static class SingletonHolder {
        private static final DBInit DB_INIT = new DBInit();
    }

    public static DBInit getDBInit() {
        return SingletonHolder.DB_INIT;
    }

    public static DataSource getDataSource() {
        return dataSource;
    }

    public Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            throw new SunshineSQLException(e.getMessage(), e);
        }
    }

}
