package ua.khpi.test.finalTask.web.command.commons.validation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ua.khpi.test.finalTask.exception.ApplicationException;
import ua.khpi.test.finalTask.utils.RegularExpressions;

public class PasswordValidator extends Middleware {
	private static final Logger LOG = LogManager.getLogger(PasswordValidator.class);
	private String password;

	public PasswordValidator(String password) {
		this.password = password;
	}

	@Override
	public boolean check() throws ApplicationException {
		LOG.trace("Request parameter: password --> " + password);

		if (password == null || password.trim().isEmpty()) { // || !password.matches(RegularExpressions.PASSWORD)) {
			throw new ApplicationException("Invalid password");
		} else {
			return checkNext();
		}
	}
}
