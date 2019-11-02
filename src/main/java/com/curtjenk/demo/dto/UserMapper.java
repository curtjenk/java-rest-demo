package com.curtjenk.demo.dto;

import com.curtjenk.demo.model.UserModel;

public class UserMapper {

    public static UserDto toUserDto(UserModel user) {
        return new UserDto()
                .setId(user.getId())
                .setName(user.getName())
                .setNickName(user.getNickName())
                .setEmail(user.getEmail())
                .setPassword(user.getPassword());
    }

}