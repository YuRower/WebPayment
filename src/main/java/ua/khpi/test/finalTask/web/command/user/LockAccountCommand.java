package ua.khpi.test.finalTask.web.command.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ua.khpi.test.finalTask.entity.Account;
import ua.khpi.test.finalTask.entity.enums.AccountStatus;
import ua.khpi.test.finalTask.exception.ApplicationException;
import ua.khpi.test.finalTask.logic.UserLogic;
import ua.khpi.test.finalTask.web.Path;
import ua.khpi.test.finalTask.web.RequestProcessorInfo;
import ua.khpi.test.finalTask.web.RequestProcessorInfo.ProcessorMode;
import ua.khpi.test.finalTask.web.command.Command;


public class LockAccountCommand extends Command {

	private static final Logger LOG = LogManager.getLogger(LockAccountCommand.class);
	UserLogic userLogic;

	public LockAccountCommand(UserLogic userLogic) {
		this.userLogic = userLogic;
	}

	@Override
	public RequestProcessorInfo execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, ApplicationException {
		LOG.debug("Command starts");
		
		String accountIdStr = request.getParameter("accountId");
		LOG.trace("Account to lock (ID) --> " + accountIdStr);
		int accountId = Integer.parseInt(accountIdStr);

		Account accountToLock = userLogic.getEntityById(accountId);
		LOG.trace("Account to lock --> " + accountToLock);
		accountToLock.setAccountStatusId(AccountStatus.LOCKED.ordinal());
		userLogic.update(accountToLock);
		LOG.trace("Account after locking --> " + accountToLock);

		LOG.info("Account locked completely");

		LOG.debug("Command finished");
		return new RequestProcessorInfo(ProcessorMode.REDIRECT, Path.COMMAND_LIST_ACCOUNTS);
	}

}
