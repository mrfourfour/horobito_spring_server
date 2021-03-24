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
@Setter(AccessLevel.PACKAGE)
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

    @Embedded
    private Content content;

    @Embedded
    private Preference preference;

    private boolean deleted;

    public Comment(Writer writer, Content content){
        this.writer = writer;
        this.content = content;
    }


    public static Comment create(Writer writer, Content content) {
        return new Comment(writer, content);
    }

    public void likeOrDislike() {

    }

    public boolean checkPossibleOfLike(User user) {
        String writerId = this.getWriter().getWrtName();
        String username = user.getUserBasicInfo().getUsername();
        return !writerId.equals(username);
    }
}
