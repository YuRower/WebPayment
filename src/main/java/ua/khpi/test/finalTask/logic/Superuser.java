package ua.khpi.test.finalTask.logic;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ua.khpi.test.finalTask.entity.User;
import ua.khpi.test.finalTask.exception.ConnectionException;
import ua.khpi.test.finalTask.exception.DBException;

public class Superuser extends ApplicationLogic {
	private static final Logger LOG = LogManager.getLogger(Superuser.class);

	public List<User> getAllAdmins() throws DBException, ConnectionException {
		try {
			return userDao.getAllAdmins();
		} catch (ConnectionException e) {
			LOG.error(e);
			throw e;
		}
	}

	public boolean addEntity(User user) throws DBException {
		boolean flag = false;
		try {
			flag = userDao.addEntity(user);
			LOG.debug("Addition operation status = " + flag);
			return flag;
		} catch (ConnectionException e) {
			LOG.error(e);
		}
		return flag;

	}

}
