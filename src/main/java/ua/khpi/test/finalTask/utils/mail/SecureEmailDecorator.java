package ua.khpi.test.finalTask.utils.mail;

import java.util.Base64;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SecureEmailDecorator extends EmailDecorator {
	private static final Logger LOG = LogManager.getLogger(FormalEmailDecorator.class);

	public SecureEmailDecorator(IEmail email) {
		super(email);
		LOG.info("secure");
	}

	@Override
	public String getContents() {
		return encode(super.getContents());
	}

	private String encode(String data) {
		byte[] result = data.getBytes();
		for (int i = 0; i < result.length; i++) {
			result[i] += (byte) 1;
		}
		return Base64.getEncoder().encodeToString(result);
	}

	private String decode(String data) {
		byte[] result = Base64.getDecoder().decode(data);
		for (int i = 0; i < result.length; i++) {
			result[i] -= (byte) 1;
		}
		return new String(result);
	}

}