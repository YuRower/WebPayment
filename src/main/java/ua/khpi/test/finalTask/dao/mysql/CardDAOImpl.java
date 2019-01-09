package ua.khpi.test.finalTask.dao.mysql;

import java.util.ArrayList;
import java.util.List;

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
		return null;
		/*
		 * EntityManager em = DBUtil.getEmFactory().createEntityManager(); String
		 * qString = "SELECT u FROM cards u " + "WHERE u.card_id = :card_id";
		 * TypedQuery<Card> q = em.createQuery(qString, Card.class);
		 * q.setParameter("card_id", id); try { Card card = q.getSingleResult(); return
		 * card; } catch (NoResultException e) { LOGGER.error("Cannot get card by id ",
		 * e); return null; } finally { em.close(); }
		 */
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

	
	/*public ArrayList<Card> getAllUserCards() {
		sessionFactory = DBUtil.getSessionFactory();

		Session session = sessionFactory.openSession();
		ArrayList<Card> cards = new ArrayList<>(session.createQuery("SELECT a FROM cards a", Card.class).getResultList());
		LOGGER.info("\nSuccessfully received" + cards );
		return cards;
	}*/
	@SuppressWarnings("deprecation")
	@Override
	public List<Card> getAllUserCards() {
		List<Card> cards = new ArrayList<>();	
		sessionFactory = DBUtil.getSessionFactory();
		Session session = sessionFactory.openSession();	

		try {
			
			session.beginTransaction();

			cards = session.createQuery("FROM cards").list();
		} catch(Exception sqlException) {
			if(null != session.getTransaction()) {
				LOGGER.info("\n.......Transaction Is Being Rolled Back.......\n");
				session.getTransaction().rollback();
			}
			sqlException.printStackTrace();
		} finally {
			if(session != null) {
				session.close();
			}
		}
		return cards;
	}
}
