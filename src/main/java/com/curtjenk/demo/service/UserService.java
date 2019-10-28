package com.curtjenk.demo.service;

import java.util.List;
import java.util.stream.Collectors;

import com.curtjenk.demo.dto.UserDto;
import com.curtjenk.demo.dto.UserMapper;
import com.curtjenk.demo.repository.UserRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserService implements IUserService {
    private static Logger logger = LoggerFactory.getLogger(UserService.class);

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDto signup(UserDto userDto) {

        return new UserDto();
    }

    @Override
    public UserDto findUserByEmail(String email) {
        return new UserDto();
    }

    @Override
    public UserDto updateProfile(UserDto userDto) {
        return new UserDto();
    }

    @Override
    public UserDto changePassword(UserDto userDto, String newPassword) {
        return new UserDto();
    }

    @Override
    public List<UserDto> getAllUsers() {
        logger.debug("getAllUsers()");
        return userRepository.findAll().stream().map(UserMapper::toUserDto).collect(Collectors.toList());
    }
}