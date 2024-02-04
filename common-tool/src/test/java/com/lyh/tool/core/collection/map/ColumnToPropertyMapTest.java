package com.lyh.tool.core.collection.map;

import com.lyh.tool.db.bean.SchemaVersion;
import org.junit.jupiter.api.Test;

public class ColumnToPropertyMapTest {

    private static final ColumnToPropertyMap MAP = SchemaVersion.getMap();

    @Test
    public void testGetKeys() {
        System.out.println(MAP.getKeys());
    }

    @Test
    public void testGetEntrySet() {
        System.out.println(MAP.getEntrySet());
    }

    @Test
    public void testGetMapKeysToString() {
        System.out.println(MAP.getMapKeysToString());
    }
}
