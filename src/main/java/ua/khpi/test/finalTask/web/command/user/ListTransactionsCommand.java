package ua.khpi.test.finalTask.web.command.user;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ua.khpi.test.finalTask.dao.AbstractDAOFactory;
import ua.khpi.test.finalTask.dao.AbstractDAOFactory.FactoryTypes;
import ua.khpi.test.finalTask.dao.PaymentsDAO;
import ua.khpi.test.finalTask.entity.Payment;
import ua.khpi.test.finalTask.exception.ApplicationException;
import ua.khpi.test.finalTask.logic.UserLogic;
import ua.khpi.test.finalTask.web.Path;
import ua.khpi.test.finalTask.web.RequestProcessorInfo;
import ua.khpi.test.finalTask.web.RequestProcessorInfo.ProcessorMode;
import ua.khpi.test.finalTask.web.command.Command;


public class ListTransactionsCommand extends Command {

	private static final Logger LOG = LogManager.getLogger(ListTransactionsCommand.class);
	UserLogic userLogic;

	public ListTransactionsCommand(UserLogic userLogic) {
		this.userLogic = userLogic;
	}

	@Override
	public RequestProcessorInfo execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, ApplicationException {
		LOG.debug("Command starts");

		HttpSession session = request.getSession();
		String accId = request.getParameter("accountId");
		List<Payment> paymentsInAccount = getPaymentsInAccount(accId);
		session.setAttribute("accPayments", paymentsInAccount);

		LOG.debug("Command finished");
		return new RequestProcessorInfo(ProcessorMode.FORWARD, Path.COMMAND_SORT_TRANSACTIONS);
	}

	private List<Payment> getPaymentsInAccount(String accId) throws ApplicationException {
	

		LOG.trace("Setting acc --> " + accId);
		int accountId = Integer.parseInt(accId);

		return userLogic.getPaymentsByAccount(accountId);
	}
}
