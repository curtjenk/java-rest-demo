package com.curtjenk.demo.db;

import java.sql.PreparedStatement;
import java.time.LocalDateTime;
import java.util.List;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

@Data
public abstract class BaseModel<T extends BaseModel<T>> implements IBindPreparedStatement {

    @Getter(value = AccessLevel.NONE)
    @Setter(value = AccessLevel.NONE)
    protected final T self;

    @Getter(value = AccessLevel.NONE)
    @Setter(value = AccessLevel.NONE)
    protected List<Holder> holders;

    @Getter(value = AccessLevel.NONE)
    @Setter(value = AccessLevel.NONE)
    protected String tableName;
    
    @Getter(value = AccessLevel.NONE)
    @Setter(value = AccessLevel.NONE)
    protected String upsertSQL;

    @DbColumn(name = "updated_user_id")
    private Long updatedUserId;

    @DbColumn(name = "created_at", bindType = BindTypeTimeStamp.class)
    private LocalDateTime createdAt;

    @DbColumn(name = "updated_at", bindType = BindTypeTimeStamp.class)
    private LocalDateTime updatedAt;

    protected BaseModel(final Class<T> selfClass) {
        this.self = selfClass.cast(this);
        this.init();
    }

    public void init() {
        try {
            tableName = SqlHelper.getTableName(this.getClass());
            holders = SqlHelper.generateHolders(this.getClass());
            upsertSQL = SqlHelper.generateInserts(tableName, holders);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @SneakyThrows
    @Override
    public void bindPreparedStatement(PreparedStatement ps) {
        this.bindPreparedStatement(ps, true);
    }
    @Override
    public void bindPreparedStatement(PreparedStatement ps, boolean isBatch) {
        try {
            SqlHelper.generatePreparedStatement(holders, ps, self);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getUpsertSQL() {
        return String.format(upsertSQL, tableName);
    }

    @Override
    public String getUpsertSQL(String schema) {
        if (schema.trim().isEmpty())
            return this.getUpsertSQL();

        return String.format(upsertSQL, schema + "." + tableName);
    }

}
