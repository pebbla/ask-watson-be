package com.apebble.askwatson.user;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserJpaRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserPhoneNum(String phoneNum);

    @Query(value = "select u from User u where :searchWord is null or (u.userNickname like %:searchWord% or u.userPhoneNum like %:searchWord% )")
    List<User> findUsersBySearchWord(String searchWord);
}
