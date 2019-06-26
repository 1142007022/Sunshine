# Sunshine

对jdbc的封装。<br>
1、将数据库配置文件放在src/main/resources/config(sunshine).properties；<br>
2、自定义一个Mapper接口，并添加相关操作；<br>
3、添加注解，参数List使用@Param，自定义或默认的RowMapper使用@RowMapper;<br>
4、若实体类和数据库字段不一致使用@Column；<br>
5、使用MapperFactory.getMapper(Class clazz)获取Mapper接口的具体实现类即可。<br>
*****************************************************************************
*  配置示例
  *  jdbc.driver=com.mysql.jdbc.Driver<br>
  *  jdbc.url=jdbc:mysql://127.0.0.1:3306/sunshine<br>
  *  jdbc.username=root<br>
  *  jdbc.password=123456<br>
*****************************************************************************
*  数据库连接池默认使用dbcp2，暂不支持更改。
