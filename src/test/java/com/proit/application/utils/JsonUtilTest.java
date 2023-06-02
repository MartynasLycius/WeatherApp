package com.proit.application.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.TextNode;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JsonUtilTest {
    @Test
    void testGetString_NodeFieldExists() {
        JsonNode node = mock(JsonNode.class);
        String field = "name";
        String expectedValue = "John";

        when(node.get(field)).thenReturn(mock(JsonNode.class));
        when(node.get(field).asText()).thenReturn(expectedValue);

        String result = JsonUtil.getString(node, field);

        assertEquals(expectedValue, result);
    }

    @Test
    void testGetString_NodeFieldDoesNotExist() {
        JsonNode node = mock(JsonNode.class);
        String field = "name";

        when(node.get(field)).thenReturn(null);

        String result = JsonUtil.getString(node, field);

        assertNull(result);
    }

    @Test
    void testGetLong_NodeFieldExists() {
        JsonNode node = mock(JsonNode.class);
        String field = "temp";
        long expectedValue = (long) 25.1;

        when(node.get(field)).thenReturn(mock(JsonNode.class));
        when(node.get(field).asLong()).thenReturn(expectedValue);

        Long result = JsonUtil.getLong(node, field);

        assertEquals(expectedValue, result);
    }

    @Test
    void testGetLong_NodeFieldDoesNotExist() {
        JsonNode node = mock(JsonNode.class);
        String field = "temp";

        when(node.get(field)).thenReturn(null);

        Long result = JsonUtil.getLong(node, field);

        assertNull(result);
    }

    @Test
    void testGetDouble_NodeFieldExists() {
        JsonNode node = mock(JsonNode.class);
        String field = "price";
        double expectedValue = 19.99;

        when(node.get(field)).thenReturn(mock(JsonNode.class));
        when(node.get(field).asDouble()).thenReturn(expectedValue);

        Double result = JsonUtil.getDouble(node, field);

        assertEquals(expectedValue, result);
    }

    @Test
    void testGetDouble_NodeFieldDoesNotExist() {
        JsonNode node = mock(JsonNode.class);
        String field = "price";

        when(node.get(field)).thenReturn(null);

        Double result = JsonUtil.getDouble(node, field);

        assertNull(result);
    }

    @Test
    void testConstructAddressFromResultNode_BothAdmin2AndAdmin1Exist() {
        JsonNode resultNode = mock(JsonNode.class);
        JsonNode admin2 = new TextNode("City");
        JsonNode admin1 = new TextNode("State");
        String expectedAddress = "City, State";

        when(resultNode.get("admin2")).thenReturn(admin2);
        when(resultNode.get("admin1")).thenReturn(admin1);

        String address = JsonUtil.constructAddressFromResultNode(resultNode);

        assertEquals(expectedAddress, address);
    }

    @Test
    void testConstructAddressFromResultNode_OnlyAdmin2Exists() {
        JsonNode resultNode = mock(JsonNode.class);
        JsonNode admin2 = new TextNode("City");
        String expectedAddress = "City";

        when(resultNode.get("admin2")).thenReturn(admin2);
        when(resultNode.get("admin1")).thenReturn(null);

        String address = JsonUtil.constructAddressFromResultNode(resultNode);

        assertEquals(expectedAddress, address);
    }

    @Test
    void testConstructAddressFromResultNode_OnlyAdmin1Exists() {
        JsonNode resultNode = mock(JsonNode.class);
        JsonNode admin1 = new TextNode("State");
        String expectedAddress = "State";

        when(resultNode.get("admin2")).thenReturn(null);
        when(resultNode.get("admin1")).thenReturn(admin1);

        String address = JsonUtil.constructAddressFromResultNode(resultNode);

        assertEquals(expectedAddress, address);
    }
}