package com.curtjenk.demo.security;

import java.util.ArrayList;

import com.curtjenk.demo.dto.UserDto;
import com.curtjenk.demo.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder bcryptEncoder;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDto user = userService.findUserByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("not found");
        }
        return new User(user.getEmail(), user.getPassword(), new ArrayList<>());
    }

    public UserDto save(UserDto user) {
        UserDto newUser = new UserDto();
        newUser.setUsername(user.getUsername());
        newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
        return newUser;
        //TODO
        // return userService.save(newUser);
    }
}