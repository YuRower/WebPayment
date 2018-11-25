package ua.khpi.test.finalTask.web.command.superuser;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ua.khpi.test.finalTask.dao.AbstractDAOFactory;
import ua.khpi.test.finalTask.dao.AbstractDAOFactory.FactoryTypes;
import ua.khpi.test.finalTask.dao.UserDAO;
import ua.khpi.test.finalTask.entity.User;
import ua.khpi.test.finalTask.entity.enums.UserStatus;
import ua.khpi.test.finalTask.entity.enums.UserType;
import ua.khpi.test.finalTask.exception.ApplicationException;
import ua.khpi.test.finalTask.logic.Superuser;
import ua.khpi.test.finalTask.utils.PasswordHasher;
import ua.khpi.test.finalTask.utils.RegularExpressions;
import ua.khpi.test.finalTask.web.Path;
import ua.khpi.test.finalTask.web.RequestProcessorInfo;
import ua.khpi.test.finalTask.web.RequestProcessorInfo.ProcessorMode;
import ua.khpi.test.finalTask.web.command.Command;
import ua.khpi.test.finalTask.web.command.commons.validation.Handler;
import ua.khpi.test.finalTask.web.command.commons.validation.LoginValidator;
import ua.khpi.test.finalTask.web.command.commons.validation.Middleware;
import ua.khpi.test.finalTask.web.command.commons.validation.NameValidator;
import ua.khpi.test.finalTask.web.command.commons.validation.PasswordValidator;
import ua.khpi.test.finalTask.web.command.commons.validation.SurnameValidator;


public class AddAdminCommand extends Command {

	private static final Logger LOG = LogManager.getLogger(AddAdminCommand.class);
	Superuser superuser;

	public AddAdminCommand(Superuser superuser) {
		this.superuser = superuser;
	}

	@Override
	public RequestProcessorInfo execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, ApplicationException {
		LOG.debug("Command starts");
		Handler handler = new Handler();

		String login = request.getParameter("login");

		String password = request.getParameter("password");

		String name = request.getParameter("name");
		String surname = request.getParameter("surname");

		Middleware middleware = new SurnameValidator(surname);
		middleware.linkWith(new NameValidator(name)).linkWith(new PasswordValidator(password))
				.linkWith(new LoginValidator(login));
		handler.setMiddleware(middleware);
		boolean success;
		do {
			success = handler.validate();
		} while (success);
		password = hashPassword(password);

		composeAndSaveUser(name, surname, login, password);

		LOG.debug("Command finished");
		return new RequestProcessorInfo(ProcessorMode.REDIRECT, Path.COMMAND_LIST_ADMINS);
	}

	private void composeAndSaveUser(String name, String surname, String login, String password)
			throws ApplicationException {

		User user = new User(name, surname, login, null, password, UserType.ADMIN.ordinal(),
				UserStatus.ALLOWED.ordinal());
		superuser.addEntity(user);
		LOG.trace("Saved user --> " + user);
	}

	private String hashPassword(String password) {
		return PasswordHasher.getHash(password);
	}

}
