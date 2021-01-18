package com.example.demo.post.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
public class Feed {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;



    @Embedded
    private Writer writer;

    private String content;

    private Long likeNum;

    private Feed(Writer writer, String content){
        this.writer = writer;
        this.content = content;
    }

    public void delete(){
        this.isDelete=true;
    }


    @OneToMany(mappedBy = "feed", cascade = {CascadeType.PERSIST})
    @JsonIgnoreProperties("feed")
    private List<Comment> comments;

    private LocalDateTime wrtTime;

    private boolean isDelete;

    public static Feed createFeed(Writer writer, String contents) {
        return new Feed(writer, contents);
    }

    public void enrollComment(Comment comment) {
        comment.setFeed(this);
        comments.add(comment);
    }
}
