package com.example.demo.friend.domain;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;

@Getter
@Setter(AccessLevel.PROTECTED)
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Identfication {


    private Long id;

    private Identfication(Long id) {
        this.id = id;
    }

    public static Identfication create(Long id){
        return new Identfication(id);
    }


}
