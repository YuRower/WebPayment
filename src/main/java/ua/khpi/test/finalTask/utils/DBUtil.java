package ua.khpi.test.finalTask.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class DBUtil {
	private static final Logger LOG = LogManager.getLogger(DBUtil.class);

	private static SessionFactory factory;

	static {
		try {
			factory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
		} catch (HibernateException e) {
			LOG.error(e);
		}
	}

	public static SessionFactory getSessionFactory() {
		return factory;
	}

}