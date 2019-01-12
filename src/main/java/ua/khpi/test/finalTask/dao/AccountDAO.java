package ua.khpi.test.finalTask.dao;

import java.util.List;
import ua.khpi.test.finalTask.entity.Account;
import ua.khpi.test.finalTask.exception.ConnectionException;
import ua.khpi.test.finalTask.exception.DBException;

public interface AccountDAO extends CrudDAO<Account>{

	List<Account> getAccountsByUserId(int userId) throws DBException, ConnectionException;

	List<Account> getAllAccounts() throws DBException, ConnectionException;

	List<Account> getAccountsByCardID(int id) throws ConnectionException, DBException;
}
