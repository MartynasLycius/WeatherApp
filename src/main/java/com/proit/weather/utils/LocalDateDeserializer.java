package com.proit.weather.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * 
 * @author Wahid
 *
 */
public class LocalDateDeserializer extends JsonDeserializer<LocalDate> {
   @Override
   public LocalDate deserialize(final JsonParser arg0, final DeserializationContext arg1) throws IOException {
      try {
         return LocalDate.parse(arg0.getText(), DateTimeFormatter.ISO_LOCAL_DATE);
      } catch (final DateTimeParseException ex) {
         return null;
      }
   }
}
