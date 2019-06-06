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

    public static String DRIVER;
    public static String URL;
    public static String NAME;
    public static String PASSWORD;
    public static Integer maxIdle;
    public static Integer minIdle;
    public static Integer maxWaitMillis;
    public static Integer initialSize;

    private static BasicDataSource dataSource = new BasicDataSource();
    private static Properties prop = new Properties();

    static {

        try {
            prop.load(new FileInputStream("src/main/resources/config.properties"));
            DRIVER = prop.getProperty("jdbc.driver");
            URL = prop.getProperty("jdbc.url");
            NAME = prop.getProperty("jdbc.username");
            PASSWORD = prop.getProperty("jdbc.password");
            maxIdle = (Integer) prop.get("maxIdle");
            minIdle = (Integer) prop.get("minIdle");
            maxWaitMillis = (Integer) prop.get("maxWaitMillis");
            initialSize = (Integer) prop.get("initialSize");
        } catch (FileNotFoundException e) {
            throw new SunshineConfigException("配置文件不存在!," + e.getMessage(), e);
        } catch (IOException e) {
            throw new SunShineBaseException("数据库连接异常:" + e.getMessage(), e);
        }

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
