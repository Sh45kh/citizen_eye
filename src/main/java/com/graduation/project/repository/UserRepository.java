package com.graduation.project.repository;

import com.graduation.project.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Spring Boot is so smart, just by naming this method "findByEmail",
    // it will automatically write the PostgreSQL query to search for a user's email!
    Optional<User> findByEmail(String email);
}