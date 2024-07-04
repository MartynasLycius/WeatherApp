package com.nashid.weatherapp.core.utils;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.nashid.weatherapp.core.api.WeatherApi;

import java.io.IOException;
import java.time.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class UnixTimestampListDeserializer extends JsonDeserializer<List<Date>> {

    @Override
    public List<Date> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        List<Date> finalList = new ArrayList<>();
        JsonNode jsonNode = jsonParser.getCodec().readTree(jsonParser);
        for (JsonNode timeNode : jsonNode) {
            Instant instant = Instant.ofEpochSecond(timeNode.asLong());
            ZoneId zoneId = ZoneId.of(WeatherApi.DEFAULT_TIMEZONE);
            ZonedDateTime zonedDateTime = instant.atZone(zoneId);
            finalList.add(Date.from(zonedDateTime.toInstant()));
        }
        Collections.sort(finalList);
        return finalList;
    }
}
