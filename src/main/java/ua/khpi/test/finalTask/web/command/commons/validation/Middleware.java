package ua.khpi.test.finalTask.web.command.commons.validation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ua.khpi.test.finalTask.exception.ApplicationException;

public abstract class Middleware {
	private static final Logger LOG = LogManager.getLogger(Middleware.class);

	private Middleware next;

	public Middleware linkWith(Middleware next) {
		this.next = next;
		return next;
	}

	public abstract boolean check() throws ApplicationException;

	protected boolean checkNext() throws ApplicationException {
		if (next == null) {
			LOG.info("next is null");
			return false;
		} else {
			return next.check();

		}
	}

}