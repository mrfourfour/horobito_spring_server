package com.example.demo.user.domain;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "user")
@Getter
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userId;

    private String password;

    private String auth;


    @OneToMany(mappedBy = "feed", cascade = {CascadeType.PERSIST})
    private List<Friend> friends;



}
