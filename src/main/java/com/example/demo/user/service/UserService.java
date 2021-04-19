package com.example.demo.user.service;





import com.example.demo.security.infrastructure.UserAuthenticationFilter;
import com.example.demo.security.service.UserDetailsService;
import com.example.demo.user.domain.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.AccessDeniedException;


@Service
@RequiredArgsConstructor
public class UserService  {

    private final UserRepository userRepository;
    private final UserSessionService userSessionService;
    private final UserAuthenticationFilter userAuthenticationFilter;


    @Transactional
    public void signup(SignupRequest signupRequest){
        Username username = Username.create(signupRequest.getUsername());
        Password password = Password.create(signupRequest.getPassword());

        Authority authority = new Authority(signupRequest.getAuthorities());
        User user = User.create(username, password);
        user.addAuthorities(authority);
        userRepository.save(user);
    }



    public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        FilterChain filterChain = new FilterChain() {
            @Override
            public void doFilter(ServletRequest request, ServletResponse response) throws IOException, ServletException {

            }
        };

        userAuthenticationFilter.doFilter(request, response, filterChain );
    }


    public String[] findUserInfo() throws AccessDeniedException {
        User user = userSessionService.getLoggeddUser();
        return new String[]{String.valueOf(user.getId()), user.getUserBasicInfo().getUsername()};
    }

    public String[] findUserInfo(Long id) throws AccessDeniedException {
        User user = userRepository.findUserById(id);
        return new String[]{String.valueOf(user.getId()),
                user.getUserBasicInfo().getUsername()};
    }
}

