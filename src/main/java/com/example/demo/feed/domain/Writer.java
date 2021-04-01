package com.example.demo.feed.domain;


import com.example.demo.user.domain.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Writer {

    @Embedded
    private WriterId id;

    @Embedded
    private WriterName wrtName;

    public Writer(WriterId id, WriterName wrtName) {
        this.id = id;
        this.wrtName = wrtName;
    }

    public static Writer create(WriterId wrtId, WriterName wrtName) {
        return new Writer(wrtId, wrtName);
    }
}
