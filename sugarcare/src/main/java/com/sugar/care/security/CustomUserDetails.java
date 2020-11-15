package com.sugar.care.security;

import com.sugar.care.entities.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class CustomUserDetails implements UserDetails {

    private String phoneNumber;
    private String password;


    public CustomUserDetails(){

    }

    public CustomUserDetails(User user){
        this.phoneNumber = user.getPhoneNumber();
        this.password = user.getPassword();
    }
    //hardcoded
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> set = new HashSet<>();
        return set;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return phoneNumber;
    }
    //hardcoded for now
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    //hardcoded for now
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    //hardcoded
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    //hardcoded
    @Override
    public boolean isEnabled() {
        return true;
    }
}
