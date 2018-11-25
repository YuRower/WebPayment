package ua.khpi.test.finalTask.web.command.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ua.khpi.test.finalTask.exception.ApplicationException;
import ua.khpi.test.finalTask.web.Path;
import ua.khpi.test.finalTask.web.RequestProcessorInfo;
import ua.khpi.test.finalTask.web.RequestProcessorInfo.ProcessorMode;
import ua.khpi.test.finalTask.web.command.Command;


public class ChangePaymentsOrder extends Command {
	
	private static final Logger LOG = LogManager.getLogger(ChangePaymentsOrder.class);

	@Override
	public RequestProcessorInfo execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, ApplicationException {
		LOG.debug("Command starts");

		HttpSession session = request.getSession();
		
		String sortBy = request.getParameter("sortby");
		LOG.trace("Will be sorted by --> "+sortBy);
		
		session.setAttribute("paymentsOrder", sortBy);
		
		LOG.debug("Command finished");
		return new RequestProcessorInfo(ProcessorMode.FORWARD, Path.COMMAND_LIST_TRANSACTIONS);
	}
}
