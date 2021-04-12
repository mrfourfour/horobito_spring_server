package com.example.demo.preferredperson.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PreferredPersonRepository extends JpaRepository<PreferenceInfo, Long> {

    PreferenceInfo findByDocumentIdAndPreferredPersonId(Long documentId, Long PreferredPersonId);
}
