package com.curtjenk.demo.controller;

import com.curtjenk.demo.dto.UserDto;
import com.curtjenk.demo.service.UserService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private static Logger logger = LoggerFactory.getLogger(UserController.class);

    UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public UserDto index(@PathVariable Long id) {
        logger.info("User id is {}", id);
        return userService.findUserById(id);
    }

}