package com.example.demo.security.infrastructure;

import com.example.demo.user.controller.UserController;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JsonParseException;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;




@Component
public class UserAuthenticationFilter extends BasicAuthenticationFilter {

    private final ObjectMapper objectMapper = new ObjectMapper();



    @Autowired
    public UserAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }


    public UserAuthenticationFilter(AuthenticationManager authenticationManager, AuthenticationEntryPoint authenticationEntryPoint) {
        super(authenticationManager, authenticationEntryPoint);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        ReadableRequestBodyWrapper readableRequestBodyWrapper
                = new ReadableRequestBodyWrapper(request);

        String requestBody = readableRequestBodyWrapper.getRequestBody();
        UserController.SignupParameter parameter;
        try {
            parameter=
                    objectMapper.readValue(requestBody,
                            UserController.SignupParameter.class);
        }catch (JsonParseException e){
            chain.doFilter(request, response);

            return;
        }

        String username = parameter.getUsername();
        String password = parameter.getPassword();

        UsernamePasswordAuthenticationToken token
                = new UsernamePasswordAuthenticationToken(username, password);

        try {
            // 인증 요구되는 상황
            if (authenticationIsRequired(username)){
                Authentication authResult
                        = this.getAuthenticationManager().authenticate(token);
                SecurityContextHolder.getContext().setAuthentication(authResult);
                onSuccessfulAuthentication(request, response, authResult);
            }
        }catch (AuthenticationException ex){
            // 인증이 실패한 상황
            SecurityContextHolder.clearContext();
            onUnsuccessfulAuthentication(request, response, ex);

            chain.doFilter(request, response);

            return;
        }

        chain.doFilter(request, response);
    }

    private boolean authenticationIsRequired(String username) {
        // Only reauthenticate if username doesn't match SecurityContextHolder and user
        // isn't authenticated (see SEC-53)
        Authentication existingAuth = SecurityContextHolder.getContext().getAuthentication();
        if (existingAuth == null || !existingAuth.isAuthenticated()) {
            return true;
        }
        // Limit username comparison to providers which use usernames (ie
        // UsernamePasswordAuthenticationToken) (see SEC-348)
        if (existingAuth instanceof UsernamePasswordAuthenticationToken && !existingAuth.getName().equals(username)) {
            return true;
        }
        // Handle unusual condition where an AnonymousAuthenticationToken is already
        // present. This shouldn't happen very often, as BasicProcessingFitler is meant to
        // be earlier in the filter chain than AnonymousAuthenticationFilter.
        // Nevertheless, presence of both an AnonymousAuthenticationToken together with a
        // BASIC authentication request header should indicate reauthentication using the
        // BASIC protocol is desirable. This behaviour is also consistent with that
        // provided by form and digest, both of which force re-authentication if the
        // respective header is detected (and in doing so replace/ any existing
        // AnonymousAuthenticationToken). See SEC-610.
        return (existingAuth instanceof AnonymousAuthenticationToken);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        if(request.getRequestURI().startsWith("/account/**")){
            return true;
        }
        return false;
    }
}
