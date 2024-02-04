package com.lyh.tool.db;

import org.junit.jupiter.api.Test;

public class FlywayMigrateTest {

    @Test
    public void testMigrate()  {
        FlywayMigrate.migrate();
    }

}
