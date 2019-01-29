package ua.khpi.test.finalTask.web.listeners;

import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class SessionListener implements HttpSessionAttributeListener, HttpSessionListener {

	private static final Logger log = LogManager.getLogger(SessionListener.class);

    
    public SessionListener() {
    }

	
    public void attributeRemoved(HttpSessionBindingEvent event)  {
    	log.debug("Attribute removed. Name --> " + event.getName() + ". Value --> " + event.getValue());
    }

	
    public void attributeAdded(HttpSessionBindingEvent event)  { 
    	log.debug("Attribute added. Name --> " + event.getName() + ". Value --> " + event.getValue());
    }


    public void sessionCreated(HttpSessionEvent event)  { 
    	log.debug("Session created.");
    }

	
    public void attributeReplaced(HttpSessionBindingEvent event)  { 
    	log.debug("Attribute replaced. Name --> " + event.getName() + ". Value --> " + event.getValue());
    }

	
    public void sessionDestroyed(HttpSessionEvent event)  { 
    	log.debug("Session Destroyed.");
    }
}
