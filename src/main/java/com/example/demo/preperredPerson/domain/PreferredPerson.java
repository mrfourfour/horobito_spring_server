package com.example.demo.preperredPerson.domain;


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

    private PreferenceStatus preferenceStatus;




}
