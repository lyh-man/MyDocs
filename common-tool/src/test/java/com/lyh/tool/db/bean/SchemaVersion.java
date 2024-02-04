package com.lyh.tool.db.bean;

import com.lyh.tool.core.collection.map.ColumnToPropertyMap;
import lombok.Data;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

/**
 * Flyway 执行后生成的 schema_version 表结构 与 Bean 之间的映射
 */
@Data
public class SchemaVersion {
    private Integer versionRank;
    private Integer installedRank;
    private String version;
    private String description;
    private String type;
    private String script;
    private Integer checksum;
    private String installedBy;
    private Timestamp installedOn;
    private Integer executionTime;
    private Boolean success;
    private static final ColumnToPropertyMap COLUMN_TO_PROPERTY_MAP;

    static {
        Map<String, String> map = new HashMap<>();
        map.put("version_rank", "versionRank");
        map.put("installed_rank", "installedRank");
        map.put("version", "version");
        map.put("description", "description");
        map.put("type", "type");
        map.put("script", "script");
        map.put("checksum", "checksum");
        map.put("installed_by", "installedBy");
        map.put("installed_on", "installedOn");
        map.put("execution_time", "executionTime");
        map.put("success", "success");
        COLUMN_TO_PROPERTY_MAP = new ColumnToPropertyMap(map);
    }

    public static ColumnToPropertyMap getMap() {
        return COLUMN_TO_PROPERTY_MAP;
    }

}


