package com.curtjenk.demo.dto;

import com.curtjenk.demo.model.User;

public class UserMapper {

    public static UserDto toUserDto(User user) {
        return new UserDto().setId(user.getId()).setName(user.getName()).setNickName(user.getNickName())
                .setEmail(user.getEmail());

    }

}