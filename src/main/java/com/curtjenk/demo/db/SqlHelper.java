package com.curtjenk.demo.db;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This is class designed to read the annotation @see
 * com.curtjenk.demo.db.DbColumn if a class has any Fields annotated with it. It
 * will generate the upsert statement, ResulSet constructor and handle the logic
 * for
 * 
 * @see com.curtjenk.demo.db.DbModel#bindPreparedStatement(PreparedStatement)
 *
 */
public class SqlHelper {
	/**
	 * When generating the upsert statement it needs to have the exact same order as
	 * the preparedStatement. An placement order is arbitrarily assigned to each
	 * annotated with field @see com.curtjenk.demo.db.DbColumn
	 * 
	 * @param holders holds the references to the fields of the class being
	 *                processed
	 */
	public static void setPlace(List<Holder> holders) {
		int place = 0;
		for (Holder hold : holders) {
			hold.setPlace(place++);
		}
	}

	/**
	 * This is responsible for adding the values to the prepared statements. It
	 * should be called inside the
	 * 
	 * @see com.curtjenk.demo.db.DbModel.bindPreparedStatement(PreparedStatement)
	 * 
	 * @param holders holds the references to the fields of the class being
	 *                processed
	 * @param ps      is the prepared statement to prepared
	 * @param obj     is the Object itself which has the values in it that needs to
	 *                be put in the prepared statement.
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	public static void generatePreparedStatement(List<Holder> holders, PreparedStatement ps, Object obj)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		try {
			for (int i = 0; i < holders.size(); i++) {

				Holder ins = holders.get(i);

				int place = ins.getPlace() + 1;
				// if (ins.getColumn().psInstantiation() != PreparedInstantiation.class) {
				// 	Class<? extends PreparedInstantiation> clazz = ins.getColumn().psInstantiation();
				// 	PreparedInstantiation inst = clazz.newInstance();
				// 	inst.apply(ps, place,ins.getField().get(obj));
				// } else {
				// 	ps.setObject(place, ins.getField().get(obj));
				// }

				if (ins.getColumn().bindType() != ColBindType.class) {
					Class<? extends ColBindType> clazz = ins.getColumn().bindType();
					ColBindType inst = clazz.newInstance();
					inst.apply(ps, place, ins.getField().get(obj));
				} else {
					ps.setObject(place, ins.getField().get(obj));
				}

			}
			ps.addBatch();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * This will generate the upsert statement for the class
	 * 
	 * @param table   is the table that will be upserted into
	 * @param holders holds the references to the fields of the class being
	 *                processed
	 * @return
	 */
	public static String generateInserts(String table, List<Holder> holders) {
		if (holders.isEmpty()) {
			return "";
		}
		
		String str = "INSERT INTO %%s(%s) VALUES(%s) ";
		holders.sort((c1, c2) -> c1.getPlace() - c2.getPlace());
		String statement = "";
		String quesMarks = "";
		for (Holder ins : holders) {
			statement += ins.getColumn().name() + ",";
			quesMarks += "?,";
		}
		statement = trimTrailingComma(statement);
		quesMarks = trimTrailingComma(quesMarks);

		// if (statement.charAt(statement.length() - 1) == ',')
		// 	statement = statement.substring(0, statement.length() - 1);

		// if (quesMarks.charAt(quesMarks.length() - 1) == ',')
		// 	quesMarks = quesMarks.substring(0, quesMarks.length() - 1);

		return String.format(str, statement, quesMarks) + generateOnConflict(holders);
	}

	/**
	 * Generates the onConflict statement for the upsert statement.
	 * 
	 * @param holders holds the references to the fields of the class being
	 *                processed
	 * @return the conflict clause for the upsert statement. If there are no
	 *         onConflicts then it will return an empty string
	 */
	public static String generateOnConflict(List<Holder> holders) {
		String str = "  ON CONFLICT(%s) DO UPDATE SET  %s";
		String conflictColumns = "";
		String updateColumns = "";
		for (Holder ins : holders) {
			DbColumn column = ins.getColumn();
			if (column.isOnconflict())
				conflictColumns += column.name() + ",";
			else if (column.updateable()) {
				String name = column.name();
				updateColumns += name + " = excluded." + name + ",";
			}

		}
		if (conflictColumns.isEmpty())
			return "";
		
		conflictColumns = trimTrailingComma(conflictColumns);
		updateColumns = trimTrailingComma(updateColumns);

		// if (conflictColumns.length() > 0 && conflictColumns.charAt(conflictColumns.length() - 1) == ',')
		// 	conflictColumns = conflictColumns.substring(0, conflictColumns.length() - 1);
		// if (updateColumns.length() > 0 && updateColumns.charAt(updateColumns.length() - 1) == ',') {
		// 	updateColumns = updateColumns.substring(0, updateColumns.length() - 1);
		// }

		return String.format(str, conflictColumns, updateColumns);
	}

