package com.sugar.care.security;

import com.sugar.care.entities.User;
import com.sugar.care.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String phoneNumber) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepo.findByPhoneNumber(phoneNumber);
        if(userOptional.isEmpty()){
            throw new UsernameNotFoundException("Not found the user with phone Number: " + phoneNumber);
        }
        return new CustomUserDetails(userOptional.get());
    }
}
