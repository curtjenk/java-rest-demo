package com.curtjenk.demo.model;

import java.time.LocalDateTime;

import com.curtjenk.demo.db.BaseModel;
import com.curtjenk.demo.db.DbTable;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@DbTable(name = "organizations")
public class OrganizationModel extends BaseModel<OrganizationModel> {
   
    private Long id; // Sequence
    private Long parentId;
    private String name;
    private String address1;
    private String address2;
    private String city;
    private String state;
    private String zipCode;
    private String phone;
    private Long updatedUserId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public OrganizationModel() {
        super(OrganizationModel.class);
    }
    // @SneakyThrows
    // public OrganizationModel(ResultSet rs) {
    //     this.id = rs.getLong("id");
    //     this.parentId = rs.getLong("parent_id");
    //     this.name = rs.getString("name");
    //     this.address1 = rs.getString("address1");
    //     this.address2 = rs.getString("address2");
    //     this.city = rs.getString("city");
    //     this.state = rs.getString("state");
    //     this.zipCode = rs.getString("zipcode");
    //     this.phone = rs.getString("phone");
    //     this.updatedUserId = rs.getLong("updated_user_id");
    //     this.createdAt = rs.getTimestamp("created_at").toLocalDateTime();
    //     this.updatedAt = rs.getTimestamp("updated_at").toLocalDateTime();

    // }

    // // Sequence should match the insert statement
    // @SneakyThrows
    // @Override
    // public void bindPreparedStatement(PreparedStatement ps, boolean isBatch) {
    //     ps.setLong(1, this.parentId);
    //     ps.setString(2, this.name);
    //     ps.setString(3, this.address1);
    //     ps.setString(4, this.address2);
    //     ps.setString(5, this.city);
    //     ps.setString(6, this.state);
    //     ps.setString(7, this.zipCode);
    //     ps.setString(8, this.phone);
    //     ps.setTimestamp(9, Timestamp.valueOf(LocalDateTime.now()));
    // }

    // private static final String insert = "INSERT INTO organizations " 
    //         + " (parent_id, name, address1, address2, city, state, zipcode, phone, created_at)" 
    //         + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

    // private static final String update = "INSERT INTO organizations "
    //         + " (parent_id, name, address1, address2, city, state, zipcode, phone, updated_at)"
    //         + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

    // @Override
    // public String getUpsertSql(String var1) {
    //     return insert;
    // }
}