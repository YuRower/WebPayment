package ua.khpi.test.finalTask.connection.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public final class DBConnector {
	private static final Logger LOG = LogManager.getLogger(DBConnector.class);

    static {
        try {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            LOG.debug( "database driver was registered successfully.");
        } catch (SQLException e) {
        	 LOG.debug( "database driver cant't be registered, throwing RuntimeException.");
            throw new RuntimeException("database driver cant't be registered.", e);
        }
}
	private static final String URL = DBResourceManager.getProperty(DBResourceManager.URL);
	private static final String USER = DBResourceManager.getProperty(DBResourceManager.USER);
	private static final String PASSWORD = DBResourceManager.getProperty(DBResourceManager.PASSWORD);

	public static Connection getConnection() throws SQLException {
		LOG.info(URL + " " + PASSWORD + " " + USER);
		Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
		return connection;
	}
}