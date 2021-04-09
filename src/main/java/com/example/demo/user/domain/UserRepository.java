package com.example.demo.user.domain;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {


    User findUserById(Long id);


    User findByUserBasicInfo_Username(Username username);
}
