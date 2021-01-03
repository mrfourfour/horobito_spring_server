package com.example.demo.post.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
    @JoinColumn(name = "feed_id")
    @JsonIgnoreProperties("feed")
    private Feed feed;

    @Embedded
    private Writer writer;

    private LocalDateTime wrtTime;

    private String content;

    private Long likeNum;

    private boolean isDelete;
}
