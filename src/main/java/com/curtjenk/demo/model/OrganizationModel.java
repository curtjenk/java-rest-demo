package com.curtjenk.demo.model;

import com.curtjenk.demo.db.BaseModel;
import com.curtjenk.demo.db.DbColumn;
import com.curtjenk.demo.db.DbTable;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@DbTable(name = "organizations")
public class OrganizationModel extends BaseModel<OrganizationModel> {
   
    private Long id; // Sequence
    @DbColumn(name = "parent_id")
    private Long parentId;
    @DbColumn(name = "name")
    private String name;
    @DbColumn(name = "address1")
    private String address1;
    private String address2;
    private String city;
    private String state;
    private String zipCode;
    private String phone;
    private Long updatedUserId;
    // private LocalDateTime createdAt;
    // private LocalDateTime updatedAt;

    public OrganizationModel() {
        super(OrganizationModel.class);
    }
   
}