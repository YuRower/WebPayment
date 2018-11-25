package ua.khpi.test.finalTask.dao;

import java.util.List;

import ua.khpi.test.finalTask.entity.Request;
import ua.khpi.test.finalTask.exception.ConnectionException;
import ua.khpi.test.finalTask.exception.DBException;


public interface RequestDAO {
	boolean addRequest (Request request) throws DBException, ConnectionException;
	boolean removeRequest (Request request) throws DBException, ConnectionException;
	List<Request> getAll() throws DBException, ConnectionException;
}
