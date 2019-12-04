package com.curtjenk.demo.security;

import java.util.ArrayList;
import java.util.Optional;

import com.curtjenk.demo.dto.UserDto;
import com.curtjenk.demo.model.UserModel;
import com.curtjenk.demo.repository.UserRepository;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class JwtUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;


    public JwtUserDetailsService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        Optional<UserModel> user = userRepository.findUserByEmail(username);
        return user.map(u -> {
                    return new User(u.getEmail(), u.getPassword(), new ArrayList<>());
                }).orElseThrow( () -> new UsernameNotFoundException("not found"));
    }

    public UserDto save(final UserDto user) {
        final UserDto newUser = new UserDto();
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(newUser);
    }
}