package com.example.demo.post.domain;


import javax.persistence.MappedSuperclass;
import java.time.Instant;

@MappedSuperclass
public class BaseEntity {

    protected Instant createAt = Instant.now();

    protected Instant updatedAt = Instant.now();
}
