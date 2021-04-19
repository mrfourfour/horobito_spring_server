package com.example.demo.security.infrastructure;

import com.google.common.io.ByteStreams;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.ByteArrayInputStream;
import java.io.IOException;



public class ReadableRequestBodyWrapper extends HttpServletRequestWrapper {

    private final String requestBody;
    private final byte[] bytes;
    /**
     * Constructs a request object wrapping the given request.
     *
     * @param request The request to wrap
     * @throws IllegalArgumentException if the request is null
     */
    public ReadableRequestBodyWrapper(HttpServletRequest request) throws IOException {
        super(request);
        this.bytes = ByteStreams.toByteArray(super.getInputStream());
        this.requestBody = new String(bytes);
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
        return new WrapperInputStream(bais);

    }

    public String getRequestBody() {
        return requestBody;
    }

    private class WrapperInputStream extends ServletInputStream {
        private final ByteArrayInputStream bais;
        public WrapperInputStream(ByteArrayInputStream bais) {
            this.bais = bais;
        }

        @Override
        public boolean isFinished() {
            return true;
        }

        @Override
        public boolean isReady() {
            return true;
        }

        @Override
        public void setReadListener(ReadListener listener) {

        }

        @Override
        public int read() throws IOException {
            return bais.read();
        }
    }


}
