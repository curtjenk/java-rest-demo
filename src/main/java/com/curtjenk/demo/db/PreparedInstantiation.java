package com.curtjenk.demo.db;

import java.sql.PreparedStatement;

/**
 * This Interface is used in the {@link DbColumn#psInstantiation()} the apply
 * method will set the value in the prepared statement at a given point
 * 
 */
public interface PreparedInstantiation {

	void apply(PreparedStatement ps, int place, Object currentValue) throws Exception;
}
