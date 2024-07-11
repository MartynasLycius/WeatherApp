package com.proit.weather.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * 
 * @author Wahid
 *
 */
public class LocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {
   @Override
   public LocalDateTime deserialize(final JsonParser arg0, final DeserializationContext arg1) throws IOException {
      try {
         return LocalDateTime.parse(arg0.getText(), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
      } catch (final DateTimeParseException ex) {
         return null;
      }
   }
}
