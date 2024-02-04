package com.lyh.tool.db;

import com.lyh.tool.core.collection.map.ColumnToPropertyMap;
import com.lyh.tool.db.bean.SchemaVersion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ColumnToPropertyTest {

    @BeforeEach
    public void before() {
        System.out.println("Flyway migrate start...");
        FlywayMigrate.migrate();
        System.out.println("Flyway migrate end...");
    }

    private static final String TEST_SQL = "SELECT %s FROM schema_version";

    @Test
    public void testMapColumnToProperty() {
        ColumnToPropertyMap map = SchemaVersion.getMap();
        try (Connection connection = JDBC.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(String.format(TEST_SQL, map.getMapKeysToString()));
             ResultSet resultSet = preparedStatement.executeQuery()) {
            List<SchemaVersion> list = new ArrayList<>();
            while(resultSet.next()) {
                list.add(ColumnToProperty.mapColumnToProperty(resultSet, SchemaVersion.class, map));
            }
            System.out.println(list);
        } catch (Exception e) {
            throw new RuntimeException("SQL Exception", e);
        }
    }
}
