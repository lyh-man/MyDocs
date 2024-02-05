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

        // 加载 配置文件，从 IOC 容器中获取 对象
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        HelloWorld helloWorld3 = (HelloWorld) context.getBean("helloWorld");
        helloWorld3.sayHelloWorld();
    }
}
