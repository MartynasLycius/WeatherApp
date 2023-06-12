package com.example.application.service.impl;

import com.example.application.data.dto.RestTemplateDTO;
import com.example.application.service.RestTemplateService;
import com.example.application.service.UtilityService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
@RequiredArgsConstructor
public class RestTemplateServiceImpl implements RestTemplateService {
    private final UtilityService utilityService;

    private final RestTemplate restTemplate;

    private final ObjectMapper objectMapper;

    @Override
    public <T> T execute(RestTemplateDTO<T> restTemplateDTO) {

        HttpEntity<Object> httpEntity = new HttpEntity<>(restTemplateDTO.getPayload(), restTemplateDTO.getHeaders());

        String curl = utilityService.getCurl(httpEntity, restTemplateDTO.getUrl(), restTemplateDTO.getHttpMethod());

        log.info("Curl: " + curl);

        T response = restTemplate.exchange(restTemplateDTO.getUrl(), restTemplateDTO.getHttpMethod(), httpEntity, restTemplateDTO.getResponseBodyClass()).getBody();

        try {
            log.info("response " + objectMapper.writeValueAsString(response));
        } catch (JsonProcessingException ignored) {
        }

        return response;
    }
}
