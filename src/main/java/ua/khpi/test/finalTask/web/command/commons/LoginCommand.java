package ua.khpi.test.finalTask.web.command.commons;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import ua.khpi.test.finalTask.entity.User;
import ua.khpi.test.finalTask.entity.enums.UserStatus;
import ua.khpi.test.finalTask.entity.enums.UserType;
import ua.khpi.test.finalTask.exception.ApplicationException;
import ua.khpi.test.finalTask.logic.CommonLogic;
import ua.khpi.test.finalTask.utils.PasswordHasher;
import ua.khpi.test.finalTask.web.Path;
import ua.khpi.test.finalTask.web.RequestProcessorInfo;
import ua.khpi.test.finalTask.web.RequestProcessorInfo.ProcessorMode;
import ua.khpi.test.finalTask.web.command.Command;


public class LoginCommand extends Command {

	private static final Logger LOG = LogManager.getLogger(LoginCommand.class);
	CommonLogic commonLogic;

	public LoginCommand(CommonLogic commonLogic) {
		this.commonLogic = commonLogic;
	}

	@Override
	public RequestProcessorInfo execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, ApplicationException {
		LOG.debug("Command starts");

		HttpSession session = request.getSession();

		String email = request.getParameter("email");
		String password = request.getParameter("password");

		User user = validateUser(email, password);

		checkUserStatus(user);

		UserType userType = UserType.getType(user);
		LOG.trace("userType --> " + userType);

		String forward = defineForwardAddressByUserType(userType);

		session.setAttribute("user", user);
		LOG.trace("Set the session attribute: user --> " + user);

		session.setAttribute("userType", userType);
		LOG.trace("Set the session attribute: userType --> " + userType);

		LOG.info("User " + user + " logged as " + userType.toString().toLowerCase());

		LOG.debug("Command finished");
		return new RequestProcessorInfo(ProcessorMode.REDIRECT, forward);
	}

	private String defineForwardAddressByUserType(UserType userType) throws ApplicationException {
		switch (userType) {
		case ADMIN:
			return Path.COMMAND_LIST_REQUESTS;
		case USER:
			return Path.COMMAND_INITIALIZE_USER_SESSION;
		case SUPERUSER:
			return Path.COMMAND_LIST_ADMINS;
		default:
			throw new ApplicationException("Unresolved usertype");
		}
	}

	private User validateUser(String email, String password) throws ApplicationException {

		LOG.trace("Request parameter: email --> " + email);

		if (email == null || password == null || email.trim().isEmpty() || password.trim().isEmpty()) {
			throw new ApplicationException("Email/password cannot be empty");
		}

		User user = commonLogic.findUserByEmail(email);
		LOG.trace("Found in DB: user --> " + user);

		if (user == null || !PasswordHasher.checkPassword(password, user.getPassword())) {
			throw new ApplicationException("Cannot find user with such login/password");
		}
		return user;
	}

	private void checkUserStatus(User user) throws ApplicationException {
		if (UserStatus.getStatus(user) == UserStatus.BLOCKED) {
			LOG.info("Blocked user tried to log in: " + user);
			throw new ApplicationException("Your profile is blocked for some reason.\nContact administrator.");
		} else if (UserStatus.getStatus(user) == UserStatus.VALIDATION) {
			LOG.info("User with unvalidated email tried to log in: " + user);
			throw new ApplicationException("Your profile is not validated.\nCheck your email for validation link.");
		}
	}
}
