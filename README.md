# Sunshine

对jdbc的封装。
1、自定义一个Mapper接口，并添加相关操作；\<br>
2、添加注解，参数List使用@Param，自定义或默认的RowMapper使用@RowMapper;\<br>
3、若实体类和数据库字段不一致使用@Column；\<br>
4、使用MapperFactory.getMapper(Class clazz)获取Mapper接口的具体实现类即可。\<br>
