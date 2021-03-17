package com.example.demo.user.domain;

import com.example.demo.friend.domain.Friendship;
import lombok.*;


import javax.persistence.*;
import java.io.Serializable;
import java.util.*;


@Entity
@Getter
@Setter(AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_pk")
    private Long id; // = code


    @Embedded
    private Username username; // = email

    @Embedded
    private Password password; // = passowrd

    @OneToMany(mappedBy = "user")
    private List<Friendship> friendships = new ArrayList<>();


    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST)
    private Set<Authority> authorities = new HashSet<>();

    public User(Username username, Password password){
        this.username = username;
        this.password = password;
    }

    public static User createUser(Username username, Password password) {
        return new User(username, password);
    }

    public void addAuthorities(Authority authority){
        authority.setUser(this);
        this.authorities.add(authority);
    }


}
