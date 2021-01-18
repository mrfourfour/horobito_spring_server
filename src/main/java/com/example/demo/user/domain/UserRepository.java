package com.example.demo.user.domain;


import lombok.Getter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUserIdAndPassword(String userId, String password);

    Optional<User> findByUserId(String userId);

    User findByIdAndIsExist(Long id, boolean isDelete);

}
