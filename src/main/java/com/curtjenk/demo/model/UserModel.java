package com.curtjenk.demo.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.curtjenk.demo.db.IBindPreparedStatement;

import lombok.Data;
import lombok.SneakyThrows;

@Data
public class UserModel implements IBindPreparedStatement {

    private Long id; // Sequence
    private String name;
    private String nickName;
    private String email;
    private String password;

    @SneakyThrows
    public UserModel(ResultSet rs) {
        this.id = rs.getLong("id");
        this.name = rs.getString("name");
        this.nickName = rs.getString("nickname");
        this.email = rs.getString("email");
        this.password = rs.getString("password");
    }

    // Sequence should match the insert statement
    @SneakyThrows
    @Override
    public void bindPreparedStatement(PreparedStatement ps) {
        this.bindPreparedStatement(ps, true);
    }

    @Override
    public void bindPreparedStatement(PreparedStatement ps, boolean isBatch) {
        try {
            ps.setString(1, this.name);
            ps.setString(2, this.nickName);
            ps.setString(3, this.email);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static final String insert = "INSERT INTO users " + " (name, nickname, email)" + " VALUES (?, ?, ?)";

    @Override
    public String getUpsertSQL() {
        return insert;
    }

    @Override
    public String getUpsertSQL(String schemaName) {
        return insert;
    }
   

}