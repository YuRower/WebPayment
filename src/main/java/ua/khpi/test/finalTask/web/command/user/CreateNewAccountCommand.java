package ua.khpi.test.finalTask.web.command.user;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ua.khpi.test.finalTask.entity.Account;
import ua.khpi.test.finalTask.entity.User;
import ua.khpi.test.finalTask.exception.ApplicationException;
import ua.khpi.test.finalTask.logic.UserLogic;
import ua.khpi.test.finalTask.utils.RegularExpressions;
import ua.khpi.test.finalTask.web.Path;
import ua.khpi.test.finalTask.web.RequestProcessorInfo;
import ua.khpi.test.finalTask.web.RequestProcessorInfo.ProcessorMode;
import ua.khpi.test.finalTask.web.command.Command;


public class CreateNewAccountCommand extends Command {

	private static final Logger LOG = LogManager.getLogger(CreateNewAccountCommand.class);
	UserLogic userLogic;
	public CreateNewAccountCommand(UserLogic userLogic) {
		this.userLogic=userLogic;
	}

	@Override
	public RequestProcessorInfo execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, ApplicationException {
		LOG.debug("Command starts");

		HttpSession session = request.getSession();

		String accName = request.getParameter("name");
		checkName(accName);

		User currUser = (User) session.getAttribute("user");
		String card_id = String.valueOf(session.getAttribute("current_card"));

		addAccount(accName, currUser , card_id);

		LOG.debug("Command finished");
		return new RequestProcessorInfo(ProcessorMode.REDIRECT, Path.COMMAND_LIST_ACCOUNTS);
	}

	private void addAccount(String accName, User currUser ,String cardID) throws ApplicationException {
		LOG.trace("User --> " + currUser);
		List<Account> userAccs = userLogic.getAccountsByUserId(currUser.getId());
		LOG.trace("User has " + userAccs.size() + " accounts");

		if (userAccs.size() >= 5) {
			throw new ApplicationException("You can't have more than 5 accounts in one profile");
		}

		Account newAccount = new Account();
		newAccount.setName(accName);
		newAccount.setUserId(currUser.getId());
		newAccount.setCardid(Integer.parseInt(cardID));
		userLogic.addEntity(newAccount);
		LOG.trace("Account added");
	}

	private void checkName(String accName) throws ApplicationException {
		LOG.trace("New account name --> " + accName);
		if (!accName.matches(RegularExpressions.ACCOUNT_NAME)) {
			throw new ApplicationException("Wrong account name");
		}
	}

}
