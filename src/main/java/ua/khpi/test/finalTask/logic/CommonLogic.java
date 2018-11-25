package ua.khpi.test.finalTask.logic;

import ua.khpi.test.finalTask.connection.util.TransactionManager;
import ua.khpi.test.finalTask.entity.User;
import ua.khpi.test.finalTask.entity.enums.UserStatus;
import ua.khpi.test.finalTask.exception.ConnectionException;
import ua.khpi.test.finalTask.exception.DBException;

public class CommonLogic extends ApplicationLogic {

	public User findUserByEmail(String email) throws DBException, ConnectionException {
		try {
			return userDao.findUserByEmail(email);
		} catch (ConnectionException e) {
			e.printStackTrace();
			throw e;
		}
	}

	public void update(User user) throws DBException {
		try {
			TransactionManager.beginTransaction();

			userDao.update(user);
			TransactionManager.commitTransaction();

		} catch (ConnectionException e) {
			e.printStackTrace();
			TransactionManager.rollbackTransaction();

		}
	}

	public void newUserWithDefaultValues(User user) throws DBException {
		try {
			TransactionManager.beginTransaction();
			userDao.newUserWithDefaultValues(user);
			TransactionManager.commitTransaction();

		} catch (ConnectionException e) {
			e.printStackTrace();
			TransactionManager.rollbackTransaction();

		}
	}

	public boolean isEmailInUse(String email) throws DBException, ConnectionException {
		return userDao.isEmailInUse(email);	
	}

}
