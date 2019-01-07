package ua.khpi.test.finalTask.utils;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class DBUtil {
	private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("PaymentCard");

	public static EntityManagerFactory getEmFactory() {
		return emf;
	}
}