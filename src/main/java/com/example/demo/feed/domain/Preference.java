package com.example.demo.feed.domain;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Getter
@Setter(AccessLevel.PACKAGE)
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Preference {

    @Column(name = "preference")
    private long preference = 0L;

    private Preference(long like){
        this.preference = like;
    }


    public Preference like(){
        return new Preference(this.preference +1L);
    }


    public static Preference create(){
        return new Preference();
    }


}
