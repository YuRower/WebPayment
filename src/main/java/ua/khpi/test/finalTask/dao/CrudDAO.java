package ua.khpi.test.finalTask.dao;

import ua.khpi.test.finalTask.entity.AbstractEntity;
import ua.khpi.test.finalTask.exception.ConnectionException;
import ua.khpi.test.finalTask.exception.DBException;


public interface CrudDAO<E extends AbstractEntity> {
	public E getEntityById(int id) throws DBException, ConnectionException;
	public boolean update(E entity) throws DBException, ConnectionException;
	public boolean addEntity(E entity) throws DBException, ConnectionException;
	public boolean removeEntity(E entity) throws DBException, ConnectionException;

}