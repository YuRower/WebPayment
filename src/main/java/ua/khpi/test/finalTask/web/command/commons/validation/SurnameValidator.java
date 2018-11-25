package ua.khpi.test.finalTask.web.command.commons.validation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ua.khpi.test.finalTask.exception.ApplicationException;
import ua.khpi.test.finalTask.utils.RegularExpressions;

public class SurnameValidator extends Middleware {
	private static final Logger LOG = LogManager.getLogger(SurnameValidator.class);
	private String surname;

	public SurnameValidator(String surname) {
		this.surname = surname;
	}

	@Override
	public boolean check() throws ApplicationException {
		LOG.trace("Request parameter: surname --> " + surname);
		if (surname == null  || surname.trim().isEmpty()
				|| !surname.matches(RegularExpressions.NAME)) {
			throw new ApplicationException("Invalid surname");
		} else {
			return checkNext();
		}
	}

}
