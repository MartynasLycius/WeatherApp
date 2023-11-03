package com.jsonparsing;

import com.fasterxml.jackson.databind.JsonNode;
import com.jsonparsing.pojo.SimpleTestCaseJsonPOJO;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class JsonTest
{
    String simpleTestCaseJsonSource = "{ \"title\": \"Coder From Scratch\" }";

    @Test
    void parse() throws IOException
    {
        JsonNode node = Json.parse(simpleTestCaseJsonSource);

        assertEquals(node.get("title").asText(), "Coder From Scratch");
    }

    @Test
    void fromJson() throws IOException
    {
        JsonNode node = Json.parse(simpleTestCaseJsonSource);
        SimpleTestCaseJsonPOJO pojo = Json.fromJson(node, SimpleTestCaseJsonPOJO.class);

        assertEquals(pojo.getTitle(), "Coder From Scratch");
    }
}