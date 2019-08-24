# Sunshine

对jdbc的封装。<br>
1、将数据库配置文件放在src/main/resources/config(sunshine).properties；<br>
2、自定义一个Mapper接口，并添加相关@Inset|@Delete|@Update|@Select|@InsertBatch等操作；<br>
3、添加注解，参数List使用@Param，自定义或默认的RowMapper使用@RowMapper;<br>
4、若实体类和数据库字段不一致使用@Column；<br>
5、使用MapperFactory.getMapper(Class clazz)获取Mapper接口的具体实现类即可。<br>
*****************************************************************************
*  配置示例
  *  jdbc.driver=com.mysql.jdbc.Driver //jdbc驱动<br>
  *  jdbc.url=jdbc:mysql://127.0.0.1:3306/sunshine //数据库地址<br>
  *  jdbc.username=root //账户名<br>
  *  jdbc.password=123456 //密码<br>
  *  jdbc.cache=true //是否使用缓存，默认关闭<br>
  *  jdbc.maxWaitMillis=10 //初始连接数<br>
  *  jdbc.maxIdle=20 //最大连接数,默认20<br>
  *  jdbc.minIdle=10 //最小连接数,默认5<br>
  *  jdbc.maxWaitMillis=5000 //最长等待时间,默认5000ms<br>
  *  sunshine.spring.scanPackage=com.sunshine//扫描的包<br>
*****************************************************************************
*  数据库连接池默认使用dbcp2，暂不支持更改。
