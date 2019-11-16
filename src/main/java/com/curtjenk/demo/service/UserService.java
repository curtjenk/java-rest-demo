package com.curtjenk.demo.service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import com.curtjenk.demo.dto.UserDto;
import com.curtjenk.demo.dto.UserMapper;
import com.curtjenk.demo.model.UserModel;
import com.curtjenk.demo.model.UserOrganizationRoleModel;
import com.curtjenk.demo.model.UserTeamRoleModel;
import com.curtjenk.demo.repository.UserRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {
    private static Logger logger = LoggerFactory.getLogger(UserService.class);

    private UserRepository userRepository;

    private final ExecutorService executorService = Executors.newFixedThreadPool(4);

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDto signup(UserDto userDto) {

        return new UserDto();
    }

    public UserDto getUserProfile(String email) {
        UserDto userDto = null;
        try {
            CompletableFuture<Optional<UserModel>> f1 = CompletableFuture
                    .supplyAsync(() -> userRepository.findUserByEmail(email), executorService);
            CompletableFuture<List<UserOrganizationRoleModel>> f2 = CompletableFuture
                    .supplyAsync(() -> userRepository.findOrganizationRoles(email), executorService);
            CompletableFuture<List<UserTeamRoleModel>> f3 = CompletableFuture
                    .supplyAsync(() -> userRepository.findTeamRoles(email), executorService);

            Optional<UserModel> user = f1.get();
            List<UserOrganizationRoleModel> organizationRoles = f2.get();
            List<UserTeamRoleModel> teamRoles = f3.get();

            userDto = UserMapper.toUserDto(user.get());
            userDto.setOrganizationRoles(organizationRoles);
            userDto.setTeamRoles(teamRoles);
        } catch (ExecutionException ee) {
            logger.error("msg", ee);
        } catch (InterruptedException ie) {
            logger.error("msg", ie);
        }

        return userDto;
    }
    
    @Override
    public UserDto findUserByEmail(String email) {
        Optional<UserModel> user = userRepository.findUserByEmail(email);
        logger.info(user.toString());
        return user.map(v -> UserMapper.toUserDto(v)).orElse(null);

        // UserDto userDto = null;
        // try {
        //     CompletableFuture<Optional<UserModel>> f1 = CompletableFuture
        //             .supplyAsync(() -> userRepository.findUserByEmail(email), executorService);
        //     CompletableFuture<List<UserOrganizationRoleModel>> f2 = CompletableFuture
        //             .supplyAsync(() -> userRepository.findOrganizationRoles(email), executorService);
        //     CompletableFuture<List<UserTeamRoleModel>> f3 = CompletableFuture
        //             .supplyAsync(() -> userRepository.findTeamRoles(email), executorService);
            
        //     Optional<UserModel> user = f1.get();
        //     List<UserOrganizationRoleModel> organizationRoles = f2.get();
        //     List<UserTeamRoleModel> teamRoles = f3.get();

        //     userDto = UserMapper.toUserDto(user.get());
        //     userDto.setOrganizationRoles(organizationRoles);
        //     userDto.setTeamRoles(teamRoles);
        //     logger.info(userDto.toString());
        // } catch (ExecutionException ee) {
        //     logger.error("msg", ee);
        // } catch (InterruptedException ie) {
        //     logger.error("msg", ie);
        // }
        // return userDto;
    }

    @Override
    public UserDto updateProfile(UserDto userDto) {
        return new UserDto();
    }

    @Override
    public UserDto changePassword(UserDto userDto, String newPassword) {
        return new UserDto();
    }

    @Override
    public List<UserDto> getAllUsers() {
        logger.debug("getAllUsers()");
        return userRepository.findAll().stream().map(UserMapper::toUserDto).collect(Collectors.toList());
    }
}