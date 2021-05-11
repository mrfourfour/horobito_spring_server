package com.example.demo.feed.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;
import java.util.ArrayList;
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
    private Title title;

    @Embedded
    private Content content;

    @Column(name = "preference")
    private PreferenceCount preferenceCountInfo;

    @Column(name = "wrt_time")
    private Instant wrtTime;

    private Boolean deleted;


    @OneToMany(mappedBy = "feed", cascade = {CascadeType.PERSIST})
    @JsonIgnoreProperties("feed")
    private List<Comment> comments = new ArrayList<>();

    private Feed(Writer writer, Title title, Content content){
        this.writer = writer;
        this.content = content;
        this.wrtTime = Instant.now();
        this.preferenceCountInfo = PreferenceCount.create();
        this.title = title;
        this.deleted = false;
    }


    public void delete(){
        this.deleted =true;
    }

    public static Feed create(Writer writer, Title title,  Content content) {
        return new Feed(writer, title, content);
    }

    public void like(){
        this.preferenceCountInfo = this.preferenceCountInfo.like();
    }

    public void enrollComment(Comment comment) {
        comment.setFeed(this);
        comments.add(comment);
    }


    public Comment getComment(int commentId) {
        return comments.get(commentId);
    }

    public void disLike() {
        this.preferenceCountInfo = PreferenceCount.create(this.preferenceCountInfo.getPreference()-1L);
    }
}
