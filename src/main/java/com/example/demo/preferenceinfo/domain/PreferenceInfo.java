package com.example.demo.preferenceinfo.domain;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter(AccessLevel.PACKAGE)
@Table(name = "preference_info")
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

    private PreferenceInfo(Long preferredPersonId, Long documentId){
        this.preferredPersonId = preferredPersonId;
        this.documentId = documentId;

        this.preferenceStatus = PreferenceStatus.INDIFFERENCE;
    }

    public static PreferenceInfo create(Long preferredPersonId, Long documentId){
        return new PreferenceInfo(preferredPersonId, documentId );
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
