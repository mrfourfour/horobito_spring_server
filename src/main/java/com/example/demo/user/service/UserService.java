package com.example.demo.user.service;





import com.example.demo.user.application.LoginRequest;
import com.example.demo.user.domain.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class UserService  {

    private final UserRepository userRepository;

    @Transactional
    public void signup(SignupRequest signupRequest){
        Username username = new Username(signupRequest.getUsername());
        Password password = new Password(signupRequest.getPassword());

        Authority authority = new Authority(signupRequest.getAuthorities());
        User user = User.createUser(username, password);
        user.addAuthorities(authority);
        userRepository.save(user);
    }

    public void login(LoginRequest loginRequest) {

    }
}

