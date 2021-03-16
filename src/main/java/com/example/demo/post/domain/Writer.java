package com.example.demo.post.domain;

import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@NoArgsConstructor
public class Writer {

    @Column(name = "writer_id")
    private Long id;

    @Column(name = "writer_name")
    private String wrtName;

    public Writer(Long id, String wrtName) {
        this.id = id;
        this.wrtName = wrtName;
    }
}