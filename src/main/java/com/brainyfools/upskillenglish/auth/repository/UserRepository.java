package com.brainyfools.upskillenglish.auth.repository;

import com.brainyfools.upskillenglish.auth.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>  {
    User findByUsername(String username);
}