package com.curtjenk.demo.db.annotations;

import java.sql.PreparedStatement;
import java.sql.SQLException;
/**
 * Set a Timestamp bind variable value to current date time
 */
public class BindTypeTimeStamp implements ColBindType {

	@Override
	public void apply(PreparedStatement ps, int place, Object currentValue) {
		try {
			ps.setTimestamp(place, new java.sql.Timestamp(System.currentTimeMillis()));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
