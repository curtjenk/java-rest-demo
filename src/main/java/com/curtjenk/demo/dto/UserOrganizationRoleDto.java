package com.curtjenk.demo.dto;

import java.sql.ResultSet;

import lombok.Data;
import lombok.SneakyThrows;

@Data
public class UserOrganizationRoleDto {

    private Long orgId; // Sequence
    private String orgName;
    private Long userId;
    private Long roleId;
    private String roleName;

    @SneakyThrows
    public UserOrganizationRoleDto(ResultSet rs) {
        this.orgId = rs.getLong("organization_id");
        this.orgName = rs.getString("organization_name");
        this.userId = rs.getLong("user_id");
        this.roleId = rs.getLong("role_id");
        this.roleName = rs.getString("role_name");
    }

}