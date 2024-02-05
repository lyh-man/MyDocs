# 泛型（Generics）

## 一、基本概念
### 1、什么是泛型？（What）
```text
泛型 是 程序语言 中的 一个 风格 或 范式。
泛型 允许 程序员 在 强类型语言 中 编写代码时 使用 不确定的类型，但在 实例化 时需指明类型。

泛型本质是 抽象化 参数类型，即 将 数据类型 指定为 一个参数。 通常使用 ?、E、K、V、T 作为参数名。
    ? 
        通配符，表示不确定的 类型
    E 
        Element，表示元素，通常用于 Set、List
    K、V
        Key、Value，表示 键-值，通常用于 Map
    T
        Type，表示通用类型

Java 语法上支持泛型，但是在 编译阶段 会执行 类型擦除（Type Erasure），将 泛型 替换为 具体类型。 即 伪泛型。
```

### 2、为什么使用泛型？（Why）
```text
泛型 提供了 编译时 类型安全检测机制，在编译时 可检测到 非法类型并 抛出异常。

避免类型转换。
    未使用泛型前，取出集合元素时 需要进行显示的类型转换
        List list = new ArrayList();
        list.add("Hello");
        String str = (String) list.get(0);
    
    使用泛型后，声明集合时已确定 集合类型，取出集合元素时可以避免类型转换。
        List<String> list = new ArrayList<>();
        list.add("Hello");
        String str = list.get(0)

代码复用。
    适用于多种类型间执行 相同的代码。 （Float、Double 等可以替换为 T extend Number） 
```

### 3、如何使用泛型？（How）
+ 泛型类
```text
// 泛型类
class ControllerDemo<T> {
}

// 多元泛型类
class ControllerDemo2<K, V> {
}

// 继承泛型类，但不指定泛型类型
class ControllerDemo3<T> extends ControllerDemo<T> {
}

// 继承泛型类，但指定泛型类型
class ControllerDemo4 extends ControllerDemo<String> {
}
```

+ 泛型接口
```text
// 泛型接口
interface ServiceDemo<T> {
}

// 实现泛型接口，但不指定泛型类型
class ServiceImpl<T> implements ServiceDemo<T> {
}

// 实现泛型接口，但指定泛型类型
class ServiceImpl2 implements ServiceDemo<String> {
}
```

+ 泛型方法
```text
// <T> 用于表示泛型方法， T 表示返回类型， Class<T> 表示泛型的具体类型
public <T> T testGeneric(Class<T> tClass) throws InstantiationException, IllegalAccessException {
    T t = tClass.newInstance();
    return t;
}
```

### 4、泛型上下边界机制
+ 泛型上下边界机制，解决了 泛型中 隐含的 转换问题
```text
<? extends T>
   表示该 类型参数 可以是 T 或者 T 的子类型，即 T 为上边界。 

<? super T>
    表示该 类型参数 可以是 T 或者 T 的父类型，即 T 为下边界。
```

### 5、类型擦除
+ Java 语法上支持泛型，但是在 编译阶段 会执行 类型擦除（Type Erasure），将 泛型 替换为 具体类型。 即 伪泛型。
```text
类型擦除原则
    根据 类型参数 的上下边界 推断并替换 所有的类型参数 为 上边界 或 下边界的 父类（若无上下边界 或 为 ?， 则替换为 Object）。为了保证类型安全，必要时插入强制类型转换代码。
        <T>、<?>    ->    Object
        <T extends Number>    ->    Number
        <? super Number>    ->    Object
```

+ 实现
```text
import java.util.ArrayList;
import java.util.List;

public class GenericsDemo {

    public static void main(String[] args) {
        List list = getGeneric("123");
        List list2 = getGeneric(123);
        List list3 = getGeneric(list);
        System.out.println(list.getClass().getTypeName());
        System.out.println(list2.getClass().getTypeName());
        System.out.println(list3.getClass().getTypeName());
    }

    public static <T> List<T> getGeneric(T t) {
        List<T> list = new ArrayList<>();
        list.add(t);
        return list;
    }

    public static <T extends Number> List<T> getGeneric(T t) {
        List<T> list = new ArrayList<>();
        list.add(t);
        return list;
    }

    public static <T> List<? super Number> getGeneric2(List<? super Number> t) {
        t.add(123);
        return t;
    }
}
```

+ 输出
```text
java.util.ArrayList
java.util.ArrayList
java.util.ArrayList
```

+ 使用 JAD 反编译 class
```text
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class GenericsDemo
{

    public GenericsDemo()
    {
    }

    public static void main(String args[])
    {
        List list = getGeneric("123");
        List list2 = getGeneric(Integer.valueOf(123));
        List list3 = getGeneric(list);
        System.out.println(list.getClass().getTypeName());
        System.out.println(list2.getClass().getTypeName());
        System.out.println(list3.getClass().getTypeName());
    }

    public static List getGeneric(Object t)
    {
        List list = new ArrayList();
        list.add(t);
        return list;
    }

    public static List getGeneric(Number t)
    {
        List list = new ArrayList();
        list.add(t);
        return list;
    }

    public static List getGeneric2(List t)
    {
        t.add(Integer.valueOf(123));
        return t;
    }
}
```

## 二、泛型应用举例
