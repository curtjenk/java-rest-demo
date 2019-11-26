package com.curtjenk.demo.model;

import java.sql.ResultSet;

import com.curtjenk.demo.db.BaseModel;
import com.curtjenk.demo.db.DbColumn;
import com.curtjenk.demo.db.DbTable;
import com.curtjenk.demo.db.SqlHelper;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.SneakyThrows;

@Data
@EqualsAndHashCode(callSuper = true)
@DbTable(name = "organizations")
public class OrganizationModel extends BaseModel<OrganizationModel> {
   
    private Long id; // Sequence
    
    @DbColumn(name = "parent_id")
    private Long parentId;

    @DbColumn(name = "name", isOnconflict = true)
    private String name;

    @DbColumn(name = "address1")
    private String address1;

    @DbColumn(name = "address2")
    private String address2;

    @DbColumn(name = "city")
    private String city;

    @DbColumn(name = "state")
    private String state;

    @DbColumn(name = "zipcode")
    private String zipCode;

    @DbColumn(name = "phone")
    private String phone;

    public OrganizationModel() {
        super();
    }

    public OrganizationModel(ResultSet rs) {
        super(rs);
    }
}