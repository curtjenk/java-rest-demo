package com.curtjenk.demo.dto.mappers;

import com.curtjenk.demo.db.model.UserModel;
import com.curtjenk.demo.dto.UserDto;

public class UserMapper {

    public static UserDto toUserDto(UserModel user) {
        return new UserDto()
                .id(user.getId())
                .name(user.getName())
                .nickName(user.getNickName())
                .email(user.getEmail())
                .password(user.getPassword());
    }

}