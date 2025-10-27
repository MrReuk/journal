package com.mpt.journal.service;

import com.mpt.journal.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        UserModel userModel = userService.findByLogin(login)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + login));

        String role;
        if (userModel.getRole() != null && userModel.getRole().getName() != null) {
            if (userModel.getRole().getName().equals("Администратор")) {
                role = "ADMIN";
            } else {
                role = "USER";
            }
        } else {
            role = "USER";
        }

        List<GrantedAuthority> authorities = Collections.singletonList(
                new SimpleGrantedAuthority(role)
        );

        return new User(
                userModel.getLogin(),
                userModel.getPassword(),
                true, true, true, true,
                authorities
        );
    }
}