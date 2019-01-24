package ua.khpi.test.finalTask.dao.mysql;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.jboss.logging.Logger;
import ua.khpi.test.finalTask.dao.CardDAO;
import ua.khpi.test.finalTask.entity.Account;
import ua.khpi.test.finalTask.entity.Card;
import ua.khpi.test.finalTask.exception.ConnectionException;
import ua.khpi.test.finalTask.exception.DBException;

public class CardDAOImpl implements CardDAO {
	private static Logger LOGGER = Logger.getLogger(CardDAOImpl.class);
	private static final String HQL_FIND_ALL_CARDS_BY_ID = "SELECT u FROM Card u WHERE id = :id";
	private static final String HQL_FIND_ALL_CARDS_BY_USER_ID = "from Card where user_id = :id";
	private static final String HQL_FIND_ALL_ACCOUNTS_BY_CARD_ID = "from Account  where cards_id = :id";
	private static final SessionFactory sessionFactory;

	static {
		try {
			sessionFactory = new Configuration().configure().buildSessionFactory();
		} catch (Throwable e) {
			LOGGER.error("Initial SessionFactory creation failed." + e);
			throw new ExceptionInInitializerError(e);
		}
	}

	@Override
	public boolean update(Card entity) throws DBException, ConnectionException {
		Session session = sessionFactory.openSession();
		boolean flag = true;
		try {
			session.beginTransaction();
			session.update(entity);
			session.getTransaction().commit();
			LOGGER.info("\nSuccessfully Updated ");
		} catch (Exception sqlException) {
			flag = false;
			if (null != session.getTransaction()) {
				LOGGER.info("\n.......Transaction Is Being Rolled Back while updating.......\n", sqlException);
				session.getTransaction().rollback();
			}
		} finally {
			close(session);

		}
		return flag;
	}

	@Override
	public boolean addEntity(Card entity) throws DBException, ConnectionException {
		boolean flag = true;
		Session session = sessionFactory.openSession();
		try {
			session.beginTransaction();
			session.save(entity);
			session.getTransaction().commit();
			LOGGER.info("\nSuccessfully Created ");
		} catch (Exception sqlException) {
			flag = false;
			if (null != session.getTransaction()) {
				LOGGER.info("\n.......Transaction Is Being Rolled Back while addition.......\n", sqlException);
				session.getTransaction().rollback();
			}
		} finally {
			close(session);

		}
		return flag;
	}

	@Override
	public boolean removeEntity(Card entity) throws DBException, ConnectionException {
		boolean flag = true;
		Session session = sessionFactory.openSession();
		try {
			session.beginTransaction();
			Card card = session.find(Card.class, entity.getId());
			session.remove(card);
			session.getTransaction().commit();
			LOGGER.info("\nSuccessfully Deleted ");
		} catch (Exception sqlException) {
			flag = false;
			if (null != session.getTransaction()) {
				LOGGER.info("\n.......Transaction Is Being Rolled Back while deleting.......\n", sqlException);
				session.getTransaction().rollback();
			}
		} finally {
			close(session);

		}
		return flag;
	}

	@Override
	public List<Card> getAllUserCards() {
		Session session = sessionFactory.openSession();
		try {
			CriteriaBuilder cb = session.getCriteriaBuilder();
			CriteriaQuery<Card> cq = cb.createQuery(Card.class);
			Root<Card> rootEntry = cq.from(Card.class);
			CriteriaQuery<Card> all = cq.select(rootEntry);
			TypedQuery<Card> allQuery = session.createQuery(all);
			return allQuery.getResultList();
		} finally {
			close(session);

		}
	}

	@Override
	public List<Account> getAccountsByCardId(int cardId) throws ConnectionException, DBException {
		Session session = sessionFactory.openSession();
		TypedQuery<Account> q = session.createQuery(HQL_FIND_ALL_ACCOUNTS_BY_CARD_ID,Account.class);
		q.setParameter("id", cardId);
		try {
		List<Account> list = q.getResultList();
		return list;
		} catch (NoResultException e) {
			LOGGER.error("Cannot get list of accounts by card id ", e);
			return null;
		} finally {
			close(session);
		}
	}

	@Override
	public List<Card> getAllCardsByUserId(int id) {
		Session session = sessionFactory.openSession();
		TypedQuery<Card> q = session.createQuery(HQL_FIND_ALL_CARDS_BY_USER_ID, Card.class);
		q.setParameter("id", id);

		try {
			List<Card> list = q.getResultList();
			return list;

		} catch (NoResultException e) {
			LOGGER.error("Cannot get list of card by user id ", e);
			return null;
		} finally {
			close(session);
		}
	}


	@Override
	public Card getEntityById(int id) throws DBException, ConnectionException {
		Session session = sessionFactory.openSession();
		TypedQuery<Card> q = session.createQuery(HQL_FIND_ALL_CARDS_BY_ID, Card.class);
		q.setParameter("id", id);
		try {
			Card card = q.getSingleResult();
			return card;
		} catch (NoResultException e) {
			LOGGER.error("Cannot get card by id ", e);
			return null;

		} finally {
			close(session);

		}

	}
	private void close(Session session) {
		if (session != null) {
			session.close();
		}		
	}
}