package com.curtjenk.demo.db.util;

import java.sql.PreparedStatement;

public interface IBindPreparedStatement {
    void bindPreparedStatement(PreparedStatement preparedStatement);
    void bindPreparedStatement(PreparedStatement preparedStatement, boolean isBatch);
    String getUpsertSQL();
    String getUpsertSQL(String schemaName);
}
