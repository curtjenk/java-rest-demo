package com.curtjenk.demo.db;

import java.sql.PreparedStatement;

/**
 *  AuditModel is:
 *      - Schema aware
 *      - Partition aware
 *      - FileID can be bound later
 */
public abstract class DbModel {
	 
    abstract public void bindPreparedStatement(PreparedStatement ps);
    abstract public String getUpsertSql(String partitionId, String schemaName);
    abstract public void setFileId(String fileId);
}
