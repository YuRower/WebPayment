package ua.khpi.test.finalTask.web.command.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ua.khpi.test.finalTask.entity.Request;
import ua.khpi.test.finalTask.entity.User;
import ua.khpi.test.finalTask.exception.ApplicationException;
import ua.khpi.test.finalTask.logic.UserLogic;
import ua.khpi.test.finalTask.web.Path;
import ua.khpi.test.finalTask.web.RequestProcessorInfo;
import ua.khpi.test.finalTask.web.RequestProcessorInfo.ProcessorMode;
import ua.khpi.test.finalTask.web.command.Command;

public class RequestUnlockAccountCommand extends Command {

	private static final Logger LOG = LogManager.getLogger(RequestUnlockAccountCommand.class);
	UserLogic userLogic;

	public RequestUnlockAccountCommand(UserLogic userLogic) {
		this.userLogic = userLogic;
	}

	@Override
	public RequestProcessorInfo execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, ApplicationException {
		LOG.debug("Command starts");

		HttpSession session = request.getSession();

		String accIdStr = request.getParameter("accountId");
		LOG.trace("Account to unlock --> " + accIdStr);
		int accId = Integer.parseInt(accIdStr);

		User user = (User) session.getAttribute("user");
		LOG.trace("User --> " + user);

		Request requestForUnlock = new Request(user.getId(), accId);
		LOG.trace("Request --> " + requestForUnlock);

		userLogic.addRequest(requestForUnlock);
		LOG.trace("Request was successfully sent");
		LOG.debug("Command finished");

		return new RequestProcessorInfo(ProcessorMode.REDIRECT, Path.COMMAND_LIST_ACCOUNTS);
	}
}
