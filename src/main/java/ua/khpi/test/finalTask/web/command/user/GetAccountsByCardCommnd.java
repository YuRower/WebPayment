package ua.khpi.test.finalTask.web.command.user;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ua.khpi.test.finalTask.entity.Account;
import ua.khpi.test.finalTask.entity.User;
import ua.khpi.test.finalTask.entity.enums.AccountStatus;
import ua.khpi.test.finalTask.exception.ApplicationException;
import ua.khpi.test.finalTask.logic.UserLogic;
import ua.khpi.test.finalTask.web.Path;
import ua.khpi.test.finalTask.web.RequestProcessorInfo;
import ua.khpi.test.finalTask.web.RequestProcessorInfo.ProcessorMode;
import ua.khpi.test.finalTask.web.command.Command;

public class GetAccountsByCardCommnd extends Command {

	private static final Logger LOG = LogManager.getLogger(ListUserAccountsCommand.class);

	UserLogic userLogic;

	public GetAccountsByCardCommnd(UserLogic userLogic) {
		this.userLogic = userLogic;
	}

	@Override
	public RequestProcessorInfo execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, ApplicationException {
		LOG.debug("Command starts");

		HttpSession session = request.getSession();
		//String card_id = String.valueOf(session.getAttribute("current_card"));
        String card_id = request.getParameter("current_card");
		List<Account> accounts = getUserAccounts(card_id);
		session.setAttribute("accounts", accounts);
		session.setAttribute("current_card", card_id);


		LOG.debug("Command finished");
		return new RequestProcessorInfo(ProcessorMode.FORWARD, Path.COMMAND_SORT_ACCOUNTS);
	}

	private List<Account> getUserAccounts(String card_id) throws ApplicationException {
		LOG.trace("card_id --> " + card_id);

		List<Account> accounts = userLogic.getAccountsCardId(Integer.parseInt(card_id));
		if (accounts.isEmpty()) {
			LOG.trace("User have 0 acc");
		} else {
			accounts = extractClosedAccounts(accounts);
		}
		LOG.trace("accounts --> " + accounts);
		return accounts;
	}

	private List<Account> extractClosedAccounts(List<Account> accounts) {
		List<Account> closedAccounts = new ArrayList<>();
		for (Account account : accounts) {
			LOG.trace("Found acc --> " + account);
			if (account.getAccountStatusId() == AccountStatus.CLOSED.ordinal()) {
				closedAccounts.add(account);
			}
		}
		accounts.removeAll(closedAccounts);
		return accounts;
	}

}
