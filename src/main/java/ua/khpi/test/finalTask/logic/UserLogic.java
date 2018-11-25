package ua.khpi.test.finalTask.logic;

import java.math.BigDecimal;
import java.util.List;

import ua.khpi.test.finalTask.connection.util.TransactionManager;
import ua.khpi.test.finalTask.entity.Account;
import ua.khpi.test.finalTask.entity.Payment;
import ua.khpi.test.finalTask.entity.Request;
import ua.khpi.test.finalTask.exception.ConnectionException;
import ua.khpi.test.finalTask.exception.DBException;

public class UserLogic extends ApplicationLogic {

	public Account getEntityById(int accountId) throws DBException, ConnectionException {
		try {
			return accountDao.getEntityById(accountId);
		} catch (ConnectionException e) {
			e.printStackTrace();
			throw e;
		}

	}

	public void update(Account accountToLock) throws DBException {
		try {
			TransactionManager.beginTransaction();
			accountDao.update(accountToLock);
			TransactionManager.commitTransaction();
		} catch (ConnectionException e) {
			e.printStackTrace();
			TransactionManager.rollbackTransaction();
		}
	}

	public List<Account> getAccountsByUserId(int id) throws DBException, ConnectionException {
		try {
			return accountDao.getAccountsByUserId(id);
		} catch (ConnectionException e) {
			e.printStackTrace();
			throw e;
		}
	}

	public void insertReplenish(BigDecimal moneyAmount, int accountIdTo) throws DBException {
		try {
			TransactionManager.beginTransaction();

			paymentDao.insertReplenish(moneyAmount, accountIdTo);
			TransactionManager.commitTransaction();

		} catch (ConnectionException e) {
			e.printStackTrace();
			TransactionManager.rollbackTransaction();

		}

	}

	public void insertRemittance(BigDecimal moneyAmount, int accountIdFrom, int accountIdTo) throws DBException {
		try {
			TransactionManager.beginTransaction();
			paymentDao.insertRemittance(moneyAmount, accountIdFrom, accountIdTo);
			TransactionManager.commitTransaction();

		} catch (ConnectionException e) {
			e.printStackTrace();
			TransactionManager.rollbackTransaction();

		}
	}

	public List<Payment> getPaymentsByAccount(int accountId) throws DBException, ConnectionException {
		try {
			return paymentDao.getPaymentsByAccount(accountId);
		} catch (ConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}
	}

	public void addRequest(Request requestForUnlock) throws DBException {
		try {
			TransactionManager.beginTransaction();
			requestDao.addRequest(requestForUnlock);
			TransactionManager.commitTransaction();

		} catch (ConnectionException e) {
			e.printStackTrace();
			TransactionManager.rollbackTransaction();

		}
	}

	public void addEntity(Account newAccount) throws DBException {
		try {
			TransactionManager.beginTransaction();
			accountDao.addEntity(newAccount);
			TransactionManager.commitTransaction();

		} catch (ConnectionException e) {
			e.printStackTrace();
			TransactionManager.rollbackTransaction();

		}
	}
}
