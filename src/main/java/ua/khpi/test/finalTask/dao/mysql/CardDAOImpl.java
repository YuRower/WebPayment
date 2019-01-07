package ua.khpi.test.finalTask.dao.mysql;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
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

	@Override
	public Card getEntityById(int id) throws DBException, ConnectionException {
		  EntityManager em = DBUtil.getEmFactory().createEntityManager();
	        String qString = "SELECT u FROM cards u " +
	                "WHERE u.card_id = :card_id";
	        TypedQuery<Card> q = em.createQuery(qString, Card.class);
	        q.setParameter("card_id", id);
	        try {
	        	Card card = q.getSingleResult();
	            return card;
	        } catch (NoResultException e) {
				LOGGER.error("Cannot get card by id ", e);
	            return null;
	        } finally {
	            em.close();
	        }
	}

	@Override
	public boolean update(Card entity) throws DBException, ConnectionException {
		boolean flag = true;
		EntityManager em = DBUtil.getEmFactory().createEntityManager();
		EntityTransaction trans = em.getTransaction();
		trans.begin();
		try {
			em.merge(entity);
			trans.commit();
		} catch (Exception e) {
			LOGGER.error("Cannot update card ", e);
			trans.rollback();
			flag=false;
		} finally {
			em.close();
		}
		return flag;
	}

	@Override
	public boolean addEntity(Card entity) throws DBException, ConnectionException {
		boolean flag = true;
		EntityManager em = DBUtil.getEmFactory().createEntityManager();
		EntityTransaction trans = em.getTransaction();
		trans.begin();
		try {
			em.persist(entity);
			trans.commit();
		} catch (Exception e) {
			LOGGER.error("Cannot insert card ", e);
			trans.rollback();
			flag=false;
		} finally {
			em.close();
		}
		return flag;
	}

	@Override
	public boolean removeEntity(Card entity) throws DBException, ConnectionException {
		boolean flag = true;
		EntityManager em = DBUtil.getEmFactory().createEntityManager();
		EntityTransaction trans = em.getTransaction();
		trans.begin();
		try {
            em.remove(em.merge(entity));
			trans.commit();
		} catch (Exception e) {
			LOGGER.error("Cannot remove card ", e);
			trans.rollback();
			flag=false;
		} finally {
			em.close();
		}
		return flag;
	}

}
