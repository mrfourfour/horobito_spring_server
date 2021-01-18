package com.example.demo.friend.domain;


import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@NoArgsConstructor
public class Owner {

    @Column(name = "owner_id")
    private Long id;

    @Column(name = "owner_name")
    private String ownerName;

    public Owner(Long id, String ownerName) {
        this.id = id;
        this.ownerName = ownerName;
    }
}
