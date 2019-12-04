package com.curtjenk.demo.repository;

import java.util.List;
import java.util.Optional;

import com.curtjenk.demo.db.PostgresUtil;
import com.curtjenk.demo.dto.UserDto;
import com.curtjenk.demo.model.UserModel;
import com.curtjenk.demo.model.UserOrganizationRoleModel;
import com.curtjenk.demo.model.UserTeamRoleModel;

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

    public Optional<UserModel> findUserById(Long id) {
        List<UserModel> users = postgresUtil.select("select * from users where id = ?", UserModel.class, id);
        if (users.size() == 1) {
            return Optional.ofNullable(users.get(0));
        } else {
            return Optional.empty();
        }

    }

    public Optional<UserModel> findUserByEmail(String email) {
        List<UserModel> users = postgresUtil.select("select * from users where email = ?", UserModel.class, email);
        if (users.size() == 1) {
            return Optional.ofNullable(users.get(0));
        } else {
            return Optional.empty();
        }

    }

    public List<UserOrganizationRoleModel> findOrganizationRoles(String email) {
        final String sql = "SELECT ou.*, o.name as organization_name, r.name as role_name "
                + "   FROM users                                                 "
                + "   JOIN organization_user ou on ou.user_id = users.id        "
                + "   JOIN roles r on r.id = ou.role_id " 
                + "   JOIN organizations o on o.id = ou.organization_id "
                + "  WHERE users.email = ? ";
        return postgresUtil.select(sql, UserOrganizationRoleModel.class, email);
    }
    

    public List<UserOrganizationRoleModel> findOrganizationRoles(Long userId) {
        final String sql = "SELECT ou.*, o.name as organization_name, r.name as role_name "
                + "   FROM users "
                + "   JOIN organization_user ou on ou.user_id = users.id        "
                + "   JOIN roles r on r.id = ou.role_id        "
                + "   JOIN organizations o on o.id = ou.organization_id "
                + "  WHERE users.id = ?                                          ";
        return postgresUtil.select(sql, UserOrganizationRoleModel.class, userId);
    }
    
    public List<UserTeamRoleModel> findTeamRoles(Long userId) {
        final String sql = "SELECT team_user.*, teams.name as team_name, roles.name as role_name "
                + "   FROM users "
                + "   JOIN team_user on team_user.user_id = users.id "
                + "   JOIN roles on roles.id = team_user.role_id " 
                + "   JOIN teams on teams.id = team_user.team_id "
                + "  WHERE users.id = ?                                          ";
        return postgresUtil.select(sql, UserTeamRoleModel.class, userId);
    }

    public List<UserTeamRoleModel> findTeamRoles(String email) {
         final String sql = "SELECT tu.*, t.name as team_name, r.name as role_name "
                + "   FROM users                                                 "
                + "   JOIN team_user tu on tu.user_id = users.id        "
                + "   JOIN roles r on r.id = tu.role_id " 
                + "   JOIN teams t on t.id = tu.team_id "
                + "  WHERE users.email = ?   ";
        return postgresUtil.select(sql, UserTeamRoleModel.class, email);
    }

    public UserDto save(UserDto newUser) {
        return newUser;
    }
}