package ua.khpi.test.finalTask.web.command.admin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.khpi.test.finalTask.entity.Account;
import ua.khpi.test.finalTask.entity.Request;
import ua.khpi.test.finalTask.entity.enums.AccountStatus;
import ua.khpi.test.finalTask.exception.ApplicationException;
import ua.khpi.test.finalTask.logic.AdminLogic;
import ua.khpi.test.finalTask.web.Path;
import ua.khpi.test.finalTask.web.RequestProcessorInfo;
import ua.khpi.test.finalTask.web.RequestProcessorInfo.ProcessorMode;
import ua.khpi.test.finalTask.web.command.Command;

public class RequestResponseCommand extends Command {

	private static final Logger LOG = LogManager.getLogger(RequestResponseCommand.class);
	AdminLogic adminLogic;

	public RequestResponseCommand(AdminLogic adminLogic) {
		this.adminLogic = adminLogic;
	}

	@Override
	public RequestProcessorInfo execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, ApplicationException {
		LOG.debug("Command starts");

		String userId = request.getParameter("requestUser");
		LOG.trace("User id --> " + userId);
		String accountId = request.getParameter("requestAccount");
		LOG.trace("Account id --> " + accountId);
		int usrId = Integer.parseInt(userId);
		int accId = Integer.parseInt(accountId);

		String action = request.getParameter("action");
		LOG.trace("Action to perform --> " + action);

		switch (action) {
		case "unlock":
			unlockAccount(usrId, accId);
			break;
		case "ignore":
			ignoreRequest(usrId, accId);
			break;
		default:
			throw new ApplicationException("unknown command");
		}

		LOG.debug("Command finished");
		return new RequestProcessorInfo(ProcessorMode.REDIRECT, Path.COMMAND_ADMIN_ACTION_CONFIRMED);
	}

	private void unlockAccount(int usrId, int accId) throws ApplicationException {

		Account account = adminLogic.getAccEntityById(accId);
		LOG.trace("Account to be unlocked --> " + account);
		account.setAccountStatusId(AccountStatus.OPEN.ordinal());
		adminLogic.update(account);
		LOG.trace("Account successfully updated");

		ignoreRequest(usrId, accId);
	}

	private void ignoreRequest(int usrId, int accId) throws ApplicationException {

		Request request = new Request(usrId, accId);
		LOG.trace("Removing request --> " + request);
		adminLogic.removeRequest(request);
		LOG.trace("Successfully removed");
	}

}
