package com.example.demo.preperredPerson.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PreferredPersonRepository extends JpaRepository<PreferredPerson, Long> {

    PreferredPerson findByDocumentIdAndPreferredPersonId(Long documentId, Long PreferredPersonId);
}
