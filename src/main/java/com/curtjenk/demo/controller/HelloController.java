package com.curtjenk.demo.controller;

import java.util.List;

import com.curtjenk.demo.db.PostgresUtil;
import com.curtjenk.demo.model.OrganizationModel;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api")
public class HelloController {

    private PostgresUtil postgresUtil;

    public HelloController(PostgresUtil postgresUtil) {
        this.postgresUtil = postgresUtil;
    }

    @GetMapping()
    public List<OrganizationModel> index() {
        OrganizationModel org = new OrganizationModel();
        org.setAddress1("123 Mocking Bird Ln 2");
        org.setName("Demo Rest Api 3");
        org.setParentId(36L);
    
        log.debug(org.getUpsertSQL());
        postgresUtil.upsert(org);

        List<OrganizationModel> orgs = postgresUtil.select("Select * from organizations", OrganizationModel.class);

        return orgs;

    }

}