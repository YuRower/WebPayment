package ua.khpi.test.finalTask.web.command.commons;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ua.khpi.test.finalTask.entity.Payment;
import ua.khpi.test.finalTask.entity.User;
import ua.khpi.test.finalTask.entity.enums.UserType;
import ua.khpi.test.finalTask.exception.ApplicationException;
import ua.khpi.test.finalTask.utils.Serializer;
import ua.khpi.test.finalTask.web.Path;
import ua.khpi.test.finalTask.web.RequestProcessorInfo;
import ua.khpi.test.finalTask.web.RequestProcessorInfo.ProcessorMode;
import ua.khpi.test.finalTask.web.command.Command;

/**
 * Logout command
 * 
 */
public class LogoutCommand extends Command {
	
	private static final Logger LOG = LogManager.getLogger(LogoutCommand.class);

	@Override
	public RequestProcessorInfo execute(HttpServletRequest request, HttpServletResponse response) 
			throws IOException, ServletException, ApplicationException {
		LOG.debug("Command starts");
		
		HttpSession session = request.getSession(false);
		
		savePreparedPayments(session, response);
		
		if (session != null) {
			session.invalidate();
		}

		LOG.debug("Command finished");
		return new RequestProcessorInfo(ProcessorMode.REDIRECT,Path.PAGE_LOGIN);
	}

	private void savePreparedPayments(HttpSession session, HttpServletResponse response) throws ApplicationException, IOException {
		User user = (User) session.getAttribute("user");
		
		if(user.getUserTypeId() == UserType.USER.ordinal()) {
			LOG.trace("Saving user's prepared payments to cookies");
			@SuppressWarnings("unchecked")
			ArrayList<Payment> preparedPayments = (ArrayList<Payment>) session.getAttribute("prepPayments");
			Cookie cookie = new Cookie(""+user.getId(), Serializer.toString(preparedPayments));
			cookie.setMaxAge(60*60);
			response.addCookie(cookie);
			LOG.trace("Cookie was completely added!");
		}
		
	}
	


}
