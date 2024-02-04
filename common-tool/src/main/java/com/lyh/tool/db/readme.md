## 数据库使用说明（默认使用 postgresql 14）
+ Linux 下通过 docker 启动 postgresql
```text
docker pull postgres:14.1
docker run -p 5432:5432 -e POSTGRES_PASSWORD=postgres -v /data/postgresql/data:/var/lib/postgresql/data --name postgres -d postgres:14.1

# 如果 -e POSTGRES_PASSWORD=postgres 设定密码未生效，可手动修正密码
    docker exec -u postgres -it postgres psql
    alter user postgres with PASSWORD 'postgres';
```

## 测试 Flyway 读取并执行 sql 文件 （src/main/resources/db/migration）
+ 除 postgresql 依赖外，需再引入如下依赖
```text
<dependency>
    <groupId>org.postgresql</groupId>
    <artifactId>postgresql</artifactId>
    <version>42.3.3</version>
</dependency>

<dependency>
    <groupId>commons-dbcp</groupId>
    <artifactId>commons-dbcp</artifactId>
    <version>1.4</version>
</dependency>

<dependency>
    <groupId>org.flywaydb</groupId>
    <artifactId>flyway-core</artifactId>
    <version>3.1</version>
</dependency>
```

+ 执行 src/main/java/com/lyh/tool/db/FlywayMigrate.java
```text
读取配置文件
加载 DataSource
执行 migrate
```

## 自定义 JDBC
+ pom 文件引入的依赖
```text
<dependency>
    <groupId>org.postgresql</groupId>
    <artifactId>postgresql</artifactId>
    <version>42.3.3</version>
</dependency>
```

+ 执行 src/main/java/com/lyh/tool/db/JDBC.java
```text
读取配置文件 
加载数据库驱动 
建立 Connection 连接
```

## 读取 DB 字段映射到 Bean
+ 执行 src/main/java/com/lyh/tool/db/ColumnToProperty.java
```text
从 "DB 字段" 映射到 "Class 的 成员变量" 
类型转换 （基本类型、包装类型、java.util.Date、java.sql.Timestamp）
```