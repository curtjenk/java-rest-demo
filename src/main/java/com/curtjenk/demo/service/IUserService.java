package com.curtjenk.demo.service;

import java.util.List;

import com.curtjenk.demo.dto.UserDto;

public interface IUserService {
    List<UserDto> getAllUsers();

    UserDto signup(UserDto userDto);

    UserDto findUserByEmail(String email);

    UserDto findUserById(Long id);

    UserDto updateProfile(UserDto userDto);

    UserDto changePassword(UserDto userDto, String newPassword);
}