package com.proit.weather.api;

import feign.RequestInterceptor;
import feign.RequestTemplate;

/**
 * @author wahid
 */
public class ApiKeyRequestInterceptor implements RequestInterceptor {

    private final String uri;

    public ApiKeyRequestInterceptor(String uri) {
        if (uri == null)
            throw new IllegalArgumentException("Required authentication fields can't be null");
        this.uri = uri;
    }

    @Override
    public void apply(RequestTemplate template) {
    }
}
