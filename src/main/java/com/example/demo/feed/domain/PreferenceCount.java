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
public class PreferenceCount {

    @Column(name = "preference")
    private long preference = 0L;

    private PreferenceCount(long like){
        this.preference = like;
    }


    public PreferenceCount like(){
        return new PreferenceCount(this.preference +1L);
    }




    public static PreferenceCount create(){
        return new PreferenceCount();
    }

    public static PreferenceCount create(long preference){
        return new PreferenceCount(preference);
    }


}
