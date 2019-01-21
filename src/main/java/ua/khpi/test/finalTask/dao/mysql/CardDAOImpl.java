package ua.khpi.test.finalTask.dao.mysql;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;
import org.jboss.logging.Logger;
import ua.khpi.test.finalTask.dao.CardDAO;
import ua.khpi.test.finalTask.entity.Account;
import ua.khpi.test.finalTask.entity.Card;
import ua.khpi.test.finalTask.exception.ConnectionException;
import ua.khpi.test.finalTask.exception.DBException;

public class CardDAOImpl implements CardDAO {
	private static Logger LOGGER = Logger.getLogger(CardDAOImpl.class);

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
	public Card getEntityById(int id) throws DBException, ConnectionException {
		Session session = sessionFactory.openSession();
		String qString = "SELECT u FROM Card u " + "WHERE id = :id";
		TypedQuery<Card> q = session.createQuery(qString, Card.class);
		q.setParameter("id", id);
		try {
			Card card = q.getSingleResult();
			return card;
		} catch (NoResultException e) {
			LOGGER.error("Cannot get card by id ", e);
			return null;

		} finally {
			if (session != null) {
				session.close();
			}
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
				LOGGER.info("\n.......Transaction Is Being Rolled Back.......\n",sqlException);
				session.getTransaction().rollback();
			}
			sqlException.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
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
				LOGGER.info("\n.......Transaction Is Being Rolled Back.......\n",sqlException);
				session.getTransaction().rollback();
			}
			sqlException.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return flag;
	}

	@Override
	public boolean removeEntity(Card entity) throws DBException, ConnectionException {
		boolean flag = true;
		Session session = sessionFactory.openSession();
		try {
			session.beginTransaction();
			session.delete(entity);
			session.getTransaction().commit();
			LOGGER.info("\nSuccessfully Deleted ");
		} catch (Exception sqlException) {
			flag = false;
			if (null != session.getTransaction()) {
				LOGGER.info("\n.......Transaction Is Being Rolled Back.......\n",sqlException);
				session.getTransaction().rollback();
			}
			sqlException.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
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
			LOGGER.info("--------------------------" + allQuery.getResultList());
			return allQuery.getResultList();

		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	@Override
	public List<Account> getAccountsByCardId(int cardId) throws ConnectionException, DBException {
		Session session = sessionFactory.openSession();
		Query q = session.createQuery("from Account  where cards_id = :id");
		q.setParameter("id", cardId);
		List<Account> list = q.list();

		/*
		 * List<Account> list = session.createCriteria(Account.class).createCriteria("")
		 * .add(Restrictions.eq("id", cardId)).list();
		 */
		return list;
	}

	@Override
	public List<Card> getAllCardsByUserId(int id) {
		Session session = sessionFactory.openSession();
		Query q = session.createQuery("from Card  where user_id = :id");
		q.setParameter("id", id);
		List<Card> list = q.list();
		return list;

	}
}