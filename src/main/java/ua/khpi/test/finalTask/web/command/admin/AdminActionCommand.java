package ua.khpi.test.finalTask.web.command.admin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ua.khpi.test.finalTask.entity.Account;
import ua.khpi.test.finalTask.entity.User;
import ua.khpi.test.finalTask.entity.enums.AccountStatus;
import ua.khpi.test.finalTask.entity.enums.UserStatus;
import ua.khpi.test.finalTask.exception.ApplicationException;
import ua.khpi.test.finalTask.logic.AdminLogic;
import ua.khpi.test.finalTask.web.Path;
import ua.khpi.test.finalTask.web.RequestProcessorInfo;
import ua.khpi.test.finalTask.web.RequestProcessorInfo.ProcessorMode;
import ua.khpi.test.finalTask.web.command.Command;


public class AdminActionCommand extends Command {

	private static final Logger LOG = LogManager.getLogger(AdminActionCommand.class);
	AdminLogic adminLogic;

	public AdminActionCommand(AdminLogic adminLogic) {
		this.adminLogic = adminLogic;
	}

	@Override
	public RequestProcessorInfo execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, ApplicationException {
		LOG.debug("Command starts");

		String action = request.getParameter("action");
		LOG.trace("Action to perform --> " + action);

		switch (action) {
		case "lockUser":
			userAction(request, UserStatus.BLOCKED);
			break;
		case "unlockUser":
			userAction(request, UserStatus.ALLOWED);
			break;
		case "lockAccount":
			accountAction(request, AccountStatus.LOCKED);
			break;
		case "unlockAccount":
			accountAction(request, AccountStatus.OPEN);
			break;
		default:
			throw new ApplicationException("unknown command");
		}

		LOG.debug("Command finished");
		return new RequestProcessorInfo(ProcessorMode.REDIRECT, Path.COMMAND_ADMIN_ACTION_CONFIRMED);
	}

	private void userAction(HttpServletRequest request, UserStatus status) throws ApplicationException {
		String userId = request.getParameter("userId");
		LOG.trace("Changing user status to --> " + status);
		int usrId = Integer.parseInt(userId);

		User user = adminLogic.getEntityById(usrId);
		LOG.trace("User to update --> " + user);
		if (user == null) {
			throw new ApplicationException("User not found");
		} else {
			user.setUserStatusId(status.ordinal());
			adminLogic.update(user);
			LOG.trace("Update completed");
		}
	}

	private void accountAction(HttpServletRequest request, AccountStatus status) throws ApplicationException {
		String accountId = request.getParameter("accountId");
		LOG.trace("Changing account status to --> " + status);
		int accId = Integer.parseInt(accountId);
		Account account = adminLogic.getAccEntityById(accId);
		LOG.trace("Account to update --> " + account);
		account.setAccountStatusId(status.ordinal());
		adminLogic.update(account);
		LOG.trace("Update completed");
	}

}
