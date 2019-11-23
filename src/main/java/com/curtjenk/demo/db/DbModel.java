package com.curtjenk.demo.db;

import java.sql.PreparedStatement;

public abstract class DbModel {	 
    abstract public void bindPreparedStatement(PreparedStatement ps);
    abstract public String getUpsertSql(String partitionId, String schemaName);
    abstract public void setFileId(String fileId);
}
