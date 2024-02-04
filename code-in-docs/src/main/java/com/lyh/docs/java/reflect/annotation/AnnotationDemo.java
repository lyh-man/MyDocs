package com.lyh.docs.java.reflect.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public class AnnotationDemo {
    public static void main(String[] args) throws ClassNotFoundException {
        String className = ServiceImpl.class.getName();    // 一般通过 package 扫描来获得
        Class<?> clazz = Class.forName(className);   // 反射获取 class 对象
        if (clazz.isAnnotationPresent(ServiceDemo.class)) {
            System.out.println("do something!");
        }
        if (!clazz.isAnnotationPresent(Documented.class)) {
            System.out.println("only test");
        }
    }
}

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@interface ServiceDemo {
    String value() default "";
}

@ServiceDemo
class ServiceImpl {

}
