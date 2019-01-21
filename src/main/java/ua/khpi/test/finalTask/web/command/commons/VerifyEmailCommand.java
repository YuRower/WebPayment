package ua.khpi.test.finalTask.web.command.commons;

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
import ua.khpi.test.finalTask.exception.ApplicationException;
import ua.khpi.test.finalTask.logic.CommonLogic;
import ua.khpi.test.finalTask.web.Path;
import ua.khpi.test.finalTask.web.RequestProcessorInfo;
import ua.khpi.test.finalTask.web.RequestProcessorInfo.ProcessorMode;
import ua.khpi.test.finalTask.web.command.Command;

/**
 * Email verification command
 *
 */
public class VerifyEmailCommand extends Command {
	
	private static final Logger LOG = LogManager.getLogger(VerifyEmailCommand.class);

	CommonLogic commonLogic;

	public VerifyEmailCommand(CommonLogic commonLogic) {
		this.commonLogic = commonLogic;
	}

	@Override
	public RequestProcessorInfo execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, ApplicationException {
		LOG.debug("Command starts");
		
		String email = request.getParameter("email");
		LOG.trace("Email --> "+email);
		String code = request.getParameter("code");
		LOG.trace("code --> "+code);

		User user = commonLogic.findUserByEmail(email);
		LOG.trace("User --> "+user);
		if(user == null) {
			throw new ApplicationException("No such user in database");
		}
		if(!user.getEmailVerification().equals(code)) {
			throw new ApplicationException("Verification failed!");
		}
		
		user.setUserStatusId(UserStatus.ALLOWED.ordinal());
		user.setEmailVerification(null);
		commonLogic.update(user);
		
		LOG.debug("Command finished");
		return new RequestProcessorInfo(ProcessorMode.REDIRECT,Path.COMMAND_REDIRECT_VALIDATION_COMPLETED);
	}

}
