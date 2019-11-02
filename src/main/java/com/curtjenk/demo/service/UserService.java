package com.curtjenk.demo.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.curtjenk.demo.dto.UserDto;
import com.curtjenk.demo.dto.UserMapper;
import com.curtjenk.demo.model.UserModel;
import com.curtjenk.demo.repository.UserRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
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
        Optional<UserModel> user = userRepository.findUserByEmail(email);
        return user.map( v -> UserMapper.toUserDto(v)).orElse(null);
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