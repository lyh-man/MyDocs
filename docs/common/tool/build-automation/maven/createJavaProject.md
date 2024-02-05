# 构建 java 项目

## 一、maven 基本概念
### 1、常用命令
```text
mvn dependency:tree  // 以 树状 输出 项目依赖 
```

### 2、default lifecycle
```text
validate
initialize
generate-sources
process-sources
generate-resources
process-resources
compile
process-classes
generate-test-sources
process-test-sources
generate-test-resources
process-test-resources
test-compile
process-test-classes
test
prepare-package
package
pre-integration-test
integration-test
post-integration-test
verify
install
deploy
pre-clean
clean
post-clean
pre-site
site
post-site
site-deploy
```

### 3、依赖限定范围（dependency scope）
+ 若制作 jar 包时，只想包含一部分 jar 依赖包，则可以在 dependency 时通过 scope 限定依赖包执行范围。 
```text
scope 常用值为：
    compile，缺省值，适用于所有阶段，会随着项目一起发布。
    provided，类似 compile，期望 JDK、容器或使用者会提供这个依赖。如 servlet.jar。
    runtime，只在运行时使用，如 JDBC 驱动，适用运行和测试阶段。
    test，只在测试时使用，用于编译和运行测试代码。不会随项目发布。
    system，类似 provided，需要显式提供包含依赖的 jar，Maven 不会在 Repository 中查找它。
```

## 二、maven 操作
### 1、通过 maven 命令行 构建 java project
+ 常用模板
```text
maven-archetype-quickstart             // java project
maven-archetype-webapp                 // java web project
```

+ 命令
```text
// 使用旧模板（1.0）
mvn archetype:generate -DgroupId=com.lyh -DartifactId=demo -DarchetypeArtifactId=maven-archetype-quickstart -DinteractiveMode=false
// 使用新模板（1.4）
mvn archetype:generate -DgroupId=com.lyh -DartifactId=demo -DarchetypeArtifactId=maven-archetype-quickstart -DarchetypeVersion=RELEASE -DinteractiveMode=false
```

+ 举例: 使用 maven 命令行构建 java web 工程
```text
// 使用旧模板（1.0）
mvn archetype:generate -DgroupId=com.lyh -DartifactId=demo -DarchetypeArtifactId=maven-archetype-webapp -DinteractiveMode=false
// 使用新模板（1.4）
mvn archetype:generate -DgroupId=com.lyh -DartifactId=demo -DarchetypeArtifactId=maven-archetype-webapp -DarchetypeVersion=RELEASE -DinteractiveMode=false

工程目录结构如下：（main、test 目录下的 java、resources 目录需要手动添加一下）
└─spring-demo
    └─src
        ├─main
        │    ├─java
        │    ├─resources
        │    └─webapp
        │        ├─WEB-INF
        │        │  └─web.xml
        │        └─index.jsp
        └─test
            ├─java
            └─resources
```

### 2、使用 maven 构建可执行的 jar 包
+ 通过 maven 命令行 构建 java project
```text
mvn archetype:generate -DgroupId=com.lyh -DartifactId=demo -DarchetypeArtifactId=maven-archetype-quickstart -DarchetypeVersion=RELEASE -DinteractiveMode=false
```

+ 修改 pom.xml 文件，将 packaging 改为 jar
```text
<packaging>jar</packaging>
```

+ 添加 maven 插件 （若不存在 maven-compiler-plugin、maven-jar-plugin 插件，则需要添加）
```text
<build>
    <plugins>
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-compiler-plugin</artifactId>
            <version>3.8.1</version>
            <configuration>
                <source>1.8</source>
                <target>1.8</target>
            </configuration>
        </plugin>

        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-jar-plugin</artifactId>
            <version>2.4</version>
            <!-- 对要打的jar包进行配置 -->
            <configuration>
                <archive>
                    <!--生成的jar中，不要包含pom.xml和pom.properties这两个文件-->
                    <addMavenDescriptor>false</addMavenDescriptor>

                    <!-- Manifest specific configuration -->
                    <manifest>
                        <!--是否要把第三方jar放到manifest的classpath中-->
                        <addClasspath>true</addClasspath>

                        <!--生成的manifest中classpath的前缀，因为要把第三方jar放到lib目录下，所以classpath的前缀是lib/-->
                        <classpathPrefix>lib/</classpathPrefix>
                    </manifest>
                </archive>
                <!--过滤掉不希望包含在jar中的文件-->
                <excludes>
                    <!-- 排除不需要的文件夹(路径是jar包内部的路径) -->
                    <exclude>**/assembly/</exclude>
                </excludes>
            </configuration>
        </plugin>

        <plugin>  <!--在打包阶段将依赖的jar包导出到lib目录下-->
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-dependency-plugin</artifactId>
            <executions>
                <execution>
                    <id>copy-dependencies</id>
                    <phase>package</phase>
                    <goals>
                        <goal>copy-dependencies</goal>
                    </goals>
                    <configuration>
                        <type>jar</type>
                        <includeTypes>jar</includeTypes>
                        <outputDirectory>${project.build.directory}/lib</outputDirectory>
                    </configuration>
                </execution>
            </executions>
        </plugin>
    </plugins>
</build>
```

+ 编译 jar 包
```text
mvn clean install
```

+ 执行 jar 包（找到 main 函数所在路径）
```text
格式为：
    java -classpath jar包名.jar 包名.类名

比如：
    java -classpath demo-1.0-SNAPSHOT.jar com.lyh.App
```

+ 修改 pom.xml 如下
```text
<build>
    <plugins>
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-compiler-plugin</artifactId>
            <version>3.8.1</version>
            <configuration>
                <source>1.8</source>
                <target>1.8</target>
            </configuration>
        </plugin>

        <plugin>
            <artifactId>maven-assembly-plugin</artifactId>
            <configuration>
                <appendAssemblyId>false</appendAssemblyId>
                <descriptorRefs>
                    <descriptorRef>jar-with-dependencies</descriptorRef>
                </descriptorRefs>
                <archive>
                    <manifest>
                        <!-- 此处指定main方法入口的class -->
                        <mainClass>com.lyh.App</mainClass>
                    </manifest>
                </archive>
            </configuration>
            <executions>
                <execution>
                    <id>make-assembly</id>
                    <phase>package</phase>
                    <goals>
                        <goal>assembly</goal>
                    </goals>
                </execution>
            </executions>
        </plugin>
    </plugins>
</build>
```

+ 若通过 mainClass 指定了 main 方法，则直接执行 jar 包即可，无需通过 classpath 指定
```text
格式：
    java -jar jar包 

比如：
    java -jar demo-1.0-SNAPSHOT.jar
```

## 三、遇到过的问题汇总
### 1、JDK11 中 sun.security.pkcs10 不存在
+ maven 编译时，一些内部包比如 sun.security.pkcs10 是默认隐藏的。需要在 maven-compiler-plugin 插件中使用 -XDignore.symbol.file 来解决。fork 需要设置为 true。
```text
<build>
    <finalName>cod-universal-print</finalName>
    <plugins>
      <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-jar-plugin</artifactId>
      </plugin>
      <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-compiler-plugin</artifactId>
        <version>3.2</version>
        <configuration>
          <fork>true</fork>
          <compilerArgument>-XDignore.symbol.file</compilerArgument>
        </configuration>
      </plugin>
    </plugins>
</build>
```