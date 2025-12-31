package com.srihari.expensetracker.expensetracker.repository;

import com.srihari.expensetracker.model.User;
import com.srihari.expensetracker.expensetracker.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<com.srihari.expensetracker.expensetracker.model.Userr, Long> {

    Optional<User> findByEmail(String email);
}
