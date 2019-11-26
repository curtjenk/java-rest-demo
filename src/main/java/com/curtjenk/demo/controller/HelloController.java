package com.curtjenk.demo.controller;

import java.util.List;

import com.curtjenk.demo.db.PostgresUtil;
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
    private PostgresUtil postgresUtil;

    public HelloController(UserService userService, PostgresUtil postgresUtil) {
        this.userService = userService;
        this.postgresUtil = postgresUtil;
    }

    @RequestMapping("/")
    public List<OrganizationModel> index() {
        OrganizationModel org = new OrganizationModel();
        org.setAddress1("123 Mocking Bird Ln");
        org.setName("Demo Rest Api 3");
        org.setParentId(33L);
    
        System.out.println(org.getUpsertSQL());
        postgresUtil.upsert(org);

        List<OrganizationModel> orgs = postgresUtil.select("Select * from organizations", OrganizationModel.class);

        return orgs;

    }

}