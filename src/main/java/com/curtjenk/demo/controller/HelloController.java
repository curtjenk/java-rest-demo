package com.curtjenk.demo.controller;

import java.util.List;

import com.curtjenk.demo.dto.UserDto;
import com.curtjenk.demo.model.OrganizationModel;
import com.curtjenk.demo.service.UserService;

// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    // private static Logger logger =
    // LoggerFactory.getLogger(HelloController.class);

    private UserService userService;

    public HelloController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/")
    public List<UserDto> index() {
        OrganizationModel org = new OrganizationModel();
        org.getAddress1();
        
        return userService.getAllUsers();

    }

}