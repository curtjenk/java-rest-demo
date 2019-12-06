package com.curtjenk.demo.dto;

import java.util.List;

import com.curtjenk.demo.db.model.UserModel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(fluent = true)
@NoArgsConstructor
@ToString
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDto {

    private Long id; // Sequence
    private String name;
    private String nickName;
    private String email;
    @JsonIgnore
    @ToString.Exclude
    private String password;
    private List<UserOrganizationRoleDto> organizationRoles;
    private List<UserTeamRoleDto> teamRoles;

    public static UserDto map(UserModel user) {
        return new UserDto()
                .id(user.getId())
                .name(user.getName())
                .nickName(user.getNickName())
                .email(user.getEmail())
                .password(user.getPassword());
    }
}