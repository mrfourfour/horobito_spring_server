package com.example.demo.feed.domain;


import com.example.demo.user.domain.User;
import com.example.demo.user.domain.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Set;


@Repository
public interface FeedRepository extends JpaRepository<Feed, Long> {


    Feed findFeedByIdAndIsDeleted(Long id, boolean isDeleted);




}

