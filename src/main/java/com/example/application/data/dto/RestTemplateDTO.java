package com.example.application.data.dto;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

@Data
@Accessors(chain = true)
public class RestTemplateDTO<T> {
    public RestTemplateDTO(Class<T> responseBodyClass){
        this.responseBodyClass = responseBodyClass;
    }
    private String url;
    private HttpMethod httpMethod;
    private HttpHeaders headers;
    private Object payload;
    private final Class<T> responseBodyClass;

}
