package ua.khpi.test.finalTask.web.command.user;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ua.khpi.test.finalTask.entity.Account;
import ua.khpi.test.finalTask.exception.ApplicationException;
import ua.khpi.test.finalTask.web.Path;
import ua.khpi.test.finalTask.web.RequestProcessorInfo;
import ua.khpi.test.finalTask.web.RequestProcessorInfo.ProcessorMode;
import ua.khpi.test.finalTask.web.command.Command;

public class SortAccountsCommand extends Command {

	private static final Logger LOG = LogManager.getLogger(SortAccountsCommand.class);

	@Override
	public RequestProcessorInfo execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, ApplicationException {
		LOG.debug("Command starts");

		HttpSession session = request.getSession();
		@SuppressWarnings("unchecked")
		List<Account> accounts = (List<Account>) session.getAttribute("accounts");
		LOG.trace("Accounts obtained: " + accounts);
		if (accounts == null) {
			LOG.trace("Cant get accounts from session");
		} else {
			String sortBy = (String) session.getAttribute("accountsOrder");
			LOG.trace("List will be sorted by " + sortBy);
			switch (sortBy) {
			case "number":
				accounts.stream().sorted(Comparator.comparing(Account::getId));
				break;
			case "balance":
				Collections.sort(accounts, Comparator.comparing(Account::getBalance));
				break;
			case "name":
				accounts.sort((acc1, acc2) -> acc1.getName().compareTo(acc2.getName()));
				break;
			default:
				throw new ApplicationException("Can't get sort parameter");
			}
		}
		LOG.trace("Sorted accounts: " + accounts);

		session.setAttribute("accounts", accounts);

		return new RequestProcessorInfo(ProcessorMode.FORWARD, Path.PAGE_LIST_ACCOUNTS);
	}

}
