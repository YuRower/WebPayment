package ua.khpi.test.finalTask.web.command.commons.validation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ua.khpi.test.finalTask.dao.AbstractDAOFactory;
import ua.khpi.test.finalTask.dao.UserDAO;
import ua.khpi.test.finalTask.dao.AbstractDAOFactory.FactoryTypes;
import ua.khpi.test.finalTask.exception.ApplicationException;
import ua.khpi.test.finalTask.utils.RegularExpressions;

public class LoginValidator extends Middleware {
	private static final Logger LOG = LogManager.getLogger(LoginValidator.class);
	private String login;

	public LoginValidator(String login) {
		this.login = login;
	}

	@Override
	public boolean check() throws ApplicationException {
		AbstractDAOFactory mysqlFactory = AbstractDAOFactory.getDAOFactory(FactoryTypes.MYSQL);
		UserDAO userDao = mysqlFactory.getUserDAO();
		LOG.trace("Request parameter: login --> " + login);
		
		if (userDao.isEmailInUse(login)) {
			throw new ApplicationException("User with such login already exists");
		}
		if (login == null || login.trim().isEmpty() || !login.matches(RegularExpressions.USERNAME)) {
			throw new ApplicationException("Invalid login");
		} else {
			return checkNext();
		}

	}

}