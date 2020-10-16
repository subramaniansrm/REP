package com.srm.repg.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.srm.repg.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}