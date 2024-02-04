# 反射（Reflect）

## 一、基本概念
### 1、什么是反射？ （What）
```text
反射
    指的是 在 程序运行期间，可以动态 获取 class 的信息 以及 动态调用 对象方法 的功能

功能
    对于任意一个 class，可以知道其 所有属性 和 方法。
    对于任意一个 对象，可以调用其 任意属性 和 方法。

缺点
    性能开销
        反射涉及动态解析的类型，可能无法被 JVM 优化，导致 反射操作性能 低于 非反射操作。
    暴露内部结构
```

### 2、为什么使用反射？ (Why)
```text
反射 可以 动态编译、创建对象，提高了 程序的灵活性，降低模块的 耦合性，强化了 多态 的特性。
反射 可以避免 Hard-Coding，是 框架技术的 基础。

静态编译
    在 编译期 确定类型，编译时 绑定对象。 

动态编译
    在 运行期 确定类型，运行时 绑定对象。
```

### 3、如何使用反射？（How）
```text
获取 class 信息
    Step1、获取 class
    Step2、根据 class 获取 属性、方法。

调用 对象
    Step1、获取 class
    Step2、根据 class 实例化对象
    Step3、设定属性、调用 方法。
```

### 4、常用方法
```text
获取 class 常用方法
    // 类名.class
        Class<?> clazz = Object.class;   
    // 对象.getClass()
        Class<?> clazz2 = new Object().getClass();   
    // Class.forName(类名)
        Class<?> clazz3 = Class.forName("java.lang.Object");
    //  ClassLoader.loadClass(类名);
        Class<?> clazz4 = ClassLoader.getSystemClassLoader().loadClass("java.lang.Object");
    // packageScan 包扫描

实例化对象
    // clazz.newInstance(), 无参构造
        Object obj = Object.class.newInstance();
    // clazz.getConstructor(Class<?>... parameterTypes).newInstance(Object... initargs)，有参构造
        Object obj2 = Object.class.getConstructor().newInstance();

获取 class 属性
    // Field[] getDeclaredFields()   获得该类中定义的所有属性（不包含继承）
    // Field getDeclaredField(String name)获得该类中定义的指定属性(不包含继承)
    // Field[] getFields()  获得该类中所有public的属性(包含继承)
    // Field getField (String name)  获得该类中指定的public属性(包含继承)

获取 class 方法
    // Method[] getDeclaredMethods()   获得该类中定义的所有方法(不包含父类继承)
    // Method getDeclaredMethod(String name, Class<?>... parameterTypes)    根据该类中定义的指定方法(不包含父类继承)
    // Method[] getMethods()  获得权限为public的所有的方法 (包含父类继承)
    // Method getMethod(String name, Class<?>... parameterTypes)    获得权限为public的指定的方法 (包含父类继承)

设定 属性值 （Field 的方法）
    // public void set(Object obj, Object value)     obj 为对象，value 为设定的值。 修改任意对象的 任意 成员变量（field) 。

调用 方法 (Method 的方法)
    // public Object invoke(Object obj, Object... args)      obj 为对象， args 为方法参数。 调用任意对象的 任意方法（method）。
```

## 二、反射应用举例
### 1、数据库驱动加载
+ 数据库驱动（Driver） 由 DriverManager 管理，一般在加载 Drvier 类时，将 Driver 注册到 DriverManager 中（在 static 块中执行 registerDriver）
```text
static {
    Driver registeredDriver = new Driver();
    DriverManager.registerDriver(registeredDriver);
}
```

+ 举例
```text
假设有 三种 DB 配置文件(db.config)，需要根据项目需要加载不同的配置文件（取得不同的 Driver）。
    com.mysql.jdbc.Driver
    oracle.jdbc.driver.OracleDriver
    org.postgresql.Driver

可以采取硬编码，通过 if-else 来判定 Driver 的种类。
    if ("com.mysql.jdbc.Driver".equals(dbConfig.driver)) {
        new com.mysql.jdbc.Driver();
    } else if ("oracle.jdbc.driver.OracleDriver".equals(dbConfig.driver)) {
        new oracle.jdbc.driver.OracleDriver();  
    } else if ("org.postgresql.Driver".equals(dbConfig.driver)) {
        new org.postgresql.Driver();
    }

可采用 反射，不需要指定具体的 Driver，在运行期间去加载。 代码更灵活。
    Class.forName(dbConfig.driver);
```

