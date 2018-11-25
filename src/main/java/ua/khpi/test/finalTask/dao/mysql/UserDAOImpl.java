/**
 * 
 */
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
import ua.khpi.test.finalTask.dao.UserDAO;
import ua.khpi.test.finalTask.entity.User;
import ua.khpi.test.finalTask.exception.ConnectionException;
import ua.khpi.test.finalTask.exception.DBException;
import ua.khpi.test.finalTask.exception.Messages;


public class UserDAOImpl implements UserDAO {

	private static final Logger LOG = LogManager.getLogger(UserDAOImpl.class);

	private MysqlDAOFactory factory;

	public UserDAOImpl(MysqlDAOFactory factory) {
		this.factory = factory;
	}

	public static final String USER_ID = "user_id";
	public static final String USER_NAME = "name";
	public static final String USER_SURNAME = "surname";
	public static final String USER_EMAIL = "email";
	public static final String USER_EMAIL_VERIFICATION = "email_verification";
	public static final String USER_PASSWORD = "password";
	public static final String USER_ROLE_ID = "user_role_id";
	public static final String USER_STATUS_ID = "user_status_id";
	
	private static final String SQL_REMOVE_ADMIN= "DELETE FROM users WHERE user_id=?";
	private static final String SQL_FIND_ADMINS= "SELECT * FROM users WHERE user_role_id=1";
	private static final String SQL_ALL_FIND_USERS= "SELECT * FROM users";

	private static final String SQL_FIND_USER_BY_ID = "SELECT * FROM users WHERE user_id=?";
	private static final String SQL_FIND_USER_BY_EMAIL = "SELECT * FROM users WHERE email=?";
	private static final String SQL_USER_WITH_EMAIL_EXISTS = "SELECT EXISTS(SELECT * FROM Users WHERE email = ?)";
	private static final String SQL_INSERT_USER_FULL_INFO = "INSERT INTO users VALUES (DEFAULT,?, ?, ?, ?, ?, ?, ?)";
	private static final String SQL_INSERT_USER_SHORT_VARIANT = "INSERT INTO users VALUES (DEFAULT, ?, ?, ?, ?, ?, DEFAULT, DEFAULT)";
	private static final String SQL_UPDATE_USER = "UPDATE users SET name = ?, surname = ?, email = ?, email_verification = ?, "
			+ "password=?, user_role_id = ?, user_status_id = ? WHERE user_id=?";

