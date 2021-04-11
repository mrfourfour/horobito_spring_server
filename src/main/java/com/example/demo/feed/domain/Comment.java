package com.example.demo.feed.domain;


import com.example.demo.preferredperson.domain.PreferredPerson;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;

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

    @OneToMany
    private List<PreferredPerson> preferredPeople;

    @Embedded
    private Writer writer;

    private Instant wrtTime;

    @Embedded
    private Content content;

    @Embedded
    private Preference preferenceInfo;

    private boolean deleted;

    public Comment(Writer writer, Content content){
        this.writer = writer;
        this.content = content;
        this.preferenceInfo = Preference.create();
        this.wrtTime = Instant.now();
        this.deleted = false;
    }

    public String getContent(){
        return this.content.getContent();
    }


    public static Comment create(Writer writer, Content content) {
        return new Comment(writer, content);
    }

    public void likeOrDislike() {

    }

//    public boolean checkPossibleOfLike(User user) {
//        String writerId = this.getWriter().getWrtName();
//        String username = user.getUserBasicInfo().getUsername();
//        return !writerId.equals(username);
//    }
}
