# SSO 单点登录
----

利用JWT实现跨域的单点登录。

### 介绍
* 认证服务器sso-server
* 系统1（taobao）sso-client1
* 系统2（tmall）sso-client2

JWT介绍：[JSON Web Token介绍--阮一峰](http://www.ruanyifeng.com/blog/2018/07/json_web_token-tutorial.html)

#### 简单的业务流程
用户（未登录）访问系统1->判断登录状态，重定向到认证服务器的登录系统，登录->生成jwt存入cookie->成功登录系统1(callback)->若在有效期中继续访问系统2，则不需要重复登录->完成sso.

### 运行方式
* sso-server： `mvn spring-boot:run`
* sso-client1：`mvn spring-boot:run`
* sso-client2：`mvn spring-boot:run`
* 访问localhost:8081/index
* 输入密码，成功登录
* 访问localhost:8082/index，发现已经登录