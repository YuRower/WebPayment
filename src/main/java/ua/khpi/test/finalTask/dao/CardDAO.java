package ua.khpi.test.finalTask.dao;

import java.util.List;

import ua.khpi.test.finalTask.entity.Account;
import ua.khpi.test.finalTask.entity.Card;
import ua.khpi.test.finalTask.exception.ConnectionException;
import ua.khpi.test.finalTask.exception.DBException;

public interface CardDAO extends CrudDAO<Card> {
	List<Card> getAllUserCards();
	List<Account> getAccountsByCardId(int cardId) throws ConnectionException, DBException;
	List<Card> getAllCardsByUserId(int id);
}
