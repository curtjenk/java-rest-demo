package com.curtjenk.demo.service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import com.curtjenk.demo.db.model.UserModel;
import com.curtjenk.demo.db.repository.UserRepository;
import com.curtjenk.demo.dto.UserDto;
import com.curtjenk.demo.dto.UserOrganizationRoleDto;
import com.curtjenk.demo.dto.UserTeamRoleDto;
import com.curtjenk.demo.dto.mappers.UserMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private static Logger logger = LoggerFactory.getLogger(UserService.class);

    private UserRepository userRepository;

    private final ExecutorService executorService = Executors.newFixedThreadPool(4);

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDto signup(UserDto userDto) {

        return new UserDto();
    }


    public UserDto getProfileById(Long id) {
        UserDto userDto = null;
        try {
            CompletableFuture<Optional<UserModel>> f1 = CompletableFuture
                    .supplyAsync(() -> userRepository.findUserById(id), executorService);
            CompletableFuture<List<UserOrganizationRoleDto>> f2 = CompletableFuture
                    .supplyAsync(() -> userRepository.findOrganizationRoles(id), executorService);
            CompletableFuture<List<UserTeamRoleDto>> f3 = CompletableFuture
                    .supplyAsync(() -> userRepository.findTeamRoles(id), executorService);

            Optional<UserModel> user = f1.get();
            List<UserOrganizationRoleDto> organizationRoles = f2.get();
            List<UserTeamRoleDto> teamRoles = f3.get();

            userDto = UserDto.map(user.get());
            // userDto = UserMapper.toUserDto(user.get());
            userDto.organizationRoles(organizationRoles);
            userDto.teamRoles(teamRoles);
            logger.info(userDto.toString());
        } catch (ExecutionException ee) {
            logger.error("msg", ee);
        } catch (InterruptedException ie) {
            logger.error("msg", ie);
        }

        return userDto;
    }
    
    public UserDto getUserByEmail(String email) {
        Optional<UserModel> user = userRepository.findUserByEmail(email);
        // logger.info(user.toString());
        return user.map(v -> UserMapper.toUserDto(v)).orElse(null);
    }

    public UserDto updateProfile(UserDto userDto) {
        return new UserDto();
    }

    public UserDto changePassword(UserDto userDto, String newPassword) {
        return new UserDto();
    }

    public List<UserDto> getAllUsers() {
        logger.debug("getAllUsers()");
        return userRepository.findAll().stream().map(UserMapper::toUserDto).collect(Collectors.toList());
    }
}