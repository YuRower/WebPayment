package ua.khpi.test.finalTask.dao;

import java.util.List;

import ua.khpi.test.finalTask.entity.Card;

public interface CardDAO extends CrudDAO<Card> {

	List<Card> getAllUserCards();

}
