package ua.khpi.test.finalTask.web.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ua.khpi.test.finalTask.entity.enums.UserType;
import ua.khpi.test.finalTask.web.Path;


public class SecurityFilter implements Filter {

	private static final Logger LOG = LogManager.getLogger(SecurityFilter.class);

	// commands access	
	private Map<UserType, List<String>> accessMap = new HashMap<>();
	private List<String> commons = new ArrayList<String>();	
	private List<String> outOfControl = new ArrayList<String>();
	
	public void destroy() {
		LOG.debug("Filter destructed");
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		LOG.debug("Filter starts");
		
		if (accessAllowed(request)) {
			LOG.debug("Filter finished");
			chain.doFilter(request, response);
		} else {
			String errorMessasge = "You do not have permission to access the requested resource";
			
			request.setAttribute("errorMessage", errorMessasge);
			LOG.trace("Set the request attribute: errorMessage --> " + errorMessasge);
			
			request.getRequestDispatcher(Path.PAGE_ERROR_PAGE)
					.forward(request, response);
		}
	}
	
	private boolean accessAllowed(ServletRequest request) {
			HttpServletRequest httpRequest = (HttpServletRequest) request;

		String commandName = request.getParameter("command");
		if (commandName == null || commandName.isEmpty()) {
			LOG.debug("Command is empty");
			return false;
		}
		
		if (outOfControl.contains(commandName)) {
			LOG.debug("Command not found");
			return true;
		}
		
		HttpSession session = httpRequest.getSession(false);
		if (session == null) { 
			LOG.debug("The session is destroyed or has not been activated");
			return false;
		}
		
		UserType userType = (UserType)session.getAttribute("userType");
		if (userType == null) {
			LOG.debug("User is not defined");
			return false;
		}
		
		return accessMap.get(userType).contains(commandName)
				|| commons.contains(commandName);
	}

	public void init(FilterConfig fConfig) throws ServletException {
		LOG.debug("Filter initialization starts");
		
		// roles
		accessMap.put(UserType.ADMIN, asList(fConfig.getInitParameter("admin")));
		accessMap.put(UserType.USER, asList(fConfig.getInitParameter("user")));
		accessMap.put(UserType.SUPERUSER, asList(fConfig.getInitParameter("superuser")));
		LOG.trace("Access map --> " + accessMap);

		// commons
		commons = asList(fConfig.getInitParameter("common"));
		LOG.trace("Common commands --> " + commons);

		// out of control
		outOfControl = asList(fConfig.getInitParameter("out-of-control"));
		LOG.trace("Out of control commands --> " + outOfControl);
		
		LOG.debug("Filter initialization finished");
	}
	
	/**
	 * Extracts parameter values from string.
	 * 
	 * @param str
	 *            parameter values string.
	 * @return list of parameter values.
	 */
	private List<String> asList(String str) {
		List<String> list = new ArrayList<String>();
		StringTokenizer st = new StringTokenizer(str);
		while (st.hasMoreTokens()) {
			list.add(st.nextToken());
		}
		return list;		
	}
}
