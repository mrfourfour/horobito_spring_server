package com.example.demo.security.service;


import com.example.demo.security.infrastructure.UserAuthenticationFilter;
import com.example.demo.user.domain.Authority;
import com.example.demo.user.domain.UserRepository;
import com.example.demo.user.domain.Username;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.SecurityUser;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    private final UserRepository userRepository;

    private final UserAuthenticationFilter userAuthenticationFilter;

    public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        FilterChain filterChain = new FilterChain() {
            @Override
            public void doFilter(ServletRequest request, ServletResponse response) throws IOException, ServletException {

            }
        };

        userAuthenticationFilter.doFilter(request, response, filterChain );
    }




    @Override
    public UserDetails loadUserByUsername(String username){
        Username name = Username.create(username);

        Optional<User> user1 = userRepository
                .findUserByUserBasicInfo_Username(name)
                .map(user -> new SecurityUser(
                        user.getId(),
                        user.getUserBasicInfo().getUsername(),
                        user.getUserBasicInfo().getPassword(),
                        user.getAuthorities()
                                .stream()
                                .map(Authority::getAuthority)
                                .map(SimpleGrantedAuthority::new)
                                .collect(Collectors.toSet())
                ));
        return user1.orElseThrow(
                ()-> new UsernameNotFoundException("Can't find by your username"));
    }
}
