package com.example.demo.security.infrastructure;

import com.google.common.io.ByteStreams;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.IOException;

public class ReadableRequestBodyWrapper extends HttpServletRequestWrapper {

    private final String requestBody;
    /**
     * Constructs a request object wrapping the given request.
     *
     * @param request The request to wrap
     * @throws IllegalArgumentException if the request is null
     */
    public ReadableRequestBodyWrapper(HttpServletRequest request) throws IOException {
        super(request);
        byte[] bytes = ByteStreams.toByteArray(super.getInputStream());
        this.requestBody = new String(bytes);
    }
}
