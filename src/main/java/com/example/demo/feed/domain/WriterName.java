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
public class WriterName {

    @Column(name = "writer_name")
    private String writerName;

    private WriterName(String writerName){
        this.writerName = writerName;
    }

    public static WriterName create(String writerName){
        return new WriterName(writerName);
    }
}