	@Override
	public User getEntityById(int id) throws DBException, ConnectionException {
		User user = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ProxyConnection con = null;
		try {
			con = factory.getProxyConnection();
			pstmt = con.prepareStatement(SQL_FIND_USER_BY_ID);
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				user = extractUser(rs);
			}
		} catch (SQLException ex) {
			LOG.error(Messages.ERR_CANNOT_OBTAIN_USER_BY_ID, ex);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_USER_BY_ID, ex);
		} finally {
			factory.close(con, pstmt, rs);
		}
		return user;
	}

	@Override
	public boolean update(User entity) throws DBException, ConnectionException {
		boolean result;
		PreparedStatement pstmt = null;
		ProxyConnection con = null;
		try {
			con = factory.getProxyConnection();
			pstmt = con.prepareStatement(SQL_UPDATE_USER);
			pstmt.setString(1, entity.getName());
			pstmt.setString(2, entity.getSurname());
			pstmt.setString(3, entity.getEmail());
			pstmt.setString(4, entity.getEmailVerification());
			pstmt.setString(5, entity.getPassword());
			pstmt.setInt(6, entity.getUserTypeId());
			pstmt.setInt(7, entity.getUserStatusId());
			pstmt.setInt(8, entity.getId());
			result = pstmt.executeUpdate() > 0;
		} catch (SQLException ex) {
			LOG.error(Messages.ERR_CANNOT_UPDATE_USER, ex);
			throw new DBException(Messages.ERR_CANNOT_UPDATE_USER, ex);
		} finally {
			factory.close(con);
			factory.close(pstmt);
		}
		return result;
	}

	@Override
	public boolean addEntity(User entity) throws DBException, ConnectionException {
		boolean result = false;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ProxyConnection con = null;
		try {
			con = factory.getProxyConnection();
			pstmt = con.prepareStatement(SQL_INSERT_USER_FULL_INFO, Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, entity.getName());
			pstmt.setString(2, entity.getSurname());
			pstmt.setString(3, entity.getEmail());
			pstmt.setString(4, entity.getEmailVerification());
			pstmt.setString(5, entity.getPassword());
			pstmt.setInt(6, entity.getUserTypeId());
			pstmt.setInt(7, entity.getUserStatusId());
			if (pstmt.executeUpdate() > 0) {
				rs = pstmt.getGeneratedKeys();
				if (rs.next()) {
					entity.setId(rs.getInt(1));
					result = true;
				}
			}
		} catch (SQLException ex) {
			LOG.error(Messages.ERR_CANNOT_INSERT_USER, ex);
			throw new DBException(Messages.ERR_CANNOT_INSERT_USER, ex);
		} finally {
			factory.close(con, pstmt, rs);
		}
		return result;
	}

	@Override
	public boolean newUserWithDefaultValues(User user) throws DBException, ConnectionException {
		boolean result = false;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ProxyConnection con = null;
		try {
			con = factory.getProxyConnection();
			pstmt = con.prepareStatement(SQL_INSERT_USER_SHORT_VARIANT, Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, user.getName());
			pstmt.setString(2, user.getSurname());
			pstmt.setString(3, user.getEmail());
			pstmt.setString(4, user.getEmailVerification());
			pstmt.setString(5, user.getPassword());
			if (pstmt.executeUpdate() > 0) {
				rs = pstmt.getGeneratedKeys();
				if (rs.next()) {
					user.setId(rs.getInt(1));
					result = true;
				}
			}
		} catch (SQLException ex) {
			LOG.error(Messages.ERR_CANNOT_INSERT_USER, ex);
			throw new DBException(Messages.ERR_CANNOT_INSERT_USER, ex);
		} finally {
			factory.close(con, pstmt, rs);
		}
		return result;
	}

	@Override
	public User findUserByEmail(String email) throws DBException, ConnectionException {
		User user = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ProxyConnection con = null;
		try {
			con = factory.getProxyConnection();
			pstmt = con.prepareStatement(SQL_FIND_USER_BY_EMAIL);
			pstmt.setString(1, email);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				user = extractUser(rs);
			}
		} catch (SQLException ex) {
			LOG.error(Messages.ERR_CANNOT_OBTAIN_USER_BY_LOGIN, ex);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_USER_BY_LOGIN, ex);
		} finally {
			factory.close(con, pstmt, rs);
		}
		return user;
	}

	@Override
	public boolean isEmailInUse(String email) throws DBException, ConnectionException {
		boolean result = true;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ProxyConnection con = null;
		try {
			con = factory.getProxyConnection();
			pstmt = con.prepareStatement(SQL_USER_WITH_EMAIL_EXISTS);
			pstmt.setString(1, email);
			rs = pstmt.executeQuery();
			rs.next();
			result = rs.getBoolean(1);
		} catch (SQLException ex) {
			LOG.error(Messages.ERR_CANNOT_VERIFY_EMAIL, ex);
			throw new DBException(Messages.ERR_CANNOT_VERIFY_EMAIL, ex);
		} finally {
			factory.close(con, pstmt, rs);
		}
		return result;
	}
	
	@Override
	public List<User> getAllAdmins() throws DBException, ConnectionException {
		List<User> result = new ArrayList<>();
		Statement stmt = null;
		ResultSet rs = null;
		ProxyConnection con = null;
		try {
			con = factory.getProxyConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(SQL_FIND_ADMINS);
			while (rs.next()) {
				result.add(extractUser(rs));
			}
		} catch (SQLException ex) {
			LOG.error(Messages.ERR_CANNOT_OBTAIN_ADMINS, ex);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_ADMINS, ex);
		} finally {
			factory.close(con, stmt, rs);
		}
		return result;
	}
	@Override
	public List<User> getAllUsers() throws DBException, ConnectionException {
		List<User> result = new ArrayList<>();
		Statement stmt = null;
		ResultSet rs = null;
		ProxyConnection con = null;
		try {
			con = factory.getProxyConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(SQL_ALL_FIND_USERS);
			while (rs.next()) {
				result.add(extractUser(rs));
			}
		} catch (SQLException ex) {
			LOG.error(Messages.ERR_CANNOT_OBTAIN_ADMINS, ex);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_ADMINS, ex);
		} finally {
			factory.close(con, stmt, rs);
		}
		return result;
	}
	
	@Override
	public boolean removeEntity(User entity) throws DBException, ConnectionException {
		boolean result = false;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ProxyConnection con = null;
		try {
			con = factory.getProxyConnection();
			pstmt = con.prepareStatement(SQL_REMOVE_ADMIN);
			pstmt.setInt(1, entity.getId());
			result = pstmt.executeUpdate() > 0;
		} catch (SQLException ex) {
			LOG.error(Messages.ERR_CANNOT_DELETE_ADMIN, ex);
			throw new DBException(Messages.ERR_CANNOT_DELETE_ADMIN, ex);
		} finally {
			factory.close(con, pstmt, rs);
		}
		return result;
	}

	/**
	 * Method that extracts entities from result set
	 * @param rs result set
	 * @return entity
	 * @throws SQLException
	 */
	private User extractUser(ResultSet rs) throws SQLException {
		User user = new User();
		user.setId(rs.getInt(USER_ID));
		user.setName(rs.getString(USER_NAME));
		user.setSurname(rs.getString(USER_SURNAME));
		user.setEmail(rs.getString(USER_EMAIL));
		user.setEmailVerification(rs.getString(USER_EMAIL_VERIFICATION));
		user.setPassword(rs.getString(USER_PASSWORD));
		user.setUserTypeId(rs.getInt(USER_ROLE_ID));
		user.setUserStatusId(rs.getInt(USER_STATUS_ID));
		return user;
	}
}
