package ua.khpi.test.finalTask.web.command.user.redirect;

import java.io.IOException;
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



public class RedirectAccountSettingsCommand extends Command {
	
	private static final Logger LOG = LogManager.getLogger(RedirectAccountSettingsCommand.class);

	@Override
	public RequestProcessorInfo execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, ApplicationException {
		LOG.debug("Command starts");

		HttpSession session = request.getSession();
		String accId = request.getParameter("accountId");
		LOG.trace("Setting acc --> "+ accId);
		int accountId = Integer.parseInt(accId);

		@SuppressWarnings("unchecked")
		List<Account> accounts = (List<Account>) session.getAttribute("accounts");
		
		for (Account account : accounts) {
			if (account.getId() == accountId) {
				session.setAttribute("settingsAcc", account);
			}
		}
		LOG.trace("Redirecting to account settings page");

		LOG.debug("Command finished");
		return new RequestProcessorInfo(ProcessorMode.FORWARD, Path.PAGE_ACCOUNT_SETTINGS);
	}
	
}
