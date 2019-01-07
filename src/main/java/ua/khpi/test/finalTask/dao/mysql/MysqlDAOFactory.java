package ua.khpi.test.finalTask.dao.mysql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ua.khpi.test.finalTask.connection.ConnectionPool;
import ua.khpi.test.finalTask.connection.ProxyConnection;
import ua.khpi.test.finalTask.dao.AbstractDAOFactory;
import ua.khpi.test.finalTask.dao.AccountDAO;
import ua.khpi.test.finalTask.dao.CardDAO;
import ua.khpi.test.finalTask.dao.PaymentsDAO;
import ua.khpi.test.finalTask.dao.RequestDAO;
import ua.khpi.test.finalTask.dao.UserAccountsCountDAO;
import ua.khpi.test.finalTask.dao.UserDAO;
import ua.khpi.test.finalTask.exception.ConnectionException;
import ua.khpi.test.finalTask.exception.DBException;
import ua.khpi.test.finalTask.exception.Messages;

public class MysqlDAOFactory extends AbstractDAOFactory {

	private static final Logger LOG = LogManager.getLogger(MysqlDAOFactory.class);

	private static MysqlDAOFactory instance;
	private static ProxyConnection connection;

	public static synchronized MysqlDAOFactory getInstance() throws DBException {
		if (instance == null) {
			instance = new MysqlDAOFactory();
		}
		return instance;
	}

	private MysqlDAOFactory() {
	}

	public ProxyConnection getProxyConnection() throws SQLException, ConnectionException {
		connection = ConnectionPool.getInstance().getConnection();
		return connection;

	}

	public void close(ProxyConnection con) {
		if (con != null) {
			try {
				con.close();
			} catch (SQLException ex) {
				LOG.error(Messages.ERR_CANNOT_CLOSE_CONNECTION, ex);
			}
		}
	}

	public void close(Statement stmt) {
		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException ex) {
				LOG.error(Messages.ERR_CANNOT_CLOSE_STATEMENT, ex);
			}
		}
	}

	public void close(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException ex) {
				LOG.error(Messages.ERR_CANNOT_CLOSE_RESULTSET, ex);
			}
		}
	}

	public void close(ProxyConnection con, Statement stmt, ResultSet rs) {
		close(rs);
		close(stmt);
		close(con);
	}

	@Override
	public UserDAO getUserDAO() {
		return new UserDAOImpl(instance);
	}

	@Override
	public AccountDAO getAccountDAO() {
		return new AccountDAOImpl(instance);
	}

	@Override
	public PaymentsDAO getPaymentsDAO() {
		return new PaymentsDAOImpl(instance);
	}

	@Override
	public RequestDAO getRequestDAO() {
		return new RequestDAOImpl(instance);
	}

	@Override
	public UserAccountsCountDAO getUserAccountsCountDAO() {
		return new UserAccountsCountDAOImpl(instance);
	}

	@Override
	public CardDAO getCardDAO() {
		return new CardDAOImpl(instance);
	}

}
