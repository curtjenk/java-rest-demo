package com.curtjenk.demo.db;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TimeStampInst implements PreparedInstantiation {
	
	// TODO not using this class

	@Override
	public void apply(PreparedStatement ps, int place, Object currentValue) {
		try {
			ps.setTimestamp(place, new java.sql.Timestamp(System.currentTimeMillis()));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
