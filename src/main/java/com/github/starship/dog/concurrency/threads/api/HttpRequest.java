package com.github.starship.dog.concurrency.threads.api;

import lombok.Data;

import java.util.Map;

@Data
public class HttpRequest {
    private final String method;
    private final String uri;
    private final String protocol;
    private Map<String, String> headers;
    private char[] body;

    public String getBody() {
        return new String(body);
    }
}
