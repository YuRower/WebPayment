package ua.khpi.test.finalTask.web.command.user;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ua.khpi.test.finalTask.entity.Account;
import ua.khpi.test.finalTask.entity.Payment;
import ua.khpi.test.finalTask.entity.enums.AccountStatus;
import ua.khpi.test.finalTask.entity.enums.PaymentType;
import ua.khpi.test.finalTask.exception.ApplicationException;
import ua.khpi.test.finalTask.exception.DBException;
import ua.khpi.test.finalTask.logic.UserLogic;
import ua.khpi.test.finalTask.web.Path;
import ua.khpi.test.finalTask.web.RequestProcessorInfo;
import ua.khpi.test.finalTask.web.RequestProcessorInfo.ProcessorMode;
import ua.khpi.test.finalTask.web.command.Command;

public class ExecuteCartPaymentsCommand extends Command {

	private static final Logger LOG = LogManager.getLogger(ExecuteCartPaymentsCommand.class);
	UserLogic userLogic;

	public ExecuteCartPaymentsCommand(UserLogic userLogic) {
		this.userLogic = userLogic;
	}

	@Override
	public RequestProcessorInfo execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, ApplicationException {
		LOG.debug("Command starts");

		HttpSession session = request.getSession();

		executeReplenishments(session);

		executeRemittances(session);

		LOG.debug("Command finished");

		return new RequestProcessorInfo(ProcessorMode.REDIRECT, Path.COMMAND_REDIRECT_TRANSACTION_COMPLETED);
	}

	private void executeRemittances(HttpSession session) throws ApplicationException {

		@SuppressWarnings("unchecked")
		List<Payment> preparedPayments = (List<Payment>) session.getAttribute("prepPayments");
		LOG.trace("Payments to execute -->" + preparedPayments);

		Iterator<Payment> itr = preparedPayments.iterator();
		while (itr.hasNext()) {
			Payment payment = itr.next();
			if (payment.getPaymentTypeId() == PaymentType.CARD_TO_CARD.ordinal() && checkPayment(payment, userLogic)) {
				LOG.trace("Executing remittance  to acc: " + payment.getAccountIdTo() + " amount: "
						+ payment.getMoneyAmount());
				userLogic.insertRemittance(payment.getMoneyAmount(), payment.getAccountIdFrom(),
						payment.getAccountIdTo());
				itr.remove();
				session.setAttribute("prepPayments", preparedPayments);
			}
		}
		LOG.trace("All remittancies executed");

		LOG.trace("Prepared payments after command --> " + preparedPayments);

		session.setAttribute("fromCart", "true");
	}

	private void executeReplenishments(HttpSession session) throws ApplicationException {

		@SuppressWarnings("unchecked")
		List<Payment> preparedPayments = (List<Payment>) session.getAttribute("prepPayments");
		LOG.trace("Payments to execute -->" + preparedPayments);
		Predicate<Payment> predicate = p -> p.getPaymentTypeId() == PaymentType.REPLENISH.ordinal();

		preparedPayments.removeIf(payment -> {
			if (predicate.test(payment))
				LOG.trace("Executing replenishment to acc: " + payment.getAccountIdTo() + " amount: "
						+ payment.getMoneyAmount());
			try {
				userLogic.insertReplenish(payment.getMoneyAmount(), payment.getAccountIdTo());
			} catch (DBException e) {
				e.printStackTrace();
			}
			return true;
		});

		LOG.trace("All replenishments executed, now proceed to executing of remittancies");
		session.setAttribute("prepPayments", preparedPayments);
		LOG.trace("Prepared payments after replenishments executed --> " + preparedPayments);
	}

	private boolean checkPayment(Payment payment, UserLogic userLogic) throws ApplicationException {
		BigDecimal amount = payment.getMoneyAmount();
		Account accountFrom = userLogic.getEntityById(payment.getAccountIdFrom());
		BigDecimal balance = accountFrom.getBalance();
		LOG.trace("balance " + balance + " amount " + amount);
		if (balance.compareTo(amount) < 0) {
			throw new ApplicationException(
					"Not all payments was processed.\nYou have insufficient funds to execute all prepared payments.");
		}
		Account accountTo = userLogic.getEntityById(payment.getAccountIdTo());
		if (accountTo == null) {
			throw new ApplicationException("Account not found");
		} else if (accountTo.getAccountStatusId() == AccountStatus.LOCKED.ordinal()
				|| accountTo.getAccountStatusId() == AccountStatus.CLOSED.ordinal()) {
			throw new ApplicationException("Not all payments was processed.\n " + "Account No. " + accountTo.getId()
					+ " is either locked or does not exist!");
		}
		return true;
	}

}
