package com.curtjenk.demo.db.annotations;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * This annotation is used with @see package com.curtjenk.demo.db.SqlHelper will
 * help generate the upsert statement, ResulSet constructor and handle the logic
 * for
 * 
 * @see package
 *      com.curtjenk.demo.db.Model#bindPreparedStatement(PreparedStatement)
 * 
 */
@Retention(RUNTIME)
@Target(FIELD)
@Inherited
public @interface DbColumn {

	/**
	 * This represents the column name in the database table.
	 * 
	 */
	String name();

	/**
	 * This represents if this column should be included in the on conflict clause
	 * when generating the upsert statement
	 */
	boolean isOnconflict() default false;

	boolean updateable() default true;

	/**
	 * This is for when you need custom processing to set bind variables
	 * Using an implementation of this class you can generate your own
	 * value for this column instead of what the existing value of the field.
	 * 
	 */
	Class<? extends ColBindType> bindType() default ColBindType.class;

	/**
	 * This is for when you need custom process for results.
	 * Requires a constructor with a Resultset parameter
	 */
	Class<? extends ColResultType> resultType() default ColResultType.class;
}
