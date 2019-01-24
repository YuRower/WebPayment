package ua.khpi.test.finalTask.web.command.commons;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ua.khpi.test.finalTask.dao.AbstractDAOFactory;
import ua.khpi.test.finalTask.dao.AbstractDAOFactory.FactoryTypes;
import ua.khpi.test.finalTask.dao.UserDAO;
import ua.khpi.test.finalTask.entity.User;
import ua.khpi.test.finalTask.exception.ApplicationException;
import ua.khpi.test.finalTask.logic.CommonLogic;
import ua.khpi.test.finalTask.utils.MailSender;
//import ua.khpi.test.finalTask.utils.MailSender;
import ua.khpi.test.finalTask.utils.PasswordHasher;
import ua.khpi.test.finalTask.utils.RegularExpressions;
import ua.khpi.test.finalTask.web.Path;
import ua.khpi.test.finalTask.web.RequestProcessorInfo;
import ua.khpi.test.finalTask.web.RequestProcessorInfo.ProcessorMode;
import ua.khpi.test.finalTask.web.command.Command;
import ua.khpi.test.finalTask.web.command.commons.validation.EmailValidator;
import ua.khpi.test.finalTask.web.command.commons.validation.Handler;
import ua.khpi.test.finalTask.web.command.commons.validation.Middleware;
import ua.khpi.test.finalTask.web.command.commons.validation.NameValidator;
import ua.khpi.test.finalTask.web.command.commons.validation.PasswordValidator;
import ua.khpi.test.finalTask.web.command.commons.validation.SurnameValidator;

/**
 * Registration of new user command
 *
 */
public class RegistrationCommand extends Command {

	private static final Logger LOG = LogManager.getLogger(RegistrationCommand.class);
	CommonLogic commonLogic;

	public RegistrationCommand(CommonLogic commonLogic) {
		this.commonLogic = commonLogic;
	}

	@Override
	public RequestProcessorInfo execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, ApplicationException {
		LOG.debug("Command starts");
		Handler handler = new Handler();
		String email = request.getParameter("email");

		String password = request.getParameter("password");

		String name = request.getParameter("name");
		String surname = request.getParameter("surname");
		Middleware middleware = new SurnameValidator(surname);

		middleware.linkWith(new NameValidator(name)).linkWith(new PasswordValidator(password))
				.linkWith(new EmailValidator(email));
		handler.setMiddleware(middleware);

		boolean success;
		do {
			success = handler.validate();
		} while (success);

		password = hashPassword(password);

		// random string that will be used for verification purposes

		String emailVerification = UUID.randomUUID().toString();
	/*	boolean isUserAdded = composeAndSaveUser(name, surname, email, emailVerification, password);
		if (isUserAdded) {
			sendVerificationMail(email, emailVerification, request);
		}*/
		composeAndSaveUser(name, surname, email, null, password);
		LOG.debug("Command finished");
		return new RequestProcessorInfo(ProcessorMode.REDIRECT, Path.COMMAND_REDIRECT_REGISTRATION_COMPLETED);

	}

	private boolean composeAndSaveUser(String name, String surname, String email, String emailVerification,
			String password) throws ApplicationException {

		User user = new User(name, surname, email, emailVerification, password);
		LOG.trace("Created user --> " + user);

		boolean result = commonLogic.newUserWithDefaultValues(user);
		LOG.trace("Saved user --> " + user);
		return result;

	}

	private void sendVerificationMail(String email, String emailVerification, HttpServletRequest request)
			throws ApplicationException {
		MailSender sender = new MailSender();
		sender.composeMessage(email, emailVerification, request);
	}

	private String hashPassword(String password) {
		return PasswordHasher.getHash(password);
	}

}
