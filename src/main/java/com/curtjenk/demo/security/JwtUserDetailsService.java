package com.curtjenk.demo.security;

import java.util.ArrayList;
import java.util.Optional;

import com.curtjenk.demo.model.UserModel;
import com.curtjenk.demo.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    // @Autowired
    // private PasswordEncoder bcryptEncoder;


    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        Optional<UserModel> user = userRepository.findUserByEmail(username);
        return user.map(u -> {
                    return new User(u.getEmail(), u.getPassword(), new ArrayList<>());
                }).orElseThrow( () -> new UsernameNotFoundException("not found"));
    }

    // public UserDto save(final UserDto user) {
    //     final UserDto newUser = new UserDto();
    //     // newUser.setName(user.getUsername());
    //     newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
    //     // return userService.save(newUser);
    // }
}