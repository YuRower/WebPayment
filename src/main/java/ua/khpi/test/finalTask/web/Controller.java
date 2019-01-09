package ua.khpi.test.finalTask.web;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ua.khpi.test.finalTask.connection.ConnectionPool;
import ua.khpi.test.finalTask.exception.ApplicationException;
import ua.khpi.test.finalTask.web.RequestProcessorInfo.ProcessorMode;
import ua.khpi.test.finalTask.web.command.Command;
import ua.khpi.test.finalTask.web.command.CommandContainer;

public class Controller extends HttpServlet {

	private static final Logger LOG = LogManager.getLogger(Controller.class);

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		process(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		process(request, response);
	}

	private void process(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		LOG.debug("Controller starts");
		/*HttpSession session = request.getSession();
        Enumeration sessionAtr = session.getAttributeNames();
        while (sessionAtr.hasMoreElements()) {
    		LOG.trace("Request parameter: command --> " +(String) sessionAtr.nextElement());

        }
        LOG.trace("iddddddddddddd"+ request.getSession().getId());*/
		String commandName = request.getParameter("command");
		LOG.trace("Request parameter: command --> " + commandName);

		Command command = CommandContainer.get(commandName);
		LOG.trace("Obtained command --> " + command);

		RequestProcessorInfo requestProcessorInfo = new RequestProcessorInfo(ProcessorMode.FORWARD,
				Path.PAGE_ERROR_PAGE);
		try {
			requestProcessorInfo = command.execute(request, response);
		} catch (ApplicationException ex) {
			request.setAttribute("errorMessage", ex.getMessage());
		}
		LOG.trace("Forward address --> " + requestProcessorInfo.getPath());
		LOG.debug("Controller finished, now go to forward address --> " + requestProcessorInfo.getPath());

		switch (requestProcessorInfo.getProcessorMode()) {
		case FORWARD:
			request.getRequestDispatcher(requestProcessorInfo.getPath()).forward(request, response);
			break;
		case REDIRECT:
			response.sendRedirect(requestProcessorInfo.getPath());
			break;
		}
	}
}
