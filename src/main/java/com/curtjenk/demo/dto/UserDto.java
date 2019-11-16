package com.curtjenk.demo.dto;

import java.util.List;

import com.curtjenk.demo.model.UserOrganizationRoleModel;
import com.curtjenk.demo.model.UserTeamRoleModel;
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
@Accessors(chain = true)
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
    private List<UserOrganizationRoleModel> organizationRoles;
    private List<UserTeamRoleModel> teamRoles;

    public void setUsername(String username) {
        this.name = username;
    }
    public String getUsername() {
        return this.name;
    }
}