package com.curtjenk.demo.dto;

import java.sql.ResultSet;

import lombok.Data;
import lombok.SneakyThrows;

@Data
public class UserTeamRoleDto {

    private Long teamId; // Sequence
    private String teamName;
    private Long userId;
    private Long roleId;
    private String roleName;

    @SneakyThrows
    public UserTeamRoleDto(ResultSet rs) {
        this.teamId = rs.getLong("team_id");
        this.teamName = rs.getString("team_name");
        this.userId = rs.getLong("user_id");
        this.roleId = rs.getLong("role_id");
        this.roleName = rs.getString("role_name");
    }

}