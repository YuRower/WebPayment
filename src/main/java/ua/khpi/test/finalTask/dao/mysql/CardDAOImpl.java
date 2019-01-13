package ua.khpi.test.finalTask.dao.mysql;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.jboss.logging.Logger;
import ua.khpi.test.finalTask.dao.CardDAO;
import ua.khpi.test.finalTask.entity.Card;
import ua.khpi.test.finalTask.exception.ConnectionException;
import ua.khpi.test.finalTask.exception.DBException;
import ua.khpi.test.finalTask.utils.DBUtil;

public class CardDAOImpl implements CardDAO {
	private static Logger LOGGER = Logger.getLogger(CardDAOImpl.class);
	MysqlDAOFactory factory;

	public CardDAOImpl(MysqlDAOFactory factory) {
		this.factory = factory;
	}

	private SessionFactory sessionFactory;

	@Override
	public Card getEntityById(int id) throws DBException, ConnectionException {

		sessionFactory = DBUtil.getSessionFactory();
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
		}

	}

	@Override
	public boolean update(Card entity) throws DBException, ConnectionException {
		return false;
	}

	/*
	 * boolean flag = true; EntityManager em =
	 * DBUtil.getEmFactory().createEntityManager(); EntityTransaction trans =
	 * em.getTransaction(); trans.begin(); try { em.merge(entity); trans.commit(); }
	 * catch (Exception e) { LOGGER.error("Cannot update card ", e);
	 * trans.rollback(); flag=false; } finally { em.close(); } return flag;
	 */

	@Override
	public boolean addEntity(Card entity) throws DBException, ConnectionException {
		boolean flag = true;
		sessionFactory = DBUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		try {
			session.beginTransaction();
			session.save(entity);
			session.getTransaction().commit();
			LOGGER.info("\nSuccessfully Created ");
		} catch (Exception sqlException) {
			flag = false;
			if (null != session.getTransaction()) {
				LOGGER.info("\n.......Transaction Is Being Rolled Back.......\n");
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
		return false;
		/*
		 * boolean flag = true; EntityManager em =
		 * DBUtil.getEmFactory().createEntityManager(); EntityTransaction trans =
		 * em.getTransaction(); trans.begin(); try { em.remove(em.merge(entity));
		 * trans.commit(); } catch (Exception e) { LOGGER.error("Cannot remove card ",
		 * e); trans.rollback(); flag=false; } finally { em.close(); } return flag; }
		 */
	}

	/*
	 * public ArrayList<Card> getAllUserCards() { sessionFactory =
	 * DBUtil.getSessionFactory();
	 * 
	 * Session session = sessionFactory.openSession(); ArrayList<Card> cards = new
	 * ArrayList<>(session.createQuery("SELECT a FROM cards a",
	 * Card.class).getResultList()); LOGGER.info("\nSuccessfully received" + cards
	 * ); return cards; }
	 */

	@Override
	public List<Card> getAllUserCards() {
		sessionFactory = DBUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<Card> cq = cb.createQuery(Card.class);
		Root<Card> rootEntry = cq.from(Card.class);
		CriteriaQuery<Card> all = cq.select(rootEntry);

		TypedQuery<Card> allQuery = session.createQuery(all);
		return allQuery.getResultList();
	}
}
