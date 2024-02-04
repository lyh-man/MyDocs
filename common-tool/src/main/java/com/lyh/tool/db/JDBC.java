package com.lyh.tool.db;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

/**
 * 自定义 数据库连接 （Connection、Statement、ResultSet）
 * 　　（1）加载驱动（使用Class.forName），建立连接（DriverManager）并返回连接（Connection）。
 * 　　（2）创建语句对象。（Connection 创建一个 Statement 或 PreparedStatement , 用于执行SQL语句）
 * 　　（3）执行SQL语句。（Statement 或 PreparedStatement执行SQL语句）
 * 　　（4）处理结果集。（SELECT产生结果集ResultSet）
 * 　　（5）关闭连接。（依次将ResultSet、Statement、PreparedStatement、Connection对象关闭，释放资源。 ）
 *
 * 由于 ResultSet、Statement、PreparedStatement、Connection 均实现 AutoCloseable 接口，可通过 try-with-resources 语法，自动关闭连接。
 * 不需要在 finally 中手动关闭。
 * 举例：
 *     try (Connection xx = getConnection()) {
 *
 *     } catch(Exception e) {
 *
 *     }
 */
public class JDBC {

    private static final String USER;
    private static final String PASSWORD;
    private static final String URL;
    private static final String DRIVERCLASSNAME;

    static {
        Properties properties = new Properties();
        try (InputStream inputStream = JDBC.class.getClassLoader().getResourceAsStream("dbcp.properties")) {
            properties.load(inputStream);
            USER = properties.getProperty("username");
            PASSWORD = properties.getProperty("password");
            URL = properties.getProperty("url");
            DRIVERCLASSNAME = properties.getProperty("driverClassName");

            // 加载数据库驱动程序
            Class.forName(DRIVERCLASSNAME);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load dbcp.properties.", e);
        }
    }

    public static Connection getConnection() {
        try  {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (Exception e) {
            throw new RuntimeException("SQL Exception", e);
        }
    }

}
