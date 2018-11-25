package ua.khpi.test.finalTask.dao.mysql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ua.khpi.test.finalTask.connection.ProxyConnection;
import ua.khpi.test.finalTask.dao.UserAccountsCountDAO;
import ua.khpi.test.finalTask.entity.bean.UserAccountsCount;
import ua.khpi.test.finalTask.exception.ConnectionException;
import ua.khpi.test.finalTask.exception.DBException;
import ua.khpi.test.finalTask.exception.Messages;

public class UserAccountsCountDAOImpl implements UserAccountsCountDAO {

	private static final Logger LOG = LogManager.getLogger(UserAccountsCountDAOImpl.class);

	private MysqlDAOFactory factory;

	public UserAccountsCountDAOImpl(MysqlDAOFactory factory) {
		this.factory = factory;
	}

	public static final String REQUEST_USER_EMAIL = "email";
	public static final String REQUEST_ACCOUNTS_COUNT = "accounts";


	private static final String SQL_FIND_INFO = "select users.email, count(accounts.account_id) as 'accounts' from users " + 
			"inner join accounts on accounts.user_id = users.user_id " + 
			"where accounts.balance > ? " + 
			"group by users.email order by count(accounts.account_id) DESC;";

	@Override
	public List<UserAccountsCount> getAccountsWithBalanceGreaterThan(int value) throws DBException, ConnectionException {
		List<UserAccountsCount> result = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ProxyConnection con = null;
		try {
			con = factory.getProxyConnection();
			pstmt = con.prepareStatement(SQL_FIND_INFO);
			pstmt.setInt(1, value);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				result.add(extractUserAccountsCount(rs));
			}
		} catch (SQLException ex) {
			LOG.error(Messages.ERR_CANNOT_GET_USER_ACCS, ex);
			throw new DBException(Messages.ERR_CANNOT_GET_USER_ACCS, ex);
		} finally {
			factory.close(con, pstmt, rs);
		}
		return result;
	}

	private UserAccountsCount extractUserAccountsCount(ResultSet rs) throws SQLException {
		UserAccountsCount instance = new UserAccountsCount();
		instance.setEmail(rs.getString(REQUEST_USER_EMAIL));
		instance.setAccounts(rs.getInt(REQUEST_ACCOUNTS_COUNT));
		return instance;
	}

}
