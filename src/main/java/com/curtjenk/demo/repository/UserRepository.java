package com.curtjenk.demo.repository;

import java.util.List;

import com.curtjenk.demo.db.PostgresUtil;
import com.curtjenk.demo.model.User;

public class UserRepository {

    // private static final String insert = "INSERT INTO users " + " (name,
    // nickname, email)" + " VALUES (?, ?, ?)";
    private PostgresUtil postgresUtil;

    public UserRepository(PostgresUtil postgresUtil) {
        this.postgresUtil = postgresUtil;
    }

    public List<User> findAll() {
        return postgresUtil.select("SELECT * from users", User.class);
    }
}