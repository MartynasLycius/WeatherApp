package com.waheduzzaman.MeteoWeather.utility;

import com.vaadin.flow.spring.annotation.SpringComponent;
import elemental.json.Json;
import elemental.json.JsonObject;
import org.springframework.beans.factory.annotation.Value;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

@SpringComponent
public class WMOCodeToOpenWeatherIcon {

    @Value("${wmo.code.json.file}")
    private String WMO_MAPPING_JSON_FILE;
    private JsonObject rootJsonObject;

    public String convertCodeToIconLink(Integer wmoCode) {
        if (rootJsonObject == null)
            loadJSON();
        return rootJsonObject.getObject(wmoCode.toString()).getObject("day").getString("image");
    }

    private void loadJSON() {
        String jsonString = getResourceFileAsString();
        rootJsonObject = Json.parse(jsonString);
    }

    private String getResourceFileAsString() {
        InputStream is = getResourceFileAsInputStream();
        if (is != null) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            return reader.lines().collect(Collectors.joining(System.lineSeparator()));
        } else throw new RuntimeException("resource not found");
    }

    private InputStream getResourceFileAsInputStream() {
        ClassLoader classLoader = this.getClass().getClassLoader();
        return classLoader.getResourceAsStream(WMO_MAPPING_JSON_FILE);
    }
}
