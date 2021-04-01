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
public class WriterId {

    @Column(name = "writer_id")
    private Long writerId;

    private WriterId(Long writerId){
        this.writerId = writerId;
    }

    public static WriterId create(Long writerId){
        return new WriterId(writerId);
    }
}
