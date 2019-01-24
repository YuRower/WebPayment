package ua.khpi.test.finalTask.logic;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ua.khpi.test.finalTask.entity.User;
import ua.khpi.test.finalTask.exception.ConnectionException;
import ua.khpi.test.finalTask.exception.DBException;

public class CommonLogic extends ApplicationLogic {
	private static final Logger LOG = LogManager.getLogger(CommonLogic.class);

	public User findUserByEmail(String email) throws DBException, ConnectionException {
		try {
			return userDao.findUserByEmail(email);
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

	public boolean newUserWithDefaultValues(User user) throws DBException {
		boolean result = false ;
		try {
			 result = userDao.newUserWithDefaultValues(user);
		} catch (ConnectionException e) {
			e.printStackTrace();
			

		}
		return result;
	}

	public boolean isEmailInUse(String email) throws DBException, ConnectionException {
		return userDao.isEmailInUse(email);	
	}

}
