package com.example.demo.friend.domain;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;

@Getter
@Setter(AccessLevel.PACKAGE)
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PersonName {

    private String name;

    private PersonName(String name) {
        this.name = name;
    }

    public static PersonName create(String name){
        return new PersonName(name);
    }
}
