# 语法

## 链接
+ [typescript 中文网站](https://www.tslang.cn/)
+ [typescript 官网](https://www.typescriptlang.org/zh/)
+ [typescript 在线编辑器](https://www.tslang.cn/play/index.html)

## npm 安装、执行
+ 安装
```text
npm install -g typescript
tsc -v
```

+ 编写 helloworld.ts 文件
```text
let message:string = "Hello World";
console.log(message);
```

+ 编译 helloworld.ts 文件 -> helloworld.js
```text
tsc helloworld.ts
```

+ 执行 js
```text
node helloworld.js
```

+ 执行结果
```text
Hello World
```


## 变量声明
+ var 全局
+ let 局部
+ const 局部

## 基础类型
+ boolean
+ number
+ string
+ array
+ tuple（元组）
+ enum
+ any
+ void
```text
boolean
    let isDone: boolean = false;

number
    let decLiteral: number = 6;

string
    let name: string = "bob";
    
    模板字符串 （使用 `` 以及 ${ expr }）
        let sentence: string = `Hello, my name is ${ name }.
            I'll be ${ age + 1 } years old next month.`;
        
        等价于
    
        let sentence: string = "Hello, my name is " + name + ".\n" + "I'll be " + (age + 1) + " years old next month.";

array
    let list: number[] = [1, 2, 3];
    let list: Array<number> = [1, 2, 3];      // Array<元素类型>

tuple
    let x: [string, number] = ['hello', 10];    // 元组类型允许表示一个已知元素数量和类型的数组，各元素的类型不必相同

enum
    enum Color {Red, Green, Blue}
    let c: Color = Color.Green;
    let colorName: string = Color[0];

any
    let list: any[] = [1, true, "free"];   // 匹配任意类型，用于通过编译时类型检查。

void
    function warnUser(): void {
        console.log("This is my warning message");
    }
```