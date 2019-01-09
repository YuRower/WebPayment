package ua.khpi.test.finalTask.web.command.user;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ua.khpi.test.finalTask.entity.Card;
import ua.khpi.test.finalTask.entity.Payment;
import ua.khpi.test.finalTask.entity.User;
import ua.khpi.test.finalTask.exception.ApplicationException;
import ua.khpi.test.finalTask.utils.Serializer;
import ua.khpi.test.finalTask.web.Path;
import ua.khpi.test.finalTask.web.RequestProcessorInfo;
import ua.khpi.test.finalTask.web.RequestProcessorInfo.ProcessorMode;
import ua.khpi.test.finalTask.web.command.Command;


public class InitializeUserSessionCommand extends Command {

	private static final Logger LOG = LogManager.getLogger(InitializeUserSessionCommand.class);

	@Override
	public RequestProcessorInfo execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, ApplicationException {
		LOG.debug("Command starts");

		HttpSession session = request.getSession();
		LOG.trace("Defining default accounts order");
		session.setAttribute("accountsOrder", "number");
		LOG.trace("Defining default payments order");
		session.setAttribute("paymentsOrder", "dateASC");

		LOG.trace("Creating container for prepared payments");
		ArrayList<Payment> preparedPayments = getPreparedPayments(session, request);
		session.setAttribute("prepPayments", preparedPayments);

		LOG.debug("Command finished");
		return new RequestProcessorInfo(ProcessorMode.REDIRECT, Path.COMMAND_LIST_CARDS);
	}

	@SuppressWarnings("unchecked")
	private ArrayList<Payment> getPreparedPayments(HttpSession session, HttpServletRequest request) {
		ArrayList<Payment> result = new ArrayList<>();

		User user = (User) session.getAttribute("user");
		LOG.trace("Searching for prep payments in cookies");
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("" + user.getId())) {
					LOG.trace("Cookie found, trying to get list...");
					try {
						result = (ArrayList<Payment>) Serializer.fromString(cookie.getValue());
						LOG.trace("Success!");
					} catch (ClassNotFoundException | IOException e) {
						LOG.trace("Error while loading payments from cookies");
					}
				}
			}
		}
		return result;
	}
}
