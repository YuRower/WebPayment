package ua.khpi.test.finalTask.dao;

import java.util.List;
import ua.khpi.test.finalTask.entity.Account;
import ua.khpi.test.finalTask.exception.ConnectionException;
import ua.khpi.test.finalTask.exception.DBException;

public interface AccountDAO extends CrudDAO<Account>{
	List<Account> getAllAccounts() throws DBException, ConnectionException;
}
