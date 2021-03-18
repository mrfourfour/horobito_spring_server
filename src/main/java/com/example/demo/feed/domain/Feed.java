package com.example.demo.feed.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "feed")
@Getter
@Setter(AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Feed {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private Writer writer;

    @Embedded
    private Content content;

    private Long likeNum;

    private LocalDateTime wrtTime;

    @Column(name = "is_delete")
    private Boolean isDeleted;

    private Feed(Writer writer, Content content){
        this.writer = writer;
        this.content = content;
    }

    public void delete(){
        this.isDeleted =true;
    }
    @OneToMany(mappedBy = "feed", cascade = {CascadeType.PERSIST})
    @JsonIgnoreProperties("feed")
    private List<Comment> comments;



    public static Feed createFeed(Writer writer, String contents) {
        return new Feed(writer, contents);
    }

    public void enrollComment(Comment comment) {
        comment.setFeed(this);
        comments.add(comment);
    }
}
