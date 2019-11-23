package com.curtjenk.demo.db;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

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
public @interface DbColumn {

	/**
	 * This represents the column in the database schema.
	 * 
	 * 
	 */
	String value();

	/**
	 * This represents if this column should be included in the on conflict clause
	 * when generating the upsert statement
	 */
	boolean isOnconflict() default false;

	/**
	 * This is for when you need custom instantiation during the @see
	 * com.curtjenk.Model#bindPreparedStatement(PreparedStatement)
	 * is called. Using an implementation of this class you can generate your own
	 * value for this column instead of what the existing value of the field.
	 * 
	 */
	Class<? extends PreparedInstantiation> psInstantiation() default PreparedInstantiation.class;

	/**
	 * This is for when you need custom instantiation during the ResultSet
	 * constructor.
	 */
	Class<? extends ResultSetInstantiation> rsInstantiation() default ResultSetInstantiation.class;
}
