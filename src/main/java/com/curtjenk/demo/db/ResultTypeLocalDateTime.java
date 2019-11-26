package com.curtjenk.demo.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class ResultTypeLocalDateTime implements ColResultType {

	@Override
	public LocalDateTime apply(ResultSet rs, String col) {
		try {
			Object rsValue = rs.getObject(col);
			if (rsValue != null) {
				return ((java.sql.Timestamp) rsValue).toLocalDateTime();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
		
	}

}
