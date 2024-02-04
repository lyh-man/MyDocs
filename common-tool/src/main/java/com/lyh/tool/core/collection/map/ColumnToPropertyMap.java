package com.lyh.tool.core.collection.map;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

public class ColumnToPropertyMap {
    private Map<String, String> map;

    public ColumnToPropertyMap(Map<String, String> map) {
        this.map = Collections.unmodifiableMap(map);
    }

    public Set<String> getKeys() {
        return map.keySet();
    }

    public Set<Map.Entry<String, String>> getEntrySet() {
        return map.entrySet();
    }

    public String getValue(String key) {
        if (map.containsKey(key)) {
            return map.get(key);
        }
        return null;
    }

    public String getMapKeysToString() {
        StringBuilder keysBuilder = new StringBuilder();
        for (String key : getKeys()) {
            keysBuilder.append(key);
            keysBuilder.append(",");
        }
        keysBuilder.deleteCharAt(keysBuilder.length() - 1);
        return keysBuilder.toString();
    }
}
