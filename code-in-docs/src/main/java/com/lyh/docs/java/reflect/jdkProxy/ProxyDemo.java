package com.lyh.docs.java.reflect.jdkProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ProxyDemo {

    public static void main(String[] args) {
        Object realObject = new Object();
        Object proxyObject = Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(), realObject.getClass().getInterfaces(), new ProxyInvocationHandler(realObject));
        realObject.toString();
        System.out.println(realObject.getClass().getTypeName());   // java.lang.Object

        proxyObject.toString();
        System.out.println(proxyObject.getClass().getTypeName());  // jdk.proxy1.$Proxy0  (代理对象)

        // 通过 java.lang.reflect.ProxyGenerator.generateProxyClass 动态生成 proxy 的 class 文件
//        new java.io.FileOutputStream(new java.io.File("./proxy.class")).write(classFile);    // 可通过 断点 将 proxy 的 class 信息输出，再通过 IDEA 打开 class 文件
    }

    private static class ProxyInvocationHandler implements InvocationHandler {
        private Object object;

        public ProxyInvocationHandler(Object realObject) {
            this.object = realObject;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            System.out.println("before exec method");
            method.invoke(object, args);
            System.out.println("after exec method");
            return null;
        }
    }

}
