package ua.khpi.test.finalTask.dao.mysql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ua.khpi.test.finalTask.connection.ProxyConnection;
import ua.khpi.test.finalTask.dao.RequestDAO;
import ua.khpi.test.finalTask.entity.Request;
import ua.khpi.test.finalTask.exception.ConnectionException;
import ua.khpi.test.finalTask.exception.DBException;
import ua.khpi.test.finalTask.exception.Messages;

public class RequestDAOImpl implements RequestDAO {

	private static final Logger LOG = LogManager.getLogger(RequestDAOImpl.class);

	private MysqlDAOFactory factory;

	public RequestDAOImpl(MysqlDAOFactory factory) {
		this.factory = factory;
	}

	public static final String REQUEST_USER_ID = "user_id";
	public static final String REQUEST_ACCOUNT_ID = "account_id";

	private static final String SQL_FIND_REQUESTS = "SELECT * FROM requests";
	private static final String SQL_INSERT_REQUEST = "INSERT INTO requests VALUES (?, ?)";
	private static final String SQL_DELETE_REQUEST = "DELETE FROM requests WHERE user_id=? AND account_id=?";

	@Override
	public boolean addRequest(Request request) throws DBException, ConnectionException {
		boolean result = false;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ProxyConnection con = null;
		try {
			con = factory.getProxyConnection();
			pstmt = con.prepareStatement(SQL_INSERT_REQUEST);
			pstmt.setInt(1, request.getUserId());
			pstmt.setInt(2, request.getAccountId());
			result = pstmt.executeUpdate() > 0;
		} catch (SQLException ex) {
			LOG.error(Messages.ERR_CANNOT_INSERT_REQUEST, ex);
			throw new DBException(Messages.ERR_CANNOT_INSERT_REQUEST, ex);
		} finally {
			factory.close(con, pstmt, rs);
		}
		return result;
	}
	
	@Override
	public boolean removeRequest(Request request) throws DBException, ConnectionException {
		boolean result = false;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ProxyConnection con = null;
		try {
			con = factory.getProxyConnection();
			pstmt = con.prepareStatement(SQL_DELETE_REQUEST);
			pstmt.setInt(1, request.getUserId());
			pstmt.setInt(2, request.getAccountId());
			result = pstmt.executeUpdate() > 0;
		} catch (SQLException ex) {
			LOG.error(Messages.ERR_CANNOT_DELETE_REQUEST, ex);
			throw new DBException(Messages.ERR_CANNOT_DELETE_REQUEST, ex);
		} finally {
			factory.close(con, pstmt, rs);
		}
		return result;
	}

	@Override
	public List<Request> getAll() throws DBException, ConnectionException {
		List<Request> result = new ArrayList<>();
		Statement stmt = null;
		ResultSet rs = null;
		ProxyConnection con = null;
		try {
			con = factory.getProxyConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(SQL_FIND_REQUESTS);
			while (rs.next()) {
				result.add(extractRequest(rs));
			}
		} catch (SQLException ex) {
			LOG.error(Messages.ERR_CANNOT_OBTAIN_REQUESTS, ex);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_REQUESTS, ex);
		} finally {
			factory.close(con, stmt, rs);
		}
		return result;
	}

	private Request extractRequest(ResultSet rs) throws SQLException {
		Request request = new Request();
		request.setUserId(rs.getInt(REQUEST_USER_ID));
		request.setAccountId(rs.getInt(REQUEST_ACCOUNT_ID));
		return request;
	}


}
