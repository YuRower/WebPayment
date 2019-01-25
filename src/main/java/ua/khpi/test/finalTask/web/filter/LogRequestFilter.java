package ua.khpi.test.finalTask.web.filter;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class LogRequestFilter implements Filter {

	private FilterConfig filterConfig = null;

	@Override
	public void init(FilterConfig filterConfig) {
		this.filterConfig = filterConfig;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		Enumeration<String> headerNames = httpRequest.getHeaderNames();
		while (headerNames.hasMoreElements()) {
			String paramName = (String) headerNames.nextElement();
			log.info(paramName + " - " +httpRequest.getHeader(paramName));
		}
		chain.doFilter(request, response);

		HttpServletResponse httpResponse = (HttpServletResponse) response;

		String logString = filterConfig.getFilterName() + " | ";
		logString += "Servlet path: " + httpRequest.getServletPath() + " | ";
		logString += "Content type: " + httpResponse.getContentType();
		log.info(logString);

	}

	@Override
	public void destroy() {
		filterConfig = null;
	}
}