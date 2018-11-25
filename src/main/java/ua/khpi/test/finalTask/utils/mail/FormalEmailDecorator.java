package ua.khpi.test.finalTask.utils.mail;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FormalEmailDecorator extends EmailDecorator {
	private static final Logger LOG = LogManager.getLogger(FormalEmailDecorator.class);

	public FormalEmailDecorator(IEmail email) {
		super(email);
		LOG.info("formal");
	}

	@Override
	public String getContents() {
		return "Dear Sir/Madam,\n" + super.getContents() + "\nYours Sincerely,\nMr...";
	}
}
