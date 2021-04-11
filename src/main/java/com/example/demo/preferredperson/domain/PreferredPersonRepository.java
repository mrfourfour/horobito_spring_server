package com.example.demo.preferredperson.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PreferredPersonRepository extends JpaRepository<PreferredPerson, Long> {

    PreferredPerson findByDocumentIdAndPreferredPersonId(Long documentId, Long PreferredPersonId);
}
