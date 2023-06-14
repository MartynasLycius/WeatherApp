package com.example.application.service.impl;


import com.example.application.service.UtilityService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class UtilityServiceImpl implements UtilityService {

    @Override
    public String getCurl(HttpEntity<?> httpEntity, String url, HttpMethod httpMethod) {

        try {
            ObjectMapper mapper = new ObjectMapper();
            StringBuilder curl = new StringBuilder();
            curl.append("curl --request ").append(httpMethod).append(" '").append(url).append("'");

            String body;

            for (Map.Entry<String, List<String>> entry : httpEntity.getHeaders().entrySet())
                curl.append(" --header '").append(entry.getKey()).append(": ").append(entry.getValue().stream().findFirst().orElse(null)).append("'");
            if (httpEntity.getHeaders().entrySet().stream().findFirst().isPresent() && httpEntity.getHeaders().entrySet().stream().findFirst().get().getValue().contains("application/x-www-form-urlencoded")) {
                MultiValueMap<String, String> multiValueMapRequest;
                multiValueMapRequest = (MultiValueMap<String, String>) httpEntity.getBody();

                for (Map.Entry<String, List<String>> entry : Objects.requireNonNull(multiValueMapRequest).entrySet()) {
                    curl.append(" --data-urlencode '").append(entry.getKey()).append("=").append(entry.getValue().stream().findFirst().orElse(null)).append("'");
                }
            } else {
                if (httpEntity.getBody() instanceof String) {
                    curl.append(" --header 'Content-Type: text/plain'");
                    body = (String) httpEntity.getBody();
                } else {
                    curl.append(" --header 'Content-Type: application/json'");
                    body = mapper.writeValueAsString(httpEntity.getBody());
                }
                curl.append(" --data ").append("'").append(body).append("'");
            }
            return curl.toString();
        } catch (Exception e) {
            return "Exception occurred";
        }

    }

}
