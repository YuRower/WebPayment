package ua.khpi.test.finalTask.web.command.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import lombok.NonNull;
import ua.khpi.test.finalTask.entity.Account;
import ua.khpi.test.finalTask.exception.ApplicationException;
import ua.khpi.test.finalTask.logic.UserLogic;
import ua.khpi.test.finalTask.utils.RegularExpressions;
import ua.khpi.test.finalTask.web.Path;
import ua.khpi.test.finalTask.web.RequestProcessorInfo;
import ua.khpi.test.finalTask.web.RequestProcessorInfo.ProcessorMode;
import ua.khpi.test.finalTask.web.command.Command;

public class ChangeAccountNameCommand extends Command {

	private static final Logger LOG = LogManager.getLogger(ChangeAccountNameCommand.class);
	UserLogic userLogic;

	public ChangeAccountNameCommand(UserLogic userLogic) {
		this.userLogic = userLogic;
	}

	@Override
	public RequestProcessorInfo execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, ApplicationException {
		LOG.debug("Command starts");

		String accIdStr = request.getParameter("accountId");
		String accountName = request.getParameter("name");
		validateName(accountName);
		LOG.trace("New account name --> " + accountName);
		changeAccountName(accountName, accIdStr);
		LOG.debug("Command finished");
		return new RequestProcessorInfo(ProcessorMode.REDIRECT, Path.COMMAND_LIST_ACCOUNTS);
	}

	private void changeAccountName(String accountName, @NonNull String accIdStr) throws ApplicationException {

		Account account = userLogic.getEntityById(Integer.parseInt(accIdStr));
		LOG.trace("Editing account --> " + account);
		account.setName(accountName);
		userLogic.update(account);
		LOG.trace("Successfully edited");
	}

	private void validateName(String accountName) throws ApplicationException {
		if (accountName == null || accountName.trim().isEmpty()
				|| !accountName.matches(RegularExpressions.ACCOUNT_NAME)) {
			throw new ApplicationException("Invalid account name");
		}
	}

}
