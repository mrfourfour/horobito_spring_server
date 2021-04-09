package com.example.demo.user.domain;

import lombok.*;


import javax.persistence.*;
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
    private UserBasicInfo userBasicInfo;


    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST)
    private Set<Authority> authorities = new HashSet<>();

    public User(UserBasicInfo userBasicInfo){
        this.userBasicInfo = userBasicInfo;
    }

    public static User create(Username username, Password password) {
        UserBasicInfo userBasicInfo = new UserBasicInfo(username, password);
        return new User(userBasicInfo);
    }

    public void addAuthorities(Authority authority){
        authority.setUser(this);
        this.authorities.add(authority);
    }


}
