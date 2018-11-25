package ua.khpi.test.finalTask.web.command.user;

import java.io.IOException;
import java.math.BigDecimal;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ua.khpi.test.finalTask.dao.AbstractDAOFactory;
import ua.khpi.test.finalTask.dao.AbstractDAOFactory.FactoryTypes;
import ua.khpi.test.finalTask.dao.AccountDAO;
import ua.khpi.test.finalTask.entity.Account;
import ua.khpi.test.finalTask.entity.User;
import ua.khpi.test.finalTask.entity.enums.AccountStatus;
import ua.khpi.test.finalTask.exception.ApplicationException;
import ua.khpi.test.finalTask.logic.UserLogic;
import ua.khpi.test.finalTask.web.Path;
import ua.khpi.test.finalTask.web.RequestProcessorInfo;
import ua.khpi.test.finalTask.web.RequestProcessorInfo.ProcessorMode;
import ua.khpi.test.finalTask.web.command.Command;


public class CloseAccountCommand extends Command {

	private static final Logger LOG = LogManager.getLogger(CloseAccountCommand.class);
	UserLogic userLogic;

	public CloseAccountCommand(UserLogic userLogic) {
		this.userLogic = userLogic;
	}

	@Override
	public RequestProcessorInfo execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, ApplicationException {
		LOG.debug("Command starts");

		HttpSession session = request.getSession();

		String accIdStr = request.getParameter("accountId");
		User user = (User) session.getAttribute("user");

		closeAccount(accIdStr, user);

		return new RequestProcessorInfo(ProcessorMode.REDIRECT, Path.COMMAND_LIST_ACCOUNTS);
	}

	private void closeAccount(String accIdStr, User user) throws ApplicationException {
		
		LOG.trace("Account to close --> " + accIdStr);
		LOG.trace("User --> " + user);

		int accId = Integer.parseInt(accIdStr);
		Account account = userLogic.getEntityById(accId);

		checkBalance(account);

		account.setAccountStatusId(AccountStatus.CLOSED.ordinal());
		LOG.trace("Account to update --> " + account);
		userLogic.update(account);
	}

	private void checkBalance(Account account) throws ApplicationException {
		if (account.getBalance().compareTo(new BigDecimal("0")) != 0) {
			LOG.trace("balance is non-zero");
			throw new ApplicationException("Can't close account with non-zero balance");
		}
	}
}
