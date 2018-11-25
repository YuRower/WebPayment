package ua.khpi.test.finalTask.web.command.commons.validation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ua.khpi.test.finalTask.exception.ApplicationException;

public class Handler {
	private Middleware middleware;
	private static final Logger LOG = LogManager.getLogger(Middleware.class);

	public void setMiddleware(Middleware middleware) {
		this.middleware = middleware;
	}

	public boolean validate() throws ApplicationException {
		if (middleware.check()) {
			LOG.info("Validation have been successful!");

			return true;
		}
		return false;
	}

}
