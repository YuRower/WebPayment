package ua.khpi.test.finalTask.utils;

import org.springframework.security.crypto.bcrypt.BCrypt;


public class PasswordHasher {
	
	private PasswordHasher () {}

	public static String getHash(String plainPassword) {
		return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
	}

	public static boolean checkPassword(String candidatePassword, String hash) {
		return BCrypt.checkpw(candidatePassword, hash);
	}
	
}
