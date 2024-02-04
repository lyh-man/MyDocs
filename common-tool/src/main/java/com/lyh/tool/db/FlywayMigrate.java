package com.lyh.tool.db;

import org.apache.commons.dbcp.BasicDataSourceFactory;
import org.flywaydb.core.Flyway;

import javax.sql.DataSource;
import java.io.InputStream;
import java.util.Properties;

/**
 * Flyway 执行 DB init (migrate)
 */
public class FlywayMigrate {

    protected static final DataSource SOURCE;
    static {
        Properties properties = new Properties();
        try (InputStream inputStream = FlywayMigrate.class.getClassLoader().getResourceAsStream("dbcp.properties")) {
            properties.load(inputStream);
            SOURCE = BasicDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            throw new Error("Failed to create DataSource.", e);
        }
    }

    /**
     * classpath:db/migration
     */
    public static void migrate() {
        Flyway flyway = new Flyway();
        flyway.setDataSource(SOURCE);
        flyway.migrate();
    }
}
