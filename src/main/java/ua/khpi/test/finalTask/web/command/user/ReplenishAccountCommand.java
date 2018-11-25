package ua.khpi.test.finalTask.web.command.user;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ua.khpi.test.finalTask.entity.Payment;
import ua.khpi.test.finalTask.entity.enums.PaymentType;
import ua.khpi.test.finalTask.exception.ApplicationException;
import ua.khpi.test.finalTask.web.Path;
import ua.khpi.test.finalTask.web.RequestProcessorInfo;
import ua.khpi.test.finalTask.web.RequestProcessorInfo.ProcessorMode;
import ua.khpi.test.finalTask.web.command.Command;



public class ReplenishAccountCommand extends Command {
	
	private static final Logger LOG = LogManager.getLogger(ReplenishAccountCommand.class);

	@Override
	public RequestProcessorInfo execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, ApplicationException {
		LOG.debug("Command starts");

		HttpSession session = request.getSession();
		
		String amountStr = request.getParameter("amount");
		LOG.trace("Replenish amount --> "+amountStr);
		String accountId = request.getParameter("accountId");
		LOG.trace("Account id --> "+accountId);
		
		validateAmount(amountStr);
		
		Payment payment = new Payment();
		payment.setMoneyAmount(new BigDecimal(amountStr));
		payment.setAccountIdTo(Integer.parseInt(accountId));
		payment.setPaymentTypeId(PaymentType.REPLENISH.ordinal());
		LOG.trace("Created payment --> "+payment);
		
		addPaymentToPreparedPayments(payment, session);
		
		LOG.debug("Command finished");
		
		return new RequestProcessorInfo(ProcessorMode.REDIRECT, Path.COMMAND_REDIRECT_TRANSACTION_COMPLETED);
	}

	private void addPaymentToPreparedPayments(Payment payment, HttpSession session) {
		@SuppressWarnings("unchecked")
		List<Payment> preparedPayments = (List<Payment>) session.getAttribute("prepPayments");

		preparedPayments.add(payment);

		session.setAttribute("prepPayments", preparedPayments);
	}

	private void validateAmount(String amountStr) throws ApplicationException {
		double amount = Double.parseDouble(amountStr);
		if (amount <= 0 || amount > 10000) {
			throw new ApplicationException("Wrong amount");
		}
	}
}
