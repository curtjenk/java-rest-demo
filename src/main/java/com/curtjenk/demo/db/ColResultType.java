package com.curtjenk.demo.db;

import java.sql.ResultSet;

public interface ColResultType {

	Object apply(ResultSet ps, String result) throws Exception;
}
