package ua.khpi.test.finalTask.dao.mysql;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ua.khpi.test.finalTask.connection.ProxyConnection;
import ua.khpi.test.finalTask.dao.PaymentsDAO;
import ua.khpi.test.finalTask.entity.Payment;
import ua.khpi.test.finalTask.exception.ConnectionException;
import ua.khpi.test.finalTask.exception.DBException;
import ua.khpi.test.finalTask.exception.Messages;

public class PaymentsDAOImpl implements PaymentsDAO {

	private static final Logger LOG = LogManager.getLogger(PaymentsDAOImpl.class);

	private MysqlDAOFactory factory;

	public PaymentsDAOImpl(MysqlDAOFactory factory) {
		this.factory = factory;
	}

	public static final String PAYMENT_ID = "payment_id";
	public static final String PAYMENT_DATE = "date";
	public static final String PAYMENT_MONEY = "money_amount";
	public static final String PAYMENT_FROM = "account_id_from";
	public static final String PAYMENT_TO = "account_id_to";
	public static final String PAYMENT_TYPE = "payment_type_id";

	private static final String SQL_FIND_PAYMENT_BY_ID = "SELECT * FROM payments WHERE payment_id=?";
	private static final String SQL_FIND_PAYMENTS_BY_ACCOUNT = "SELECT DISTINCT * FROM payments WHERE account_id_from=? OR account_id_to=?";
	private static final String SQL_INSERT_PAYMENT = "INSERT INTO payments VALUES (DEFAULT, now(), ?, ?, ?, 0)";
	private static final String SQL_PAYMENT_FROM = "UPDATE accounts SET balance = (balance - ?) where account_id = ?";
	private static final String SQL_PAYMENT_TO = "UPDATE accounts SET balance = (balance + ?) where account_id = ?";
	private static final String SQL_REPLENISH_PAYMENT = "INSERT INTO payments VALUES (DEFAULT, now(), ?, null, ?, 1)";
	
	@Override
	public boolean insertReplenish(BigDecimal amount, int accountId) throws DBException, ConnectionException {
		boolean result = false;		
		PreparedStatement add = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ProxyConnection con = null;
		try {
			con = factory.getProxyConnection();
			
			add = con.prepareStatement(SQL_PAYMENT_TO);
			add.setBigDecimal(1, amount);
			add.setInt(2, accountId);
			add.executeUpdate();
		
			pstmt = con.prepareStatement(SQL_REPLENISH_PAYMENT);
			pstmt.setBigDecimal(1, amount);
			pstmt.setInt(2, accountId);
			result  = pstmt.executeUpdate() > 0;
		} catch (SQLException ex) {
			LOG.error(Messages.ERR_CANNOT_INSERT_PAYMENT_REPLENISH, ex);
			throw new DBException(Messages.ERR_CANNOT_INSERT_PAYMENT_REPLENISH, ex);
		} finally {
			factory.close(con, pstmt, rs);
		}
		return result;
	}
	
	@Override
	public boolean insertRemittance(BigDecimal amount, int accountFrom, int accountTo) throws DBException, ConnectionException {
		boolean result = false;		
		PreparedStatement from = null;
		PreparedStatement to = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ProxyConnection con = null;
		try {
			con = factory.getProxyConnection();
			
			from = con.prepareStatement(SQL_PAYMENT_FROM);
			from.setBigDecimal(1, amount);
			from.setInt(2, accountFrom);
			from.executeUpdate();
			
			to = con.prepareStatement(SQL_PAYMENT_TO);
			to.setBigDecimal(1, amount);
			to.setInt(2, accountTo);
			to.executeUpdate();
			
			pstmt = con.prepareStatement(SQL_INSERT_PAYMENT);
			pstmt.setBigDecimal(1, amount);
			pstmt.setInt(2, accountFrom);
			pstmt.setInt(3, accountTo);
			result = pstmt.executeUpdate() > 0;
		} catch (SQLException ex) {
			LOG.error(Messages.ERR_CANNOT_UPDATE_PAYMENT, ex);
			throw new DBException(Messages.ERR_CANNOT_UPDATE_PAYMENT, ex);
		} finally {
			factory.close(con, pstmt, rs);
		}
		return result;
	}

	@Override
	public List<Payment> getPaymentsByAccount(int accountId) throws DBException, ConnectionException {
		List<Payment> result = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ProxyConnection con = null;
		try {
			con = factory.getProxyConnection();
			pstmt = con.prepareStatement(SQL_FIND_PAYMENTS_BY_ACCOUNT);
			pstmt.setInt(1, accountId);
			pstmt.setInt(2, accountId);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				result.add(extractPayment(rs));
			}
		} catch (SQLException ex) {
			LOG.error(Messages.ERR_CANNOT_OBTAIN_PAYMENTS_BY_ACCOUNT, ex);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_PAYMENTS_BY_ACCOUNT, ex);
		} finally {
			factory.close(con, pstmt, rs);
		}
		return result;
	}
	
	@Override
	public Payment getPaymentById(int paymentId) throws DBException, ConnectionException {
		Payment payment = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ProxyConnection con = null;
		try {
			con = factory.getProxyConnection();
			pstmt = con.prepareStatement(SQL_FIND_PAYMENT_BY_ID);
			pstmt.setInt(1, paymentId);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				payment = extractPayment(rs);
			}
		} catch (SQLException ex) {
			LOG.error(Messages.ERR_CANNOT_OBTAIN_PAYMENT_BY_ID, ex);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_PAYMENT_BY_ID, ex);
		} finally {
			factory.close(con, pstmt, rs);
		}
		return payment;
	}

	private Payment extractPayment(ResultSet rs) throws SQLException {
		Payment payment = new Payment();
		payment.setId(rs.getInt(PAYMENT_ID));
		payment.setDate(rs.getTimestamp(PAYMENT_DATE));
		payment.setMoneyAmount(rs.getBigDecimal(PAYMENT_MONEY));
		payment.setAccountIdFrom(rs.getInt(PAYMENT_FROM));
		payment.setAccountIdTo(rs.getInt(PAYMENT_TO));
		payment.setPaymentTypeId(rs.getInt(PAYMENT_TYPE));
		return payment;
	}
}
