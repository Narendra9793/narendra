package com.assignment.narendra.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.assignment.narendra.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

    public User findByEmail(String email);

    public User findByUserName(String userName);

    public User findByUserId(Long userId);
    
}
