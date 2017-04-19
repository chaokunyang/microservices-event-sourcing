# Spring-event-sourcing

## 一、简介

Spring-event-sourcing是一个使用Spring Boot、Spring Cloud、Spring Reactor、CQRS、Event Sourcing、OAuth2构建的基于微服务架构的在线购物平台。

整个应用包含以下微服务.
* Config Service
* Discovery Service
* Edge Service
* User Service
* Catalog Service
* Account Service
* Order Service
* Inventory Service
* Shopping Cart Service
* Online Store Web
* Hystrix Dashboard

#### 数据库
微服务架构通常使用多个数据库。业务领域的资源分布在整个微服务架构中，每个微服务有它单独的数据库。开发团队通常根据数据库在解决特定问题时其优势选择合适类型的数据库。

Spring-event-sourcing使用以下数据库类型.
* MySQL - RDBMS
* Neo4j - GraphDB
* MongoDB - Document Store
* Redis - Key/value Store

为了开发方便，每种类型的数据库我只使用一个实例，在生产环境请避免这么做以免引起数据库层面的耦合

## 二、Getting Started

1、创建mysql数据库与用户
``` sql
CREATE  DATABASE time_online_store CHARACTER SET  utf8  COLLATE utf8_general_ci;
grant all privileges on time_online_store.* to 'time_store_user'@'localhost' identified by '123456'; 
grant all privileges on time_online_store.* to 'time_store_user'@'127.0.0.1' identified by '123456'; 
grant all privileges on time_online_store.* to 'time_store_user'@'::1' identified by '123456'; 
flush privileges;
```

2、安装neo4j 2.3 并修改默认密码为secret
``` shell
curl -v -u neo4j:neo4j -X POST localhost:7474/user/neo4j/password -H "Content-type:application/json" -d "{\"password\":\"secret\"}"
```
3、在 localhost:27017 上启动mongo

4、依次启动以下微服务
* Discovery Service
* Edge Service
* User Service
* Catalog Service
* Account Service
* Order Service
* Inventory Service
* Shopping Cart Service
* Online Store Web

为了节省内存，以下服务可以不启动，它们对于业务开发没有很大影响
* Hystrix Dashboard
* Config Service

当所有服务启动完成后，验证服务已经成功注册到[Eureka](http://localhost:8761)。

如果每个服务成功加载，浏览[在线购物网站](http://localhost:8787)。点击登录，你将被重定向到Oauth2.0授权服务器，即[user-service](http://localhost:8181/auth/login)。用户名是 user，密码是 password。你将被认证和请求允许对在线购物网站进行令牌授权。在完成授权后，你将被重定向到在线购物网站，然后便可以访问来自边缘服务的受保护资源。

## 三、预览
* 首页未登录
![首页未登录](https://raw.githubusercontent.com/chaokunyang/microservices-event-sourcing/master/docs/images/01-home-not-logged.png)
* OAuth2.0登录
![OAuth2.0登录](https://github.com/chaokunyang/microservices-event-sourcing/raw/master/docs/images/02-oauth2.0-login.png)
* Oauth2.0授权
![Oauth2.0授权](https://github.com/chaokunyang/microservices-event-sourcing/raw/master/docs/images/03-oauth2.0-grant.png)
* 首页已登录
![首页已登录](https://github.com/chaokunyang/microservices-event-sourcing/raw/master/docs/images/04-home-logged.png)
* 商品详情
![商品详情](https://github.com/chaokunyang/microservices-event-sourcing/raw/master/docs/images/05-product-detail.jpg)
* 购物车
![购物车](https://github.com/chaokunyang/microservices-event-sourcing/raw/master/docs/images/06-shopping-cart.png)
* 订单列表
![订单列表](https://github.com/chaokunyang/microservices-event-sourcing/raw/master/docs/images/07-orders.png)
* 订单详情
![订单详情](https://github.com/chaokunyang/microservices-event-sourcing/raw/master/docs/images/08-order.jpg)
* 用户设置
![订单详情](https://github.com/chaokunyang/microservices-event-sourcing/raw/master/docs/images/09-user-settings.jpg)

## 四、License

This project is licensed under Apache License 2.0.