package ua.khpi.test.finalTask.web.listeners;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RequestListener implements ServletRequestListener {
	private static final Logger log = LogManager.getLogger(RequestListener.class);

	@Override
	public void requestDestroyed(ServletRequestEvent arg0) {
		log.info("request initialized, request by: " + arg0.getServletRequest().getRemoteAddr());
		log.info("request initialized, servlet context name: " + arg0.getServletContext().getServletContextName());
		log.info("request initialized, info about server: " + arg0.getServletContext().getServerInfo());
		HttpServletRequest req = (HttpServletRequest) arg0.getServletRequest();
		String uri = "request Initialized for " + req.getRequestURI();
		String id = "request Initialized with ID=" + req.getRequestedSessionId();
		log.info(uri + "\n" + id);
		

	}

	@Override
	public void requestInitialized(ServletRequestEvent arg0) {
		log.info("Request destroyed");

	}

}