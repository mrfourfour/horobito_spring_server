package com.example.demo.security.infrastructure;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;


@Component
public class UserAuthenticationFilter extends BasicAuthenticationFilter {
    public UserAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    public UserAuthenticationFilter(AuthenticationManager authenticationManager, AuthenticationEntryPoint authenticationEntryPoint) {
        super(authenticationManager, authenticationEntryPoint);
    }
}
