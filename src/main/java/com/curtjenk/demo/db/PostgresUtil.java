package com.curtjenk.demo.db;

import java.lang.reflect.Constructor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.curtjenk.demo.exception.DatabaseException;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.mutable.MutableInt;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.statement.Query;
import org.jdbi.v3.core.statement.Update;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * A generic PostGres utility class.
 *
 * @author
 */
@Repository
public class PostgresUtil {

	private static Logger logger = LoggerFactory.getLogger(PostgresUtil.class);

	@Autowired
	PooledDataSource pooledDataSource;

	/**
	 * SELECTS from postgres and maps the ResultSet to the bean.
	 * 
	 * @param sql
	 * @param clz bean class
	 * @return
	 */
	public <T> List<T> select(String sql, Class<T> clz, Object... objects) {
		logger.info("select(), entered. type: {}, sql: {}", clz.getSimpleName(), sql);
		Constructor<T> constructor;
		try {
			constructor = clz.getConstructor(ResultSet.class);
		} catch (Exception e) {
			throw new RuntimeException(
					"Couldn't find a constructor that takes ResultSet as parameter for: " + clz.getSimpleName());
		}

		try (Handle handle = Jdbi.open(pooledDataSource.getConnection()); Query q = handle.createQuery(sql);) {
			for (int i = 0; i < objects.length; i++) {
				q.bind(i, objects[i]);
			}
			List<T> list = q.map((rs, ctx) -> {
				try {
					return constructor.newInstance(rs);
				} catch (Exception e) {
					e.printStackTrace();
					handle.close();
					throw new RuntimeException(
							"Couldn't instantiate a constructor that takes ResultSet as parameter for: "
									+ clz.getSimpleName());
				}
			}).list();
			logger.info("select(), exiting. type: {}, list-size: {}", clz.getSimpleName(), list.size());
			return list;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	/**
	 * SELECTS from postgres and maps the ResultSet to String.
	 * 
	 * @param sql
	 * @return
	 */
	public List<String> selectSQL(String sql, Object... objects) {
		logger.info("select(), entered. sql: {}", sql);

		try (Handle handle = Jdbi.open(pooledDataSource.getConnection()); Query q = handle.createQuery(sql);) {
			for (int i = 0; i < objects.length; i++) {
				q.bind(i, objects[i]);
			}
			List<String> list = q.map((rs, ctx) -> {
				return rs.getString(1);
			}).list();
			handle.close();
			logger.info("select(), exiting. list-size: {}", list.size());
			return list;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public int upsert(List<? extends IBindPreparedStatement> list, String schemaName) throws DatabaseException {
		return upsert(list, schemaName, (Connection) null);
	}

	/**
	 * For batch upserts.
	 * 
	 * @param list
	 * @return
	 * @throws TaxDatabaseException
	 */
	public int upsert(List<? extends IBindPreparedStatement> list, String schemaName, Connection conn)
			throws DatabaseException {
		IBindPreparedStatement itemOne = null;
		boolean localConnection = conn == null;
		try {
			if (list == null || list.isEmpty()) {
				return 0;
			}

			final MutableInt total = new MutableInt(0);
			itemOne = list.get(0);
			logger.info("local?: {} upsert(list), entered. type: {}, list-size: {}", localConnection,
					itemOne.getClass().getSimpleName(), list.size());

			// create a local connection that commits if no connection is passed in
			final Connection myConn = localConnection ? pooledDataSource.getConnection() : conn;
			try (PreparedStatement ps = myConn.prepareStatement(itemOne.getUpsertSql(schemaName))) {

				if (localConnection) {
					myConn.setAutoCommit(false);
				}

				final MutableInt count = new MutableInt(0);

				list.forEach(psBindable -> {
					psBindable.bindPreparedStatement(ps, true);
					count.increment();
					total.increment();

					if (count.intValue() > 500) {
						count.setValue(0);
						try {
							ps.executeBatch();
							if (localConnection) {
								myConn.commit();
							}
						} catch (Exception e) {
							String error = "upsert(list): Failed to exeuteBatch(), count: " + total.intValue();
							logger.error(error, e);
							throw new RuntimeException(error, e);
						}
					}
				});
				ps.executeBatch();

				if (localConnection) {
					myConn.commit();
				}
			} finally {
				if (localConnection && myConn != null) {
					try {
						myConn.close();
					} catch (SQLException e) {
						logger.error(e.getMessage(), e);
						// nothing to do...we tried to close it
					}
				}
			}

			logger.info("upsert(list), exiting. type: {}, upsert-count: {}", itemOne.getClass().getSimpleName(),
					total.intValue());
			return total.intValue();
		} catch (Exception catchAll) {
			if (itemOne != null) {
				logger.error("upsert(list), catchAll exception: {}, type: {}", catchAll.getMessage(),
						itemOne.getClass().getSimpleName(), catchAll);
			} else {
				logger.error("upsert(list), catchAll exception: {}, type is missing", catchAll.getMessage(), catchAll);
			}

			throw new DatabaseException(catchAll.getMessage(), catchAll);
		}
	}

	/**
	 * For single upserts.
	 * 
	 * @param iBindPreparedStatement
	 */
	public void upsert(IBindPreparedStatement iBindPreparedStatement, String schemaName) {
		logger.info("upsert(single), entered. type: {}", iBindPreparedStatement.getClass().getSimpleName());

		try (Connection conn = pooledDataSource.getConnection();
				PreparedStatement ps = conn.prepareStatement(iBindPreparedStatement.getUpsertSql(schemaName))) {
			iBindPreparedStatement.bindPreparedStatement(ps, false);
			ps.executeUpdate();
		} catch (Exception catchAll) {
			logger.error("upsert(single), catchAll exception: {}, type: {}", catchAll.getMessage(),
					iBindPreparedStatement.getClass().getSimpleName(), catchAll);
			logger.error(ReflectionToStringBuilder.toString(iBindPreparedStatement));
			catchAll.printStackTrace();
		}
	}

	public void update(String sql, Object... objects) {
		logger.info("update(), entered. sql: {}", sql);
		try (Handle handle = Jdbi.open(pooledDataSource.getConnection());) {
			try (Update u = handle.createUpdate(sql);) {
				for (int i = 0; i < objects.length; i++) {
					u.bind(i, objects[i]);
				}
				u.execute();
			}
		}
	}
}
