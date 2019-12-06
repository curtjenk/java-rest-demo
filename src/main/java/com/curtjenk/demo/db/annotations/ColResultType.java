package com.curtjenk.demo.db.annotations;

import java.sql.ResultSet;

public interface ColResultType {

	Object apply(ResultSet ps, String result) throws Exception;
}
