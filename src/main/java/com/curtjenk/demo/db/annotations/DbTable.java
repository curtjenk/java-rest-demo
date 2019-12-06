package com.curtjenk.demo.db.annotations;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * 
 */
@Retention(RUNTIME)
@Target(ElementType.TYPE)
public @interface DbTable {

	/**
	 * This represents the Table in the database schema.
	 */
	String name();
}
