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

	/*
	 * public static String encrypt(String key, String initVector, String value) {
	 * try { IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
	 * SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
	 * 
	 * Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
	 * cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
	 * 
	 * byte[] encrypted = cipher.doFinal(value.getBytes());
	 * System.out.println("encrypted string: " +
	 * Base64.encodeBase64String(encrypted));
	 * 
	 * return Base64.encodeBase64String(encrypted); } catch (Exception ex) {
	 * ex.printStackTrace(); }
	 * 
	 * return null; }
	 * 
	 * public static String decrypt(String key, String initVector, String encrypted)
	 * { try { IvParameterSpec iv = new
	 * IvParameterSpec(initVector.getBytes("UTF-8")); SecretKeySpec skeySpec = new
	 * SecretKeySpec(key.getBytes("UTF-8"), "AES");
	 * 
	 * Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
	 * cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
	 * 
	 * byte[] original = cipher.doFinal(Base64.decodeBase64(encrypted));
	 * 
	 * return new String(original); } catch (Exception ex) { ex.printStackTrace(); }
	 * 
	 * return null; }
	 */

}