package com.example.demo.feed.domain;


import com.example.demo.user.domain.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "comment")
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

    public Comment(Writer writer, String content){
        this.writer = writer;
        this.content = content;
    }


    public static Comment makeComment(Writer writer, String contents) {
        return new Comment(writer, contents);
    }

    public void likeOrDislike() {

    }

    public boolean checkPossibleOfLike(User user) {
        String writerId = this.getWriter().getWrtName();
        String username = user.getUserId();
        return !writerId.equals(username);
    }
}
