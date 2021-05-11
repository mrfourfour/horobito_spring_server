package com.example.demo.user.service;





import com.example.demo.user.domain.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.AccessDeniedException;


@Service
@RequiredArgsConstructor
public class UserService  {

    private final UserRepository userRepository;
    private final UserSessionService userSessionService;



    public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {



    }



    @Transactional
    public void signup(SignupRequest signupRequest){
        Username username = Username.create(signupRequest.getUsername());
        Password password = Password.create(signupRequest.getPassword());

        Authority authority = new Authority(signupRequest.getAuthorities());
        User user = User.create(username, password);
        user.addAuthorities(authority);
        userRepository.save(user);
    }




    public UserDto findUserInfo() throws AccessDeniedException {
        User user = userSessionService.getLoggeddUser();
        return toUserDto(user);
    }

    public UserDto findUserInfo(Long id) throws AccessDeniedException {
        User user = userRepository.findUserById(id);
        return toUserDto(user);
    }

    public UserDto toUserDto(User user){
        return UserDto.create(
                user.getId(),
                user.getUserBasicInfo().getUsername()
        );
    }
}