### 2、JDK 动态代理
+ 借助 JDK 提供的 Proxy 以及 ProxyGenerator 生成代理对象
```text
JDK 动态代理的实现逻辑
    角色
        被代理的类  ->   真正干活的 类 （Real）
        代理的类   ->    JDK 的 ProxyGenerator 生成的代理对象 （$Proxy0）
        代理的类执行的动作    ->   实现 java.lang.reflect.InvocationHandler 的类 （MyInvocationHandler）。
    
    关系
        MyInvocationHandler 内部持有 Real，即 Real 作为成员变量，并通过 构造函数 传入。
        MyInvocationHandler 重写 invoke 方法，可以通过 method.invoke(Real, xx) 的方式执行 Real.method
        $Proxy0 由 JDK 内部逻辑生成。

$Proxy0 的核心思想：
    $Proxy0 继承 Proxy 并实现 被代理类 相同的 接口。 即 class $Proxy0 extends Proxy implements xxx, yyy, zzz
    $Proxy0 的构造函数中，传入 InvocationHandler。 即 public $Proxy0(InvocationHandler var1)
    生成 被代理对象 同样的方法 以及 Method 对象，内部调用 InvocationHandler 的 invoke 方法。即 super.h.invoke(Method)

$Proxy0 的调用过程：
    Proxy.newProxyInstance()  生成一个 Proxy 对象，由于 实现了 被代理对象 同样的 接口，所以 可以进行 类型强转。
    调用方法 xxx.method -> $Proxy0.method -> InvocationHandler.invoke -> xxMethod.invoke -> realObject.method
```

+ JDK 代码实现
```text
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ProxyDemo {

    public static void main(String[] args) {
        Object realObject = new Object();
        Object proxyObject = Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(), realObject.getClass().getInterfaces(), new ProxyInvocationHandler(realObject));
        realObject.toString();
        proxyObject.toString();
        System.out.println(realObject.getClass().getTypeName());   // java.lang.Object
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
```

+ 输出结果
```text
java.lang.Object
before exec method
after exec method
jdk.proxy1.$Proxy0
```

+ Proxy 的 class
```text
package jdk.proxy1;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.lang.reflect.UndeclaredThrowableException;

public final class $Proxy0 extends Proxy {
    private static final Method m0;
    private static final Method m1;
    private static final Method m2;

    public $Proxy0(InvocationHandler var1) {
        super(var1);
    }

    public final int hashCode() {
        try {
            return (Integer)super.h.invoke(this, m0, (Object[])null);
        } catch (RuntimeException | Error var2) {
            throw var2;
        } catch (Throwable var3) {
            throw new UndeclaredThrowableException(var3);
        }
    }

    public final boolean equals(Object var1) {
        try {
            return (Boolean)super.h.invoke(this, m1, new Object[]{var1});
        } catch (RuntimeException | Error var2) {
            throw var2;
        } catch (Throwable var3) {
            throw new UndeclaredThrowableException(var3);
        }
    }

    public final String toString() {
        try {
            return (String)super.h.invoke(this, m2, (Object[])null);
        } catch (RuntimeException | Error var2) {
            throw var2;
        } catch (Throwable var3) {
            throw new UndeclaredThrowableException(var3);
        }
    }

    static {
        try {
            m0 = Class.forName("java.lang.Object").getMethod("hashCode");
            m1 = Class.forName("java.lang.Object").getMethod("equals", Class.forName("java.lang.Object"));
            m2 = Class.forName("java.lang.Object").getMethod("toString");
        } catch (NoSuchMethodException var2) {
            throw new NoSuchMethodError(var2.getMessage());
        } catch (ClassNotFoundException var3) {
            throw new NoClassDefFoundError(var3.getMessage());
        }
    }

    private static MethodHandles.Lookup proxyClassLookup(MethodHandles.Lookup var0) throws IllegalAccessException {
        if (var0.lookupClass() == Proxy.class && var0.hasFullPrivilegeAccess()) {
            return MethodHandles.lookup();
        } else {
            throw new IllegalAccessException(var0.toString());
        }
    }
}
```

### 3、注解扫描
+ 注解扫描如何生效
```text
核心思想
    package 扫描，扫描所有类，并过滤出 标有 某注解 的类。
    通过 反射，实例化 该类，并放到 容器中。
```

+ 实现
```text
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
```

+ 输出结果
```text
do something!
only test
```