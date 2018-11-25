package ua.khpi.test.finalTask.connection.util;

import java.sql.SQLException;

import ua.khpi.test.finalTask.connection.ConnectionPool;
import ua.khpi.test.finalTask.connection.ProxyConnection;
import ua.khpi.test.finalTask.exception.ConnectionException;

public class TransactionManager {

	private TransactionManager() {

	}

	public static void beginTransaction() {
	
			try {
				ConnectionPool.getInstance().getConnection().setAutoCommit(false);
			} catch (ConnectionException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}

	public static void rollbackTransaction() {
		try {
			ProxyConnection connection = ConnectionPool.getInstance().getConnection();
			connection.rollback();
			//connection.setAutoCommit(true);
		} catch (SQLException | ConnectionException e) {
			e.printStackTrace();
		}
	}

	public static void commitTransaction() {
		try {
			ProxyConnection connection = ConnectionPool.getInstance().getConnection();
			connection.commit();
		//	connection.setAutoCommit(true);
		} catch (SQLException | ConnectionException e) {
			e.printStackTrace();
		}
	}

}