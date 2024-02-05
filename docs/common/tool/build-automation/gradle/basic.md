# 快速入门

## 链接
+ [gradle 下载](https://gradle.org/install/)
+ [gradle 8.5 下载](https://gradle.org/next-steps/?version=8.5&format=bin)

## 配置 Gradle
+ 安装
```text
前提： 
    安装 Java 8 或者 更高版本。
    可通过 java --version 查看 Java 版本。
    
配置环境变量
    GRADLE_HOME=D:\Gradle\gradle-8.5
    在 PATH 中追加 %GRADLE_HOME%\bin

查看 gradle 版本
    gradle -v
```

+ 配置镜像仓库
  + 进入 %GRADLE_HOME%\init.d 目录
  + 新建 init.gradle 文件，配置 阿里云 仓库
```text    
allprojects {
    repositories {
        mavenLocal()
        maven { name "Alibaba" ; url "https://maven.aliyun.com/repository/public" }
        mavenCentral()
    }
 
    buildscript { 
        repositories { 
            maven { name "Alibaba" ; url 'https://maven.aliyun.com/repository/public' }
        }
    }
}
```

+ 配置本地仓库
```text
配置环境变量
    GRADLE_USER_HOME=D:\mavenrepos
```

+ IDEA 配置 Gradle
```text
File->Settings->Build Tools->Gradle

Gradle user home:
    配置 本地仓库 的路径
Use Gradle from:
    Specified location
    配置 Gradle 安装路径
```