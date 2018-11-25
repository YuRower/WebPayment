package ua.khpi.test.finalTask.web.command.user;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ua.khpi.test.finalTask.entity.Payment;
import ua.khpi.test.finalTask.exception.ApplicationException;
import ua.khpi.test.finalTask.web.Path;
import ua.khpi.test.finalTask.web.RequestProcessorInfo;
import ua.khpi.test.finalTask.web.RequestProcessorInfo.ProcessorMode;
import ua.khpi.test.finalTask.web.command.Command;


public class ClearCartCommand extends Command {
	
	private static final Logger LOG = LogManager.getLogger(ClearCartCommand.class);

	@Override
	public RequestProcessorInfo execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, ApplicationException {
		LOG.debug("Command starts");

		HttpSession session = request.getSession();
		LOG.trace("Obtaining cart content");
		
		@SuppressWarnings("unchecked")
		List<Payment> preparedPayments = (List<Payment>) session.getAttribute("prepPayments");
		LOG.trace("Cart content --> " + preparedPayments);
		
		LOG.trace("Clearing cart content");
		preparedPayments = new ArrayList<>();
		
		session.setAttribute("prepPayments", preparedPayments);
		LOG.debug("Command finished");
		return new RequestProcessorInfo(ProcessorMode.FORWARD, Path.PAGE_CART);
	}}
