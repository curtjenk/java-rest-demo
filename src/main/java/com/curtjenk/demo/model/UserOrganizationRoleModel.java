package com.curtjenk.demo.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.curtjenk.demo.db.IBindPreparedStatement;

import lombok.Data;
import lombok.SneakyThrows;

@Data
public class UserOrganizationRoleModel implements IBindPreparedStatement {

    private Long orgId; // Sequence
    private String orgName;
    private Long userId;
    private Long roleId;
    private String roleName;

    @SneakyThrows
    public UserOrganizationRoleModel(ResultSet rs) {
        this.orgId = rs.getLong("organization_id");
        this.orgName = rs.getString("organization_name");
        this.userId = rs.getLong("user_id");
        this.roleId = rs.getLong("role_id");
        this.roleName = rs.getString("role_name");
    }

    // Sequence should match the insert statement
    @SneakyThrows
    @Override
    public void bindPreparedStatement(PreparedStatement ps, boolean isBatch) {
        ps.setLong(1, this.orgId);
        ps.setLong(2, this.userId);
        ps.setLong(3, this.roleId);
    }

    private static final String insert = "INSERT INTO organization_user " + " (organization_id, user_id, role_id)" + " VALUES (?, ?, ?)";

    @Override
    public String getUpsertSql(String var1) {
        return insert;
    }
}