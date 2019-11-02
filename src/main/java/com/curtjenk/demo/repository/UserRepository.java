package com.curtjenk.demo.repository;

import java.util.List;
import java.util.Optional;

import com.curtjenk.demo.db.PostgresUtil;
import com.curtjenk.demo.model.UserModel;

import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {

    // private static final String insert = "INSERT INTO users " + " (name,
    // nickname, email)" + " VALUES (?, ?, ?)";
    private PostgresUtil postgresUtil;

    public UserRepository(PostgresUtil postgresUtil) {
        this.postgresUtil = postgresUtil;
    }

    public List<UserModel> findAll() {
        return postgresUtil.select("SELECT * from users", UserModel.class);
    }

    public Optional<UserModel> findUserByEmail(String email) {
        List<UserModel> users = postgresUtil.select("select * from users where email = ?", UserModel.class, email);
        if (users.size() == 1) {
            return Optional.ofNullable(users.get(0));
        } else {
            return Optional.empty();
        }

    }
}