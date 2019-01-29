package ua.khpi.test.finalTask.logic;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ua.khpi.test.finalTask.entity.Account;
import ua.khpi.test.finalTask.entity.Request;
import ua.khpi.test.finalTask.entity.User;
import ua.khpi.test.finalTask.exception.ConnectionException;
import ua.khpi.test.finalTask.exception.DBException;

public class AdminLogic extends ApplicationLogic {
	private static final Logger LOG = LogManager.getLogger(AdminLogic.class);

	public List<Request> getAll() throws DBException, ConnectionException {
		try {
			return requestDao.getAll();
		} catch (ConnectionException e) {
			LOG.error(e);
			throw e;
		}
	}

	public User getEntityById(int usrId) throws DBException, ConnectionException {
		try {
			return userDao.getEntityById(usrId);
		} catch (ConnectionException e) {
			LOG.error(e);
			throw e;

		}
	}

	public void update(User user) throws DBException {
		try {
			userDao.update(user);
		} catch (ConnectionException e) {
			LOG.error(e);
		}
	}

	public void update(Account account) throws DBException {
		try {
			accountDao.update(account);
		} catch (ConnectionException e) {
			LOG.error(e);
		}

	}

	public Account getAccEntityById(int accId) throws DBException, ConnectionException {
		try {
			return accountDao.getEntityById(accId);
		} catch (ConnectionException e) {
			LOG.error(e);
			throw e;

		}

	}

	public void removeRequest(Request request) throws DBException {
		try {
			requestDao.removeRequest(request);
		} catch (ConnectionException e) {
			LOG.error(e);
		}
	}

	

	public List<User> getAllUsers() throws DBException, ConnectionException {
		try {
			return userDao.getAllUsers();
		} catch (ConnectionException e) {
			LOG.error(e);
			throw e;

		}
	}

	public List<Account> getAllAccounts() throws DBException, ConnectionException {
		try {
			return accountDao.getAllAccounts();
		} catch (ConnectionException e) {
			LOG.error(e);
			throw e;

		}
	}

}
