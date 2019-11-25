package com.curtjenk.demo.db;

import java.sql.PreparedStatement;

/**
 * This Interface is used in the {@link DbColumn#bindType()} the apply
 * method will set the value in the prepared statement at a given point
 * 
 */
public interface ColBindType {

	void apply(PreparedStatement ps, int place, Object currentValue) throws Exception;
}
