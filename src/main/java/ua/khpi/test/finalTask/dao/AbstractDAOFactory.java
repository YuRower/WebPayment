package ua.khpi.test.finalTask.dao;

import ua.khpi.test.finalTask.dao.mysql.MysqlDAOFactory;
import ua.khpi.test.finalTask.exception.DBException;

public abstract class AbstractDAOFactory {
	
	public enum FactoryTypes{
		MYSQL
	}

	public abstract UserDAO getUserDAO();
	public abstract AccountDAO getAccountDAO();
	public abstract PaymentsDAO getPaymentsDAO();
	public abstract RequestDAO getRequestDAO();
	public abstract UserAccountsCountDAO getUserAccountsCountDAO();

	public static AbstractDAOFactory getDAOFactory(FactoryTypes type)  {

		switch (type) {
		case MYSQL:
			try {
				return MysqlDAOFactory.getInstance();
			} catch (DBException e) {
				e.printStackTrace();
			}
		default:
			return null;
		}
	}

}
