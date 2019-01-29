package ua.khpi.test.finalTask.logic;

import ua.khpi.test.finalTask.dao.AbstractDAOFactory;
import ua.khpi.test.finalTask.dao.PaymentsDAO;
import ua.khpi.test.finalTask.dao.RequestDAO;
import ua.khpi.test.finalTask.dao.UserDAO;
import ua.khpi.test.finalTask.dao.AbstractDAOFactory.FactoryTypes;
import ua.khpi.test.finalTask.dao.AccountDAO;
import ua.khpi.test.finalTask.dao.CardDAO;

public class ApplicationLogic {
	protected static AbstractDAOFactory mysqlFactory = AbstractDAOFactory.getDAOFactory(FactoryTypes.MYSQL);
    PaymentsDAO paymentDao = mysqlFactory.getPaymentsDAO();
    UserDAO userDao = mysqlFactory.getUserDAO();
    RequestDAO requestDao = mysqlFactory.getRequestDAO();
    AccountDAO accountDao = mysqlFactory.getAccountDAO();
    CardDAO cardDao= mysqlFactory.getCardDAO();

	
}
