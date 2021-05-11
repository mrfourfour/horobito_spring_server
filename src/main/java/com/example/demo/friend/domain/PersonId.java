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
public class PersonId {



    private Long id;

    private PersonId(Long id) {
        this.id = id;
    }

    public static PersonId create(Long id){
        return new PersonId(id);
    }


}
