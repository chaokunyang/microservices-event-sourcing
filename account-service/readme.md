1、创建数据库
  CREATE  DATABASE time_online_store CHARACTER SET  utf8  COLLATE utf8_general_ci;
  
2、创建用户
create user 'time_online_store'@'localhost' IDENTIFIED BY '123456'

3、赋予权限
GRANT ALL on time_online_store.* to 'time_online_store'@'%';


