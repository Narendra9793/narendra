package com.assignment.narendra.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.assignment.narendra.dto.CredentialDTO;
import com.assignment.narendra.model.User;
import com.assignment.narendra.repository.UserRepository;


@Service
public class UserService {
  
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;


    public String addUser(CredentialDTO cred) {
        User user= new User();

        user.setName(cred.getName());
        user.setEmail(cred.getEmail());
        user.setPassword(passwordEncoder.encode(cred.getPassword()));
        user.setUserName(cred.getUserName());

        this.userRepository.save(user);
        return user.getName() ;

    }

}


