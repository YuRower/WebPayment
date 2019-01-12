package ua.khpi.test.finalTask.dao.mysql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mysql.jdbc.Statement;

import ua.khpi.test.finalTask.connection.ProxyConnection;
import ua.khpi.test.finalTask.dao.AccountDAO;
import ua.khpi.test.finalTask.entity.Account;
import ua.khpi.test.finalTask.exception.ConnectionException;
import ua.khpi.test.finalTask.exception.DBException;
import ua.khpi.test.finalTask.exception.Messages;

public class AccountDAOImpl implements AccountDAO {

	private static final Logger LOG = LogManager.getLogger(AccountDAOImpl.class);

	private MysqlDAOFactory factory;

	public AccountDAOImpl(MysqlDAOFactory factory) {
		this.factory = factory;
	}

	public static final String ACCOUNT_ID = "id";
	public static final String ACCOUNT_USER_ID = "user_id";
	public static final String ACCOUNT_NAME = "name";
	public static final String ACCOUNT_BALANCE = "balance";
	public static final String ACCOUNT_STATUS = "account_status_id";
	public static final String ACCOUNT_CARD_ID = "card_id";

	
	private static final String SQL_FIND_ALL_ACCOUNTS = "SELECT * FROM accounts";
	private static final String SQL_FIND_ACCOUNT_BY_ID = "SELECT * FROM accounts WHERE account_id=?";
	private static final String SQL_UPDATE_ACCOUNT = "UPDATE accounts SET user_id = ?, name = ?, balance = ?,"
			+ " account_status_id = ? WHERE account_id=?";
	private static final String SQL_INSERT_ACCOUNT = "INSERT INTO accounts VALUES (DEFAULT,?, ?, DEFAULT, DEFAULT,?)";
	private static final String SQL_FIND_ACCOUNTS_BY_USER_ID = "SELECT * FROM accounts WHERE user_id=?";
	private static final String SQL_FIND_ACCOUNTS_BY_CARD_ID ="SELECT * FROM accounts WHERE card_id=?";

	@Override
	public Account getEntityById(int id) throws DBException, ConnectionException {
		Account account = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ProxyConnection con = null;
		try {
			con = factory.getProxyConnection();
			pstmt = con.prepareStatement(SQL_FIND_ACCOUNT_BY_ID);
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				account = extractAccount(rs);
			}
		} catch (SQLException ex) {
			LOG.error(Messages.ERR_CANNOT_OBTAIN_ACCOUNT_BY_ID, ex);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_ACCOUNT_BY_ID, ex);
		} finally {
			factory.close(con, pstmt, rs);
		}
		return account;
	}

	@Override
	public boolean update(Account entity) throws DBException, ConnectionException {
		boolean result;
		PreparedStatement pstmt = null;
		ProxyConnection con = null;
		try {
			con = factory.getProxyConnection();
			pstmt = con.prepareStatement(SQL_UPDATE_ACCOUNT);
			pstmt.setInt(1, entity.getUserId());
			pstmt.setString(2, entity.getName());
			pstmt.setBigDecimal(3, entity.getBalance());
			pstmt.setInt(4, entity.getAccountStatusId());
			pstmt.setInt(5, entity.getId());
			result = pstmt.executeUpdate() > 0;
		} catch (SQLException ex) {
			LOG.error(Messages.ERR_CANNOT_UPDATE_ACCOUNT, ex);
			throw new DBException(Messages.ERR_CANNOT_UPDATE_ACCOUNT, ex);
		} finally {
			factory.close(con);
			factory.close(pstmt);
		}
		return result;
	}

	@Override
	public boolean addEntity(Account entity) throws DBException, ConnectionException {
		boolean result = false;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ProxyConnection con = null;
		try {
			con = factory.getProxyConnection();
			pstmt = con.prepareStatement(SQL_INSERT_ACCOUNT, Statement.RETURN_GENERATED_KEYS);
			pstmt.setInt(1, entity.getUserId());
			pstmt.setString(2, entity.getName());
			pstmt.setInt(3, entity.getCardid());

			if (pstmt.executeUpdate() > 0) {
				rs = pstmt.getGeneratedKeys();
				if (rs.next()) {
					entity.setId(rs.getInt(1));
					result = true;
				}
			}
		} catch (SQLException ex) {
			LOG.error(Messages.ERR_CANNOT_INSERT_ACCOUNT, ex);
			throw new DBException(Messages.ERR_CANNOT_INSERT_ACCOUNT, ex);
		} finally {
			factory.close(con, pstmt, rs);
		}
		return result;
	}

	@Override
	public List<Account> getAccountsByUserId(int userId) throws DBException, ConnectionException {
		List<Account> result = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ProxyConnection con = null;
		try {
			con = factory.getProxyConnection();
			pstmt = con.prepareStatement(SQL_FIND_ACCOUNTS_BY_USER_ID);
			pstmt.setInt(1, userId);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				result.add(extractAccount(rs));
			}
		} catch (SQLException ex) {
			LOG.error(Messages.ERR_CANNOT_OBTAIN_ACCOUNTS_BY_USER_ID, ex);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_ACCOUNTS_BY_USER_ID, ex);
		} finally {
			factory.close(con, pstmt, rs);
		}
		return result;
	}

	@Override
	public List<Account> getAllAccounts() throws DBException, ConnectionException {
		List<Account> result = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ProxyConnection con = null;
		try {
			con = factory.getProxyConnection();
			pstmt = con.prepareStatement(SQL_FIND_ALL_ACCOUNTS);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				result.add(extractAccount(rs));
			}
		} catch (SQLException ex) {
			LOG.error(Messages.ERR_CANNOT_OBTAIN_ACCOUNTS_BY_USER_ID, ex);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_ACCOUNTS_BY_USER_ID, ex);
		} finally {
			factory.close(con, pstmt, rs);
		}
		return result;
	}

	@Override
	public boolean removeEntity(Account entity) throws DBException {
		throw new UnsupportedOperationException();
	}

	private Account extractAccount(ResultSet rs) throws SQLException {
		Account account = new Account();
		account.setId(rs.getInt(ACCOUNT_ID));
		account.setUserId(rs.getInt(ACCOUNT_USER_ID));
		account.setName(rs.getString(ACCOUNT_NAME));
		account.setBalance(rs.getBigDecimal(ACCOUNT_BALANCE));
		account.setAccountStatusId(rs.getInt(ACCOUNT_STATUS));
		account.setCardid(rs.getInt(ACCOUNT_CARD_ID));

		return account;
	}

	@Override
	public List<Account> getAccountsByCardID(int cardId) throws DBException, ConnectionException  {
		List<Account> result = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ProxyConnection con = null;
		try {
			con = factory.getProxyConnection();
			pstmt = con.prepareStatement(SQL_FIND_ACCOUNTS_BY_CARD_ID);
			pstmt.setInt(1, cardId);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				result.add(extractAccount(rs));
			}
		} catch (SQLException ex) {
			LOG.error(Messages.ERR_CANNOT_OBTAIN_CREDIT_CARD_BY_ID, ex);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_CREDIT_CARD_BY_ID, ex);
		} finally {
			factory.close(con, pstmt, rs);
		}
		return result;
	}
}
