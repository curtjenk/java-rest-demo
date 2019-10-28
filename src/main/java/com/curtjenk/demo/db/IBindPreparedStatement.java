package com.curtjenk.demo.db;

import java.sql.PreparedStatement;

public interface IBindPreparedStatement {
    void bindPreparedStatement(PreparedStatement var1, boolean var2);

    String getUpsertSql(String var1);
}
