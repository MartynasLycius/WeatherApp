package com.proit.application.utils;

import com.fasterxml.jackson.databind.JsonNode;

public final class JsonUtil {
    private JsonUtil() {
    }

    public static String getString(JsonNode node, String field) {
        return node.get(field) != null ? node.get(field).asText() : null;
    }

    public static Long getLong(JsonNode node, String field) {
        return node.get(field) != null ? node.get(field).asLong() : null;
    }

    public static Double getDouble(JsonNode node, String field) {
        return node.get(field) != null ? node.get(field).asDouble() : null;
    }

    public static String constructAddressFromResultNode(JsonNode resultNode) {
        String admin2 = getString(resultNode, "admin2");
        String admin1 = getString(resultNode, "admin1");

        if (admin2 != null && admin1 != null) {
            return String.format("%s, %s", admin2, admin1);
        } else if (admin2 != null) {
            return admin2;
        } else {
            return admin1;
        }
    }
}
