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

import ua.khpi.test.finalTask.entity.Card;
import ua.khpi.test.finalTask.entity.Payment;
import ua.khpi.test.finalTask.entity.enums.Fee;
import ua.khpi.test.finalTask.entity.enums.PaymentType;
import ua.khpi.test.finalTask.exception.ApplicationException;
import ua.khpi.test.finalTask.exception.ConnectionException;
import ua.khpi.test.finalTask.exception.DBException;
import ua.khpi.test.finalTask.logic.UserLogic;
import ua.khpi.test.finalTask.web.Path;
import ua.khpi.test.finalTask.web.RequestProcessorInfo;
import ua.khpi.test.finalTask.web.RequestProcessorInfo.ProcessorMode;
import ua.khpi.test.finalTask.web.command.Command;

public class ReplenishAccountCommand extends Command {

	private static final Logger LOG = LogManager.getLogger(ReplenishAccountCommand.class);
	UserLogic userLogic;

	public ReplenishAccountCommand(UserLogic userLogic) {
		this.userLogic = userLogic;
	}

	@Override
	public RequestProcessorInfo execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, ApplicationException {
		LOG.debug("Command starts");

		HttpSession session = request.getSession();

		String amountStr = request.getParameter("amount");
		LOG.trace("Replenish amount --> " + amountStr);
		String accountId = request.getParameter("accountId");
		LOG.trace("Account id --> " + accountId);
		int accID = Integer.parseInt(accountId);
		double amountReceived = validateAmount(amountStr);
		String card_id = String.valueOf(session.getAttribute("current_card"));

		Card card = defineCard(Integer.parseInt(card_id));
		Fee fee = Fee.getFee(card);
		LOG.trace("card fee --> " + fee);
		int percentage = defineCardFee(fee);
		double amount = calculatePercentage(percentage, Double.parseDouble(amountStr));
		LOG.trace("amount of payment with card fee --> " + percentage+""+ amount);
		Payment payment = new Payment();
		double finalPayment = amountReceived+amount;
		LOG.trace("amount of payment with card fee --> " + finalPayment);

		payment.setMoneyAmount(new BigDecimal(finalPayment));
		payment.setAccountIdTo(accID);
		payment.setPaymentTypeId(PaymentType.REPLENISH.ordinal());
		LOG.trace("Created payment --> " + payment);

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

	private Card defineCard(int accountFrom) throws DBException, ConnectionException {
		Card card = userLogic.getCardbyId(accountFrom);
		return card;

	}

	private int defineCardFee(Fee cardFee) throws ApplicationException {
		switch (cardFee) {
		case GOLD:
			return Fee.GOLD.getPercentage();
		case SILVER:
			return Fee.SILVER.getPercentage();
		case DEFAULT:
			return Fee.DEFAULT.getPercentage();
		default:
			throw new ApplicationException("Unresolved card fee");
		}
	}

	public double calculatePercentage(double percentage, double total) {
		return percentage == 0 ? total : total / 100 * percentage;
	}

	private double validateAmount(String amountStr) throws ApplicationException {
		double amount = Double.parseDouble(amountStr);
		if (amount <= 0 || amount > 10000) {
			throw new ApplicationException("Wrong amount");
		}
		return amount;
	}
}
