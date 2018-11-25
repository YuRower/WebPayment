package ua.khpi.test.finalTask.logic;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ua.khpi.test.finalTask.connection.util.TransactionManager;
import ua.khpi.test.finalTask.entity.User;
import ua.khpi.test.finalTask.exception.ConnectionException;
import ua.khpi.test.finalTask.exception.DBException;

public class Superuser extends ApplicationLogic {
	private static final Logger LOG = LogManager.getLogger(Superuser.class);

	public List<User> getAllAdmins() throws DBException, ConnectionException {
		try {
			return userDao.getAllAdmins();
		} catch (ConnectionException e) {
			e.printStackTrace();
			throw e;
		}
	}

	public boolean addEntity(User user) throws DBException {
		boolean flag = false;
		try {
			TransactionManager.beginTransaction();
			flag = userDao.addEntity(user);
			LOG.debug("Addition operation status = " + flag);
			TransactionManager.commitTransaction();
			return flag;
		} catch (ConnectionException e) {
			e.printStackTrace();
			TransactionManager.rollbackTransaction();
		}
		return flag;

	}

}
