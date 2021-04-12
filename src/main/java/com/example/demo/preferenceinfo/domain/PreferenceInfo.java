package com.example.demo.preferenceinfo.domain;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter(AccessLevel.PACKAGE)
@Entity
@NoArgsConstructor
public class PreferenceInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long preferredPersonId;

    private PreferenceLocation location;

    private Long documentId;

    private PreferenceStatus preferenceStatus;

    private PreferenceInfo(Long preferredPersonId, Long documentId, PreferenceLocation location  ){
        this.preferredPersonId = preferredPersonId;
        this.documentId = documentId;
        this.location = location;
        this.preferenceStatus = PreferenceStatus.DISLIKE;
    }

    public static PreferenceInfo create(Long preferredPersonId, Long documentId, PreferenceLocation location ){
        return new PreferenceInfo(preferredPersonId, documentId, location );
    }


    public void like() {
        this.preferenceStatus = PreferenceStatus.LIKE;
    }

    public void locate(PreferenceLocation location) {
        this.location = location;
    }

    public PreferenceStatus findState() {
        return this.preferenceStatus;
    }

    public void disLike() {
        this.preferenceStatus = PreferenceStatus.INDIFFERENCE;
    }
}
