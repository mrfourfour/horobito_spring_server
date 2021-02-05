package com.example.demo.login.service;


import com.example.demo.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Delegate;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;



@AllArgsConstructor
@Getter
public class MyUserDetails implements UserDetails {

    @Delegate
    private User user;
    private Collection<? extends GrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }



    @Override
    public String getUsername() {
        return user.getUserId();
    }



    @Override
    public boolean isAccountNonExpired() {
        return user.getIsEnable();
    }

    @Override
    public boolean isAccountNonLocked() {
        return user.getIsEnable();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return user.getIsEnable();
    }

    @Override
    public boolean isEnabled() {
        return user.getIsEnable();
    }
}