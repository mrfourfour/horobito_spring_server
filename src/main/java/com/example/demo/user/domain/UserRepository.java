package com.example.demo.user.domain;


import lombok.Getter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {


    User findUserById(Long id);

    Boolean existsByUserId(String userId);


    User findByUserId(String userId);

    Optional<User> findUserByUserId(String userId);


}
