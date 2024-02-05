# 快速入门 (Spring 6.1.3)
+ [Spring 教程](https://spring.io/guides)
+ [spring-framework 最新的文档](https://docs.spring.io/spring-framework/reference/index.html)
+ [spring-framework 历史版本的文档](https://docs.spring.io/spring-framework/docs/)

## 一、基本概念
### 1、什么是 Spring？（What）
```text
Spring 通常指的是 以 Spring Framework 为核心的 一系列技术栈。比如： Spring Security、Spring Data、Spring Boot、Spring Cloud 等。
    
Spring Framework 是一个 Java 开发框架，用于简化 企业级 Java 开发流程。
    核心：
        IOC（Inverse Of Control， 控制反转）
            将 对象的创建过程 交给 Spring 进行管理。
        AOP（Aspect Oriented Programming，面向切面编程）
            从 动态角度 考虑程序运行的过程，底层采用 动态代理 实现（JDK 动态代理、CGLIB 动态代理）。
            将与 业务无关 但 业务 都需调用 的逻辑 封装起来，需要时再将其插入业务逻辑中，减少重复代码，降低模板间的耦合。
```

### 2、为什么使用 Spring? （Why）
```text
简化 Java EE 开发流程。
```

### 3、如何使用 Spring？ （How）
+ 使用流程
```text
Step1：构建项目（maven、gradle 等）
Step2：引入 Spring 相关依赖 （spring-context、spring-core、spring-web 等）
Step3：构建 Spring 配置文件（xml 格式）
Step4：编写业务逻辑，并配置 Spring 配置文件
Step5：加载 Spring 配置文件，执行业务功能
```

#### 举例： 获取 HelloWorld 的 Bean 对象
+ 构建一个 maven 项目
```text
mvn archetype:generate -DgroupId=com.lyh -DartifactId=demo -DarchetypeArtifactId=maven-archetype-quickstart -DarchetypeVersion=RELEASE -DinteractiveMode=false
```

+ 引入 Spring 相关依赖
```text
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-context</artifactId>
    <version>6.1.3</version>
</dependency>
```

+ 构建 类（定义 属性、方法）
```text
package com.lyh.bean;

public class HelloWorld {
    public void sayHelloWorld() {
        System.out.println("Hello World!!!");
    }
}
```

+ 根据 Spring 格式要求创建、并配置 配置文件 （xml 格式）
  + src/main/resources/applicationContext.xml
```text
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans https://www.springframework.org/schema/beans/spring-beans-4.3.xsd
                    http://www.springframework.org/schema/context https://www.springframework.org/schema/context/spring-context-4.3.xsd">

    <bean id="helloWorld" class="com.lyh.bean.HelloWorld"></bean>

</beans>
```

+ 执行并测试
  + 如下三种方式，均能实现 HelloWorld 对象的创建。
```text
package com.lyh.bean;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class HelloWorldTest {

    @Test
    public void test() {
        // 代码中直接 new 一个对象
        HelloWorld helloWorld1 = new HelloWorld();
        helloWorld1.sayHelloWorld();

        // 根据 class 类路径，反射创建对象
        try {
            Class clazz = Class.forName("com.lyh.bean.HelloWorld");
            HelloWorld helloWorld2 = (HelloWorld) clazz.getDeclaredConstructor().newInstance();
            helloWorld2.sayHelloWorld();
        } catch (Exception e) {
            throw new RuntimeException("load class error", e);
        }

        // 加载 Spring 配置文件，从 Spring 的 IOC 容器（Map<String, BeanDefinition> beanDefinitionMap）中获取 对象。 （具体如何实现的，见后文分析）
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        HelloWorld helloWorld3 = (HelloWorld) context.getBean("helloWorld");
        helloWorld3.sayHelloWorld();
    }
}
```

### 4、Spring Framework 模块
+ Core
```text
Beans (spring-beans)
    提供框架的基础部分。
    功能： 
        控制反转（IOC）、依赖注入（Dependency Injection，DI）等

Core (spring-core)
    封装 Spring 框架底层部分。
    功能：
        资源访问（resources）、类型转换（type conversion）、常用工具类 等

Context (spring-context、spring-context-indexer、spring-context-support)
    建立在 Beans 和 Core 基础上。
    功能：
        资源绑定（data binding）、数据验证（validation）、国际化（i18n）、事件（events）、容器生命周期 等

SpEL (spring-expression)
    提供 表达式 语言支持。
    功能：
        修改属性值、方法调用、变量命名、算数与逻辑运算 等
```

+ Data Access
```text
JDBC (spring-jdbc)
    提供一个 JDBC 模板。

Transaction (spring-tx)
    提供 编程式 和 声明式 事务管理。

ORM (spring-orm)
    提供与 "Object - Relational" 映射框架 无缝集成的 API，支持 JPA、JDO、Hibernate、MyBatis。

OXM (spring-oxm)
    提供一个 "Object - XML" 映射的抽象实现。Marshalling XML。
```

+ Integration
```text
JMS (spring-jms)
    提供一个 JMS 模板，简化 两个应用程序间的 异步通信。
```

+ Web
```text
Web (spring-web)
    提供 web 基本功能
    功能： 
        多文件上传、使用 Servlet 监听器初始化一个 IOC 容器以及 Web 应用上下文 等

Servlet (spring-mvc)
    提供一个 MVC Web 框架实现。
    功能： 
        Spring MVC、REST Web Services 等

WebSocket (spring-websocket)
    提供简单接口，快速搭建 WebSocket Server

Webflux (spring-flux)
    提供响应式 web 框架实现。用于创建基于 事件循环执行模型 的 完全异步且非阻塞 的应用程序。
```

+ Other
```text
AOP (spring-aop)
    提供 面向切面编程 的实现。

Aspects (spring-aspects)
    提供一个 AOP 框架实现。

Jcl (spring-jcl)
    提供一个 日志框架实现。

Messaging (spring-messaging)
    提供对 消息传递体系结构 和 协议 的支持。

Instrument (spring-instrument)
    提供 类植入 与 类加载器 的实现，在 JVM 启动时，生成一个代理类，可在运行时修改一个类的功能 （AOP）。
```

## 二、IOC
### 1、IOC (Inversion Of Control, 控制反转)
+ IOC 是一种设计思想，将 对象创建的权力 以及 维护对象与对象之间关系的权力（对象的 成员变量 赋值） 交给 第三方容器 负责。
+ Spring 通过 IOC 容器来管理 Java 对象的 初始化 和 实例化，控制 对象 与 对象 之间的依赖关系。通常将 IOC 管理的 Java 对象称为 Spring Bean。
+ Spring 采用 DI 来实现 IOC。

### 2、DI (Dependency Injection，依赖注入)
+ DI 指的是在 Spring 创建对象的过程中，将 对象 依赖的 属性 通过 配置 进行 注入。
+ 常见注入方式：
  + set 注入
  + 构造器注入

### 3、IOC 容器的实现方式
+ BeanFactory
  + IOC 容器的基本实现，是 Spring 内部使用的接口。
+ ApplicationContext
  + BeanFactory 的子接口，提供更多特性，可供开发人员使用。