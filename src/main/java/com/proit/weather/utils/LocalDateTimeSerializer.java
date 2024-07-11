package com.proit.weather.utils;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * 
 * @author Wahid
 *
 */
public class LocalDateTimeSerializer extends JsonSerializer<LocalDateTime> {

   @Override
   public void serialize(final LocalDateTime value, final JsonGenerator gen, final SerializerProvider serializers) throws IOException {
      gen.writeString(DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(value));
   }
}
