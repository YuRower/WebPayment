package ua.khpi.test.finalTask.logic;

import java.math.BigDecimal;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ua.khpi.test.finalTask.entity.Account;
import ua.khpi.test.finalTask.entity.Card;
import ua.khpi.test.finalTask.entity.Payment;
import ua.khpi.test.finalTask.entity.Request;
import ua.khpi.test.finalTask.exception.ConnectionException;
import ua.khpi.test.finalTask.exception.DBException;

public class UserLogic extends ApplicationLogic {
	private static final Logger LOG = LogManager.getLogger(UserLogic.class);

	public Account getEntityById(int accountId) throws DBException, ConnectionException {
		try {
			return accountDao.getEntityById(accountId);
		} catch (ConnectionException e) {
			LOG.error(e);
			throw e;
		}

	}

	public Card getCardbyId(int cardId) throws DBException, ConnectionException {
		try {
			return cardDao.getEntityById(cardId);
		} catch (ConnectionException e) {
			LOG.error(e);
			throw e;
		}

	}

	public void update(Account accountToLock) throws DBException {
		try {
			accountDao.update(accountToLock);
		} catch (ConnectionException e) {
			LOG.error(e);
		}
	}

	public void insertReplenish(BigDecimal moneyAmount, int accountIdTo) throws DBException {
		try {
			paymentDao.insertReplenish(moneyAmount, accountIdTo);
		} catch (ConnectionException e) {
			LOG.error(e);

		}

	}

	public void insertRemittance(BigDecimal moneyAmount, int accountIdFrom, int accountIdTo) throws DBException {
		try {
			paymentDao.insertRemittance(moneyAmount, accountIdFrom, accountIdTo);

		} catch (ConnectionException e) {
			LOG.error(e);

		}
	}

	public void insertCard(Card card) throws DBException, ConnectionException {
		cardDao.addEntity(card);
	}

	public List<Payment> getPaymentsByAccount(int accountId) throws DBException, ConnectionException {
		try {
			return paymentDao.getPaymentsByAccount(accountId);
		} catch (ConnectionException e) {
			LOG.error(e);
			throw e;
		}
	}

	public void addRequest(Request requestForUnlock) throws DBException {
		try {
			requestDao.addRequest(requestForUnlock);
		} catch (ConnectionException e) {
			LOG.error(e);
		}
	}

	public void addEntity(Account newAccount) throws DBException {
		try {
			accountDao.addEntity(newAccount);
		} catch (ConnectionException e) {
			LOG.error(e);

		}
	}

	public List<Account> getAccountsByCardId(int cardId) throws DBException {
		List<Account> accounts = null;
		try {
			accounts = cardDao.getAccountsByCardId(cardId);
		} catch (ConnectionException e) {
			LOG.error(e);
		}
		return accounts;
	}

	public List<Card> getAllCardsByUserId(int id) {
		List<Card> cards = cardDao.getAllCardsByUserId(id);
		return cards;

	}

	public List<Card> getAllUserCards() {
		List<Card> cards = cardDao.getAllUserCards();
		return cards;

	}

	public boolean deleteCard(Card matchingCard) throws DBException, ConnectionException {
		return cardDao.removeEntity(matchingCard);
	}

}
