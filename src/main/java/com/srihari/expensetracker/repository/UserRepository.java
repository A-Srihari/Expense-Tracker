package com.srihari.expensetracker.repository;

import com.srihari.expensetracker.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<com.srihari.expensetracker.model.User, Long> {

    Optional<User> findByEmail(String email);
}
