package com.proit.weather.utils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * 
 * @author Wahid
 *
 */
public class LocalDateSerializer extends JsonSerializer<LocalDate> {
   @Override
   public void serialize(final LocalDate value, final JsonGenerator gen, final SerializerProvider serializers) throws IOException {
      gen.writeString(DateTimeFormatter.ISO_LOCAL_DATE.format(value));
   }
}
