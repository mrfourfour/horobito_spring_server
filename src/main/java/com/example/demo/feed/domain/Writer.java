package com.example.demo.feed.domain;


import com.example.demo.user.domain.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Writer {

    @Column(name = "writer_id")
    private Long id;

    @Column(name = "writer_name")
    private String wrtName;

    public Writer(Long id, String wrtName) {
        this.id = id;
        this.wrtName = wrtName;
    }

    public static Writer makeWriter(User user) {
        return new Writer(user.getId(), user.getUserBasicInfo().getUsernameInfo());
    }
}
