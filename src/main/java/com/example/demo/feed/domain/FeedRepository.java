package com.example.demo.feed.domain;



import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;



@Repository
public interface FeedRepository extends JpaRepository<Feed, Long> {


    Feed findFeedByIdAndDeleted(Long id, Boolean deleted);

    List<Feed> findFeedsByWriter_Id(WriterId id);

    Page<Feed> findAllByWriter(Writer writer);




}

