package com.assignment.narendra.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.assignment.narendra.repository.UserRepository;
import com.assignment.narendra.model.User;



public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user= (User)userRepository.findByEmail(email);
        if(user == null){
            throw new UsernameNotFoundException("Invalid username or password");
        }
        CustomUserDetails customUserDetails=new CustomUserDetails((com.assignment.narendra.model.User) user);
        return  customUserDetails;
    }
    
}
