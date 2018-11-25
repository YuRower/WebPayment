package ua.khpi.test.finalTask.web.command.commons.validation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ua.khpi.test.finalTask.exception.ApplicationException;
import ua.khpi.test.finalTask.utils.RegularExpressions;

public class NameValidator extends Middleware {
	private static final Logger LOG = LogManager.getLogger(NameValidator.class);
	private String name;

	public NameValidator(String name) {
		this.name = name;
	}

	@Override
	public boolean check() throws ApplicationException {
		LOG.trace("Request parameter: name --> " + name);
		if (name == null || name.trim().isEmpty()  || !name.matches(RegularExpressions.NAME)) {
			throw new ApplicationException("Invalid name");
		} else {
			return checkNext();
		}

	}

}
