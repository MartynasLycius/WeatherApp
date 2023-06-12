package com.example.application.service;


import com.example.application.data.dto.RestTemplateDTO;

public interface RestTemplateService {
    <T> T execute(RestTemplateDTO<T> restTemplateDTO);
}
