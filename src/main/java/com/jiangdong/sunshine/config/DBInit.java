package com.jiangdong.sunshine.config;

import com.jiangdong.sunshine.exception.SunshineConfigException;
import com.jiangdong.sunshine.exception.SunShineBaseException;
import com.jiangdong.sunshine.exception.SunshineSQLException;
import org.apache.commons.dbcp2.BasicDataSource;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class DBInit {

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
            FileInputStream fileInputStream = new FileInputStream("src/main/resources/config.properties");
            prop.load(fileInputStream);
        } catch (FileNotFoundException e) {
            throw new SunshineConfigException("配置文件不存在!," + e.getMessage(), e);
        } catch (IOException e) {
            throw new SunShineBaseException("数据库连接异常:" + e.getMessage(), e);
        }

        DRIVER = prop.getProperty("jdbc.driver");
        URL = prop.getProperty("jdbc.url");
        NAME = prop.getProperty("jdbc.username");
        PASSWORD = prop.getProperty("jdbc.password");
        maxIdle = (Integer) prop.get("maxIdle");
        minIdle = (Integer) prop.get("minIdle");
        maxWaitMillis = (Integer) prop.get("maxWaitMillis");
        initialSize = (Integer) prop.get("initialSize");

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
