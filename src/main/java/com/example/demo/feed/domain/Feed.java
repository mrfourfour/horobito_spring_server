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

    @Column(name = "preference")
    private Preference preferenceInfo;

    @Column(name = "wrt_time")
    private LocalDateTime wrtTime;

    private Boolean deleted;

    @OneToMany(mappedBy = "feed", cascade = {CascadeType.PERSIST})
    @JsonIgnoreProperties("feed")
    private List<Comment> comments;

    private Feed(Writer writer, Content content){
        this.writer = writer;
        this.content = content;
        this.wrtTime = LocalDateTime.now();
        this.preferenceInfo = Preference.create();
        this.deleted = false;
    }


    public void delete(){
        this.deleted =true;
    }

    public static Feed create(Writer writer, Content content) {
        return new Feed(writer, content);
    }

    public void like(){
        this.preferenceInfo.like();
    }

    public void enrollComment(Comment comment) {
        comment.setFeed(this);
        comments.add(comment);
    }


    public Comment getComment(int commentId) {
        return comments.get(commentId);
    }
}
