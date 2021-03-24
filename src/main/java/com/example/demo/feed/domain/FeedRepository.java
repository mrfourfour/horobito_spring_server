package com.example.demo.feed.domain;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.Optional;



@Repository
public interface FeedRepository extends JpaRepository<Feed, Long> {


    Feed findFeedByIdAndDeleted(Long id, Boolean deleted);





}

