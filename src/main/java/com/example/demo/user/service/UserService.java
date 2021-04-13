package com.example.demo.user.service;





import com.example.demo.user.domain.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.AccessDeniedException;


@Service
@RequiredArgsConstructor
public class UserService  {

    private final UserRepository userRepository;
    private final UserSessionService userSessionService;

    @Transactional
    public void signup(SignupRequest signupRequest){
        Username username = Username.create(signupRequest.getUsername());
        Password password = Password.create(signupRequest.getPassword());

        Authority authority = new Authority(signupRequest.getAuthorities());
        User user = User.create(username, password);
        user.addAuthorities(authority);
        userRepository.save(user);
    }

    public void login(LoginRequest loginRequest) {

    }

    public Object[] findUserInfo() throws AccessDeniedException {
        User user = userSessionService.getLoggeddUser();
        return new Object[]{user.getId(), user.getUserBasicInfo().getUsername()};
    }

    public Object[] findUserInfo(Long id) throws AccessDeniedException {
        User user = userRepository.findUserById(id);
        return new Object[]{user.getId(),
                user.getUserBasicInfo().getUsername()};
    }
}

