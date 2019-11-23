package com.curtjenk.demo.db;

import java.sql.ResultSet;

public interface ResultSetInstantiation {
	Object apply(ResultSet rs, String result) throws Exception;
}
