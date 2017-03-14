1、创建数据库
  CREATE  DATABASE online_store CHARACTER SET  utf8  COLLATE utf8_general_ci;
  
2、创建用户
create user 'online_store'@'localhost' IDENTIFIED BY '123456'

3、赋予权限
GRANT ALL on online_store.* to 'online_store'@'%';


