package com.example.demo.user.service;




import com.example.demo.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService  {

    private final UserRepository userRepository;



}
