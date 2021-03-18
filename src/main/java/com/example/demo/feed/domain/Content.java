package com.example.demo.feed.domain;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Getter
@Setter
@Embeddable
@NoArgsConstructor
public class Content {

    @Column(name = "content")
    private String content;

    private Content(String contentParameter){
        this.content = contentParameter;
    }

    public static Content createContent(String contents) {
        return new Content(contents);
    }
}
