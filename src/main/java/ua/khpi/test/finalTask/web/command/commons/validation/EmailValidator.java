package ua.khpi.test.finalTask.web.command.commons.validation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ua.khpi.test.finalTask.exception.ApplicationException;
import ua.khpi.test.finalTask.exception.ConnectionException;
import ua.khpi.test.finalTask.exception.DBException;
import ua.khpi.test.finalTask.logic.CommonLogic;
import ua.khpi.test.finalTask.utils.RegularExpressions;

public class EmailValidator extends Middleware {
	private static final Logger LOG = LogManager.getLogger(EmailValidator.class);
	private String email;

	public EmailValidator(String email) {
		this.email = email;
	}

	@Override
	public boolean check() throws ApplicationException {

		LOG.trace("Request parameter: email --> " + email);

		if (isEmailInUse(email)) {
			throw new ApplicationException("User with such email already exists");
		}
		if (email == null || email.trim().isEmpty() || !email.matches(RegularExpressions.EMAIL)) {
			throw new ApplicationException("Invalid email");
		}
		return checkNext();

	}

	private boolean isEmailInUse(String param) throws DBException, ConnectionException {
		CommonLogic cl = new CommonLogic();
		return cl.isEmailInUse(param);
	}
}
