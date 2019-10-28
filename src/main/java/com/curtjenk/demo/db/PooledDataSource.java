package com.curtjenk.demo.db;

import java.sql.Connection;

import javax.annotation.PostConstruct;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.curtjenk.demo.exception.GeneralException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Simple database connection pooling that uses C3PO Pooled Data Source
 *
 */
@Component
public class PooledDataSource {

    private static final Logger logger = LoggerFactory.getLogger(PooledDataSource.class);

    @Value("${db.pool.max.size:5}")
    private int maxPoolSize;
    @Value("${db.pool.min.size:2}")
    private int minPoolSize;
    @Value("${db.pool.max.wait.millis:30000}")
    private int maxWaitMillis;
    @Value("${jdbc.url}")
    private String url;
    @Value("${jdbc.user}")
    private String user;
    @Value("${jdbc.pass}")
    private String password;

    private ComboPooledDataSource datasource;

    @PostConstruct
    public void init() {

        try {
            System.out.println("\n\t***************************************************");
            System.out.println("\tDatasource url: [" + url + "]   user: [" + user + "]");
            System.out.println("\t***************************************************\n");

            datasource = new ComboPooledDataSource();
            datasource.setDriverClass("org.postgresql.Driver");
            datasource.setJdbcUrl(url);
            datasource.setUser(user);
            datasource.setPassword(password);
            datasource.setInitialPoolSize(minPoolSize);
            datasource.setMinPoolSize(minPoolSize);
            datasource.setMaxPoolSize(maxPoolSize);
            datasource.setMaxStatements(maxPoolSize * 10);
            datasource.setAcquireIncrement(5);// get connections in batches of 5
            datasource.setMaxConnectionAge(3600);// discard any connection older than 1 hour to maintain freshness;
            datasource.setMaxIdleTime(300);// discard any connection that has been idle for more than 5 minutes
            datasource.setMaxIdleTimeExcessConnections(300);// discard any excess connection that has been idle for more
                                                            // than 5 minutes
            datasource.setIdleConnectionTestPeriod(600);// test the connection every 10 minutes for connectivity
            datasource.setTestConnectionOnCheckin(true);// test the connection on checkin
        } catch (Exception e) {
            logger.error("Error in PooledDataSource() constructor", e);
            // Can't do anything if the database doesn't work!!!
            throw new GeneralException(e.getMessage(), e);
        }
    }

    public Connection getConnection() throws GeneralException {
        try {
            return datasource.getConnection();
        } catch (Exception e) {
            logger.error("Error in getConnection()", e);
            throw new GeneralException(e.getMessage(), e);
        }
    }
}
