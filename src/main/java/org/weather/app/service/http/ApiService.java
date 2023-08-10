package org.weather.app.service.http;

import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;

public class ApiService<TYPE> {

  private String baseUrl;
  private String apiPath;
  private MultiValueMap<String, String> params;
  private Class<TYPE> responseModel;
  private WebClient webClient;

  public ApiService(Class<TYPE> responseModel) {
    this.responseModel = responseModel;
  }

  public TYPE get() {
    webClient = WebClient.builder().baseUrl(baseUrl).build();
    return sendRequest();
  }

  private TYPE sendRequest() {
    return webClient
        .get()
        .uri(uriBuilder -> uriBuilder.path(apiPath).queryParams(params).build())
        .retrieve()
        .bodyToMono(responseModel)
        .block();
  }

  public String getBaseUrl() {
    return baseUrl;
  }

  public void setBaseUrl(String baseUrl) {
    this.baseUrl = baseUrl;
  }

  public String getApiPath() {
    return apiPath;
  }

  public void setApiPath(String apiPath) {
    this.apiPath = apiPath;
  }

  public MultiValueMap<String, String> getParams() {
    return params;
  }

  public void setParams(MultiValueMap<String, String> params) {
    this.params = params;
  }
}
