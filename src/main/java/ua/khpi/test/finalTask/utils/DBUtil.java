package ua.khpi.test.finalTask.utils;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


	public class DBUtil {

	    private static SessionFactory factory;

	    static {
	        try {
	             factory = new Configuration()
	                    .configure("hibernate.cfg.xml")
	                    .buildSessionFactory();
	        } catch (HibernateException e) {
	            e.printStackTrace();
	        }
	    }

	    public static SessionFactory getSessionFactory() {
	        return factory;
	    }

}