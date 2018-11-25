package ua.khpi.test.finalTask.web.command.admin;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ua.khpi.test.finalTask.dao.UserAccountsCountDAO;
import ua.khpi.test.finalTask.dao.mysql.MysqlDAOFactory;
import ua.khpi.test.finalTask.entity.bean.UserAccountsCount;
import ua.khpi.test.finalTask.exception.ApplicationException;
import ua.khpi.test.finalTask.logic.AdminLogic;
import ua.khpi.test.finalTask.web.Path;
import ua.khpi.test.finalTask.web.RequestProcessorInfo;
import ua.khpi.test.finalTask.web.RequestProcessorInfo.ProcessorMode;
import ua.khpi.test.finalTask.web.command.Command;

public class AccountsGreaterCommand extends Command {

	private static final Logger LOG = LogManager.getLogger(AccountsGreaterCommand.class);
	AdminLogic adminLogic;

	public AccountsGreaterCommand(AdminLogic adminLogic) {
		this.adminLogic = adminLogic;
	}

	@Override
	public RequestProcessorInfo execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, ApplicationException {
		LOG.debug("Command starts");
		HttpSession session = request.getSession();
		String balance = request.getParameter("balance");

		List<UserAccountsCount> list = getList(balance);
		LOG.trace("Returned list --> " + list);

		session.setAttribute("showList", list);
		session.setAttribute("showListBalance", balance);
		LOG.debug("Command finished");
		return new RequestProcessorInfo(ProcessorMode.FORWARD, Path.PAGE_EPAM_TASK);
	}

	private List<UserAccountsCount> getList(String balance) throws ApplicationException {
		return adminLogic.getAccountsWithBalanceGreaterThan(Integer.parseInt(balance));
	}

}
