package ua.khpi.test.finalTask.connection.util;

import java.util.ResourceBundle;

public final class DBResourceManager {
	public static final String URL = "url";
	public static final String USER = "user";
	public static final String PASSWORD = "password";
	public static final String POOL_INITIAL_SIZE = "pool.initial.size";
	public static final String POOL_CAPACITY = "pool.poolSize";
	public static final String POOL_TIMEOUT = "pool.coonectiontimeout";
	public static final String CHARACTER_ENCODING = "characterEncoding";
	public static final String USE_ENCODING = "useUnicode";

	private static final ResourceBundle resourceBundle = ResourceBundle.getBundle("ua/khpi/test/finalTask/configuration/database");

	public static String getProperty(String key) {
		return resourceBundle.getString(key);
	}
}