package com.curtjenk.demo.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.curtjenk.demo.dto.UserDto;
import com.curtjenk.demo.service.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    private static Logger logger = LoggerFactory.getLogger(HelloController.class);

    private UserService userService;

    public HelloController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/")
    public List<UserDto> index() {

        return userService.getAllUsers();

    }

    private String show(String... x) {
        String line = Stream.of(x).collect(Collectors.joining(" "));
        System.out.println(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + " " + line);
        return line;
    }

}