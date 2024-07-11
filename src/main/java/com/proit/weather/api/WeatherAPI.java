package com.proit.weather.api;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import feign.*;
import feign.codec.ErrorDecoder;
import feign.httpclient.ApacheHttpClient;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import feign.slf4j.Slf4jLogger;
import org.slf4j.LoggerFactory;
import com.proit.weather.dto.LocationResult;
import com.proit.weather.dto.WeatherInfo;

import java.io.IOException;


@Headers({ "Content-Type: application/json", "Accept: application/json" })
public interface WeatherAPI {
   String HOURLY_PARAMETERS = "temperature_2m,rain,wind_speed_10m";
   String DAILY_PARAMETERS = "temperature_2m_max,rain_sum,wind_speed_10m_max";
   /**
    * @return {@link org.vaadin.example.dto.WeatherInfo}
    */
   @RequestLine("GET forecast?latitude={latitude}&longitude={longitude}&daily=" + DAILY_PARAMETERS + "&hourly=" + HOURLY_PARAMETERS)
   WeatherInfo getTodaysWeatherInfo(@Param("latitude") Double latitude, @Param("longitude") Double longitude);
   /**
    * @return {@link org.vaadin.example.dto.WeatherInfo}
    */
   @RequestLine("GET forecast?latitude={latitude}&longitude={longitude}&start_date={startDate}&end_date={endDate}" +
           "&daily=" + DAILY_PARAMETERS + "&hourly=" + HOURLY_PARAMETERS)
   WeatherInfo getWeatherInfoByDate(@Param("latitude") Double latitude, @Param("longitude") Double longitude,
                                    @Param("startDate") String startDate, @Param("endDate") String endDate);

   /**
    * @return {@link org.vaadin.example.dto.LocationResult}
    */
   @RequestLine("GET search?name={name}&count=100")
   LocationResult getLocations(@Param("name") String name);

   static WeatherAPI connect(final String url) {
       final ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule())
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .configure(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE, false);

      return Feign.builder().client(new ApacheHttpClient()).encoder(new JacksonEncoder(mapper))
            .decoder(new JacksonDecoder(mapper)).errorDecoder(new Status500Decoder()).logger(new Slf4jLogger())
            .logLevel(Logger.Level.FULL)
            .requestInterceptor(new ApiKeyRequestInterceptor(url))
            .target(WeatherAPI.class, url);
      }

   class Status500Decoder implements ErrorDecoder {
      private final org.slf4j.Logger logger = LoggerFactory.getLogger(getClass());
      public Exception decode(final String methodKey, final Response response) {
         if (response.status() == 500) {
            return new Default().decode(methodKey, response);
         }
         final String message = String.format("status %s reading %s", response.status(), methodKey);
         Object body = null;
         final String errorStatus = Integer.toString(response.status());
         if (response.body() != null) {
            try {
               body = Util.toByteArray(response.body().asInputStream());
            } catch (IOException e) {
               logger.debug(e.getMessage());
            }
         }
         throw new ApiException(response.status(), message, body, errorStatus);
      }
   }
}