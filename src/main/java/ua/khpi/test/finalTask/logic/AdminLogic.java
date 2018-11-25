package ua.khpi.test.finalTask.logic;

import java.util.List;

import ua.khpi.test.finalTask.connection.util.TransactionManager;
import ua.khpi.test.finalTask.entity.Account;
import ua.khpi.test.finalTask.entity.Request;
import ua.khpi.test.finalTask.entity.User;
import ua.khpi.test.finalTask.entity.bean.UserAccountsCount;
import ua.khpi.test.finalTask.exception.ConnectionException;
import ua.khpi.test.finalTask.exception.DBException;

public class AdminLogic extends ApplicationLogic {

	public List<Request> getAll() throws DBException, ConnectionException {
		try {
			return requestDao.getAll();
		} catch (ConnectionException e) {
			e.printStackTrace();
			throw e;
		}
	}

	public User getEntityById(int usrId) throws DBException, ConnectionException {
		try {
			return userDao.getEntityById(usrId);
		} catch (ConnectionException e) {
			e.printStackTrace();
			throw e;

		}
	}

	public void update(User user) throws DBException {
		try {
			userDao.update(user);
		} catch (ConnectionException e) {
			e.printStackTrace();
		}
	}

	public void update(Account account) throws DBException {
		try {
			TransactionManager.beginTransaction();
			accountDao.update(account);
			TransactionManager.commitTransaction();
		} catch (ConnectionException e) {
			e.printStackTrace();
			TransactionManager.rollbackTransaction();
		}

	}

	public Account getAccEntityById(int accId) throws DBException, ConnectionException {
		try {
			return accountDao.getEntityById(accId);
		} catch (ConnectionException e) {
			e.printStackTrace();
			throw e;

		}

	}

	public void removeRequest(Request request) throws DBException {
		try {
			TransactionManager.beginTransaction();
			requestDao.removeRequest(request);
			TransactionManager.commitTransaction();

		} catch (ConnectionException e) {
			e.printStackTrace();
			TransactionManager.rollbackTransaction();

		}
	}

	public List<UserAccountsCount> getAccountsWithBalanceGreaterThan(int parseInt)
			throws DBException, ConnectionException {

		try {
			return userAccountsDao.getAccountsWithBalanceGreaterThan(parseInt);
		} catch (ConnectionException e) {
			e.printStackTrace();
			throw e;

		}
	}

	public List<User> getAllUsers() throws DBException, ConnectionException {
		try {
			return userDao.getAllUsers();
		} catch (ConnectionException e) {
			e.printStackTrace();
			throw e;

		}
	}

	public List<Account> getAllAccounts() throws DBException, ConnectionException {
		try {
			return accountDao.getAllAccounts();
		} catch (ConnectionException e) {
			e.printStackTrace();
			throw e;

		}
	}

}
