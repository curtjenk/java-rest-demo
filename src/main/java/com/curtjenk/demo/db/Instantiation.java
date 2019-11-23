package com.curtjenk.demo.db;

import java.sql.PreparedStatement;

/**
 * This Interface is used in the {@link Column#instantiation()} the apply
 * method will set the value in the prepared statement at a given point
 * 
 */
public interface Instantiation {

	void apply(PreparedStatement ps, int place);
}
