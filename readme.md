# Microservices Event Sourcing

## 一、简介

Microservices Event Sourcing 是一个微服务架构的在线购物平台，使用Spring Boot、Spring Cloud、Spring Reactor、OAuth2、CQRS 构建，实现了基于Event Sourcing的最终一致性，提供了构建端到端微服务的最佳实践。

#### 微服务优点
微服务是最近几年很流行的一种架构模式，相比其他架构模式有着诸多优点。如：
* 每个微服务很小
    * 易于开发者理解，上手快，易开发、易重构
    * IDE更快，使得开发更具生产力
    * 启动速度更快，也使得开发更具生产力，同时加快应用部署
* 每个服务可以独立部署，从而更容易频繁部署新版本的服务
* 容易扩大开发规模。微服务使你能够跨团队开发。每个团队服务自己的一个或多个单一服务。每个团队可以独立于其他团队开发、部署、伸缩他们的服务
* 改善故障隔离。比如，如果一个服务内存泄漏，那么只有这个服务被影响，其它服务仍然能够继续处理请求。而在单体架构中，一个不工作的组件可能拖垮整个系统
* 每个服务可以独立开发和部署，易于持续集成/交付
* 避免长期绑定到一个技术栈。当开发一个新的服务时，你可以使用一个新的技术栈。类似地，当对一个已有服务做出大的改动时，你可以使用一种新的技术栈轻松重写它。

#### 微服务缺点
微服务不是银弹，它也有着自己的缺点：
* 一个分布式系统天然的复杂性
    * 测试更加困难
    * 开发者必须实现服务通信机制
    * 数据的一致性问题
* 部署复杂性。在生产环境中，还存在着部署和管理一个由很多不同类型服务组成的系统的运维复杂性
* 中小规模下增加的资源消耗。在中小规模下，微服务比单体应用消耗更多资源。在大规模环境下，微服务凭借其独立部署的优势能够比单体应用有更好的资源利用率。

#### 微服务模式
业界对于微服务开发已经有了一套实践模式，如：
* 服务注册
* 服务发现
* 负载均衡
* 配置管理
* 断路器
* API网关
* 每个服务一个数据库
* 消息驱动的微服务
* 事件源
* CQRS
* 访问令牌
* 消费者驱动的契约测试
* 日志聚合
* 应用监控
* 审计日志
* 分布式追踪
* 异常追踪
* 健康监测
* 每个服务一个容器

这些微服务模式中的大多数都已经被Spring Cloud(套件)所实现，且开箱即用。但有一些需要经过一番努力才能够实现，如基于事件源实现最终一致性，命令查询职责分离(CQRS)，消息驱动的微服务。而这些模式对于微服务能否成功落地至关重要。因此，[Microservices Event Sourcing](https://github.com/chaokunyang/microservices-event-sourcing)项目在提供构建端到端微服务的最佳实践的同时，主要关注于基于事件源实现最终一致性。我将在接下来分别构建基于CQRS的微服务，和消息驱动的微服务。

#### Microservices Event Sourcing架构
整个系统分为三层：
* web层：在线商店Web，负责请求和UI
* backing层：服务发现、边缘服务、配置服务、用户服务、监控服务
* backend层：目录服务、库存服务、账户服务、购物车服务、订单服务

包含以下微服务.：
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

Microservices Event Sourcing使用以下数据库类型.
* MySQL - RDBMS
* Neo4j - GraphDB
* MongoDB - Document Store
* Redis - Key/value Store

即使是同种类型的数据库，每个服务也应该使用单独的数据库，这是微服务的基本模式之一，能够避免引起数据库层面的耦合，也易于维护、扩展、伸缩.

## 二、Getting Started

1、分别为user-service、account-service、catalog-service、shopping-cart-service创建单独的mysql数据库与用户
``` sql
CREATE  DATABASE time_store_user CHARACTER SET  utf8  COLLATE utf8_general_ci;
grant all privileges on time_store_user.* to 'time'@'localhost' identified by '123456';
grant all privileges on time_store_user.* to 'time'@'127.0.0.1' identified by '123456';
grant all privileges on time_store_user.* to 'time'@'::1' identified by '123456';
flush privileges;
```
``` sql
CREATE  DATABASE time_store_account CHARACTER SET  utf8  COLLATE utf8_general_ci;
grant all privileges on time_store_account.* to 'time'@'localhost' identified by '123456';
grant all privileges on time_store_account.* to 'time'@'127.0.0.1' identified by '123456';
grant all privileges on time_store_account.* to 'time'@'::1' identified by '123456';
flush privileges;
```
``` sql
CREATE  DATABASE time_store_catalog CHARACTER SET  utf8  COLLATE utf8_general_ci;
grant all privileges on time_store_catalog.* to 'time'@'localhost' identified by '123456';
grant all privileges on time_store_catalog.* to 'time'@'127.0.0.1' identified by '123456';
grant all privileges on time_store_catalog.* to 'time'@'::1' identified by '123456';
flush privileges;
```
``` sql
CREATE  DATABASE time_store_cart CHARACTER SET  utf8  COLLATE utf8_general_ci;
grant all privileges on time_store_cart.* to 'time'@'localhost' identified by '123456';
grant all privileges on time_store_cart.* to 'time'@'127.0.0.1' identified by '123456';
grant all privileges on time_store_cart.* to 'time'@'::1' identified by '123456';
flush privileges;
```

2、为inventory-service安装neo4j 2.3，启动neo4j并修改密码为secret
``` shell
curl -v -u neo4j:neo4j -X POST localhost:7474/user/neo4j/password -H "Content-type:application/json" -d "{\"password\":\"secret\"}"
```
3、为order-service安装mongo。在 localhost:27017上启动mongo

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

如果每个服务成功加载，浏览[在线购物网站首页](http://localhost:8787)。点击登录，你将被重定向到Oauth2.0授权服务器，即[user-service](http://localhost:8181/auth/login)。用户名是 user，密码是 password。你将被认证和请求允许对在线购物网站进行令牌授权。在完成授权后，你将被重定向到在线购物网站，然后便可以访问来自边缘服务的受保护资源。

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