	/**
	 * This will inject into the new Object values from the ResultSet.
	 * 
	 * @param dbModel   is the model object that will have the ResultSet values put
	 *                  into.
	 * @param resultSet is the ResultSet with the values
	 * @param holders   holds the references to the fields of the class being
	 *                  processed
	 * @throws Exception
	 */
	public static void getValueFromResultSet(Object dbModel, ResultSet resultSet, List<Holder> holders)
			throws Exception {
		for (Holder hold : holders) {
			try {
				if (hold.getColumn().rsInstantiation() == ResultSetInstantiation.class) {
					Object value = hold.getRsMethod().invoke(resultSet, hold.getColumn().name());
					hold.getField().set(dbModel, value);
				} else {
					ResultSetInstantiation rsI = hold.getColumn().rsInstantiation().newInstance();
					Object value = rsI.apply(resultSet, hold.getColumn().name());
					hold.getField().set(dbModel, value);
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * This is responsible for finding all the fields of a class annotated with
	 * the @see com.cfa.taxhub.audit.data.model.sql.TaxColumn
	 * 
	 * @param <T>
	 * 
	 * @param clazz
	 * @return
	 * @throws ClassNotFoundException
	 */
	public static <T> List<Holder> generateHolders(Class<T> clazz) throws ClassNotFoundException {

		List<Holder> holderList = new ArrayList<Holder>();
		for (Field field : clazz.getDeclaredFields()) {
			if (field.isAnnotationPresent(DbColumn.class)) {
				DbColumn column = field.getAnnotation(DbColumn.class);
				field.setAccessible(true);
				holderList.add(new Holder(field, column));
			}
		}

		final Class<? super T> superClass = clazz.getSuperclass();
		if (superClass != null) {
			for (Field field : superClass.getDeclaredFields()) {
				if (field.isAnnotationPresent(DbColumn.class)) {
					DbColumn column = field.getAnnotation(DbColumn.class);
					field.setAccessible(true);
					holderList.add(new Holder(field, column));
				}
			}
		}

		generateRS(holderList);
		setPlace(holderList);
		return holderList;
	}

	public static <T> String getTableName(Class<T> clazz) throws ClassNotFoundException {
		if (clazz.isAnnotationPresent(DbTable.class)) {
			Annotation annotation = clazz.getAnnotation(DbTable.class);
			DbTable dbTable = (DbTable) annotation;
			return dbTable.name();
		}
		return null;
	}

	/**
	 * This will assign a ResultMap to a holder based upon the
	 * {@link Holder#getField()} type .
	 * 
	 * @param holders holds the references to the fields of the class being
	 *                processed
	 */
	private static void generateRS(List<Holder> holders) {

		Map<String, Method> resultSetMap = getResultSetMap();
		for (Holder hold : holders) {
			String type = hold.getField().getType().getSimpleName().toLowerCase();
			Class<?> clz = hold.getField().getType();
			if (clz == Integer.class) {
				type = "int";
			}
			if (resultSetMap.containsKey(type)) {
				Method m = resultSetMap.get(type);
				hold.setRsMethod(m);
			}
		}

	}

	/**
	 * Gets all the getter methods for {@link java.sql.ResultSet} Inteface
	 * 
	 * @return Map of all the getter methods for {@link java.sql.ResultSet} Inteface
	 */
	private static Map<String, Method> getResultSetMap() {
		Map<String, Method> resultSetMap = new HashMap<>();
		for (Method method : ResultSet.class.getDeclaredMethods()) {
			if (method.getName().startsWith("get") && method.getParameterCount() == 1
					&& method.getParameterTypes()[0] == String.class) {
				resultSetMap.put(method.getName().replace("get", "").toLowerCase(), method);
			}
		}
		return resultSetMap;

	}

	private static String trimTrailingComma(String str) {

		if (str.isEmpty())
			return "";

		if (str.charAt(str.length() - 1) != ',') {
			return "";
		}

		return str.substring(0, str.length() - 1);
	}
}
