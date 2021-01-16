package com.example.demo.user.domain;


import lombok.*;


import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity
@Table
@Getter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // = code

    @Column(nullable = false, unique = true)
    private String userId; // = email

    @Column(nullable = false)
    private String password; // = passowrd

    @Setter
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRole role;



    @OneToMany(mappedBy = "feed", cascade = {CascadeType.PERSIST})
    private List<Friend> friends;



}
