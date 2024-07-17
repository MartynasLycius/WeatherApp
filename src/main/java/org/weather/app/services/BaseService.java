package org.weather.app.services;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

public abstract class BaseService<T> {

    private final HttpClient client;

    public BaseService() {
        this.client = HttpClient.newHttpClient();
    }

    protected CompletableFuture<T> fetch(String apiUrl, java.util.function.Function<String, T> responseParser) {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(apiUrl)).build();
        return client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenApply(responseParser);
    }
}
