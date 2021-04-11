package com.example.demo.preferredperson.domain;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter(AccessLevel.PACKAGE)
@Entity
@NoArgsConstructor
public class PreferredPerson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long preferredPersonId;

    private PreferredPersonInfoLocation location;

    private Long documentId;

    private PreferenceStatus preferenceStatus;

    private PreferredPerson(Long preferredPersonId, Long documentId, PreferredPersonInfoLocation location  ){
        this.preferredPersonId = preferredPersonId;
        this.documentId = documentId;
        this.location = location;
        this.preferenceStatus = PreferenceStatus.DISLIKE;
    }

    public static PreferredPerson create(Long preferredPersonId, Long documentId, PreferredPersonInfoLocation location ){
        return new PreferredPerson(preferredPersonId, documentId, location );
    }


    public void like() {
        this.preferenceStatus = PreferenceStatus.LIKE;
    }
}
