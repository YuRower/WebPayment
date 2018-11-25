package ua.khpi.test.finalTask.dao;

import java.util.List;

import ua.khpi.test.finalTask.entity.bean.UserAccountsCount;
import ua.khpi.test.finalTask.exception.ConnectionException;
import ua.khpi.test.finalTask.exception.DBException;

public interface UserAccountsCountDAO {
	List<UserAccountsCount> getAccountsWithBalanceGreaterThan(int value) throws DBException, ConnectionException;
}
