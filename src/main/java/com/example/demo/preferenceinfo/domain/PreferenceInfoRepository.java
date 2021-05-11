package com.example.demo.preferenceinfo.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PreferenceInfoRepository extends JpaRepository<PreferenceInfo, Long> {

    PreferenceInfo findByDocumentIdAndPreferredPersonIdAndLocation(Long documentId, Long PreferredPersonId, PreferenceLocation location);
}
