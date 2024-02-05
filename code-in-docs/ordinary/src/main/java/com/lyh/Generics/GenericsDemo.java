package com.lyh.Generics;

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

// 泛型接口
interface ServiceDemo<T> {
}

// 实现泛型接口，但不指定泛型类型
class ServiceImpl<T> implements ServiceDemo<T> {
}

// 实现泛型接口，但指定泛型类型
class ServiceImpl2 implements ServiceDemo<String> {
}
