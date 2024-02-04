# 基本概念

## 一、面向对象
### 1、三大特性
#### 1.1、封装
+ 说明
```text
使用 "抽象数据类型" 将 "数据" 和 "基于数据的操作" 封装在一起，使其变成不可分割的独立实体。

"数据" 被保护在 "抽象数据类型（对象）" 内部，尽可能隐藏内部的细节，仅保留一些 "对外的接口" 与  外界联系。
 
即用户无需知道对象内部的细节，但可以通过对象对外提供的接口来访问该对象。
```

+ 优点
```text
减少耦合: 可以独立地开发、测试、优化、使用、理解和修改

减轻维护的负担: 可以更容易被程序员理解，并且在调试的时候可以不影响其他模块

有效地调节性能: 可以通过剖析确定哪些模块影响了系统的性能

提高软件的可重用性

降低了构建大型系统的风险: 即使整个系统不可用，但是这些独立的模块却有可能是可用的
```

+ 举例
```Java
/*
Person 类封装 name、gender、age 等属性。
外界只能通过 get() 方法获取一个 Person 对象的 name 属性和 gender 属性，而无法获取 age 属性，但是 age 属性可以供 work() 方法使用。
注意到 gender 属性使用 int 数据类型进行存储，封装使得用户注意不到这种实现细节。并且在需要修改 gender 属性使用的数据类型时，也可以在不影响客户端代码的情况下进行。
*/
public class Person {
    private String name;
    private int gender;
    private int age;
    public String getName() {
        return name;
    }
    public String getGender() {
        return gender == 0 ? "man" : "woman";
    }
    public void work() {
        if (18 <= age && age <= 50) {
            System.out.println(name + " is working very hard!");
        } else {
            System.out.println(name + " can't work any more!");
        }
    }
}
```

#### 1.2、继承
+ 说明
```text
继承实现了 IS-A 关系。

继承应该遵循里氏替换原则，子类对象必须能够替换掉所有父类对象。
```

+ 举例
```java
/*
Cat 和 Animal 就是一种 IS-A 关系，因此 Cat 可以继承自 Animal，从而获得 Animal 非 private 的属性和方法。
Cat 可以当做 Animal 来使用，也就是说可以使用 Animal 引用 Cat 对象。
*/
public class Animal {
}
public class Cat extends Animal {
}
// 父类引用指向子类对象称为 向上转型 。
//Animal animal = new Cat();
```

#### 1.3、多态
+ 说明
```text
多态分为编译时多态和运行时多态。

编译时多态主要指方法的重载。

运行时多态指程序中定义的对象引用所指向的具体类型在运行期间才确定。

运行时多态的三个条件： 继承、覆盖(重写)、向上转型
```

+ 举例
```java
/*
运行时多态举例：
乐器类(Instrument)有两个子类: Wind 和 Percussion。
子类都覆盖了父类的 play() 方法，并且在 main() 方法中使用父类 Instrument 来引用 Wind 和 Percussion 对象。
在 Instrument 引用调用 play() 方法时，会执行实际引用对象所在类的 play() 方法，而不是 Instrument 类的方法。
*/
public class Instrument {
    public void play() {
        System.out.println("Instrument is playing...");
    }
}
public class Wind extends Instrument {
    public void play() {
        System.out.println("Wind is playing...");
    }
}
public class Percussion extends Instrument {
    public void play() {
        System.out.println("Percussion is playing...");
    }
}
public class Music {
    public static void main(String[] args) {
        List<Instrument> instruments = new ArrayList<>();
        instruments.add(new Wind());
        instruments.add(new Percussion());
        for(Instrument instrument : instruments) {
            instrument.play();
        }
    }
}
```