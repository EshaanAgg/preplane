package com.preplane.dev.assets;

import org.springframework.http.HttpStatusCode;

public class SQLResult<T> {
    public HttpStatusCode statusCode;
    public String message;
    public T response;

    public SQLResult() {
    }

    public SQLResult(T response, String message, HttpStatusCode statusCode) {
        this.statusCode = statusCode;
        this.message = message;
        this.response = response;
    }

}
