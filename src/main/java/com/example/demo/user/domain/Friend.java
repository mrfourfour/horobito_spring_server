package com.example.demo.user.domain;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "friend")
@Getter
@Setter
public class Friend {

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


}
