package com.curtjenk.demo.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.curtjenk.demo.db.IBindPreparedStatement;

import lombok.Data;
import lombok.SneakyThrows;

@Data
public class UserTeamRoleModel implements IBindPreparedStatement {

    private Long teamId; // Sequence
    private String teamName;
    private Long userId;
    private Long roleId;
    private String roleName;

    @SneakyThrows
    public UserTeamRoleModel(ResultSet rs) {
        this.teamId = rs.getLong("team_id");
        this.teamName = rs.getString("team_name");
        this.userId = rs.getLong("user_id");
        this.roleId = rs.getLong("role_id");
        this.roleName = rs.getString("role_name");
    }

    // Sequence should match the insert statement
    @SneakyThrows
    @Override
    public void bindPreparedStatement(PreparedStatement ps, boolean isBatch) {
        ps.setLong(1, this.teamId);
        ps.setLong(2, this.userId);
        ps.setLong(3, this.roleId);
    }
    
    @Override
    public void bindPreparedStatement(PreparedStatement preparedStatement) {
    }

    private static final String insert = "INSERT INTO team_user " + " (team_id, user_id, role_id)" + " VALUES (?, ?, ?)";

    @Override
    public String getUpsertSQL() {
        return insert;
    }

    @Override
    public String getUpsertSQL(String schemaName) {
        return insert;
    }

   
}