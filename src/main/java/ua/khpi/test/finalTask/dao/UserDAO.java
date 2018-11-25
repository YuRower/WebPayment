package ua.khpi.test.finalTask.dao;

import java.util.List;

import ua.khpi.test.finalTask.entity.User;
import ua.khpi.test.finalTask.exception.ConnectionException;
import ua.khpi.test.finalTask.exception.DBException;


public interface UserDAO extends CrudDAO<User> {
	User findUserByEmail(String email) throws DBException, ConnectionException;
	boolean isEmailInUse(String email) throws DBException, ConnectionException;
	boolean newUserWithDefaultValues(User user) throws DBException, ConnectionException;
	List<User> getAllAdmins() throws DBException, ConnectionException;
	List<User> getAllUsers() throws DBException, ConnectionException;
}
