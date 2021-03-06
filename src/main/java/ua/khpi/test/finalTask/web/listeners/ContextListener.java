package ua.khpi.test.finalTask.web.listeners;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ua.khpi.test.finalTask.dao.AbstractDAOFactory;
import ua.khpi.test.finalTask.dao.AbstractDAOFactory.FactoryTypes;




public class ContextListener implements ServletContextListener {

	private static final Logger LOG = LogManager.getLogger(ContextListener.class);


	public void contextDestroyed(ServletContextEvent arg0) {
		LOG.trace("Servlet context destruction");
	}

	
	public void contextInitialized(ServletContextEvent arg0) {
		LOG.trace("Servlet context initialization started");
		initCommandContainer();
		initDataSource();
		LOG.trace("Servlet context initialization finished");
	}

	private void initDataSource() {
		AbstractDAOFactory.getDAOFactory(FactoryTypes.MYSQL);
	}

	private void initCommandContainer() {
		try {
			Class.forName("ua.khpi.test.finalTask.web.command.CommandContainer");
		} catch (ClassNotFoundException ex) {
			throw new IllegalStateException("Cannot initialize Command Container");
		}
	}
}
