package ua.khpi.test.finalTask.dao;

import ua.khpi.test.finalTask.dao.mysql.MysqlDAOFactory;

public abstract class AbstractDAOFactory {

	public enum FactoryTypes {
		MYSQL
	}

	public abstract UserDAO getUserDAO();

	public abstract AccountDAO getAccountDAO();

	public abstract PaymentsDAO getPaymentsDAO();

	public abstract RequestDAO getRequestDAO();


	public abstract CardDAO getCardDAO();

	public static AbstractDAOFactory getDAOFactory(FactoryTypes type) {
		switch (type) {
		case MYSQL:
			return MysqlDAOFactory.getInstance();
		default:
			return null;
		}
	}

}
