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

import lombok.NonNull;
import ua.khpi.test.finalTask.entity.Account;
import ua.khpi.test.finalTask.entity.Card;
import ua.khpi.test.finalTask.entity.enums.AccountStatus;
import ua.khpi.test.finalTask.exception.ApplicationException;
import ua.khpi.test.finalTask.logic.UserLogic;
import ua.khpi.test.finalTask.utils.RegularExpressions;
import ua.khpi.test.finalTask.web.Path;
import ua.khpi.test.finalTask.web.RequestProcessorInfo;
import ua.khpi.test.finalTask.web.RequestProcessorInfo.ProcessorMode;
import ua.khpi.test.finalTask.web.command.Command;

public class CreateNewAccountCommand extends Command {
	private final BigDecimal START_BALANCE = new BigDecimal(0);
	private static final Logger LOG = LogManager.getLogger(CreateNewAccountCommand.class);
	UserLogic userLogic;

	public CreateNewAccountCommand(UserLogic userLogic) {
		this.userLogic = userLogic;
	}

	@Override
	public RequestProcessorInfo execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, ApplicationException {
		LOG.debug("Command starts");

		HttpSession session = request.getSession();

		String accName = request.getParameter("name");

		checkName(accName);
		String card_id1= (String) session.getAttribute("current_card");
		LOG.debug(" " + card_id1 + " " + accName);
		Integer card_id = Integer.valueOf((String)session.getAttribute("current_card"));
		LOG.debug(" " + card_id + " " + accName);
		Card result = null;
		
		if (card_id == null) {
			List<Card> cardList = userLogic.getAllUserCards();
			LOG.debug("cardList" + cardList);
			result = cardList.stream().findFirst().get();
			if (result == null) {
				throw new ApplicationException("First you need add a card");
			}
			LOG.debug("findFirst" + result);
			card_id = result.getId();
		}
		
		
		addAccount(accName, card_id);

		LOG.debug("Command finished");
		return new RequestProcessorInfo(ProcessorMode.REDIRECT, Path.COMMAND_LIST_ACCOUNTS);
	}

	private void addAccount(String accName, int cardID) throws ApplicationException {
		List<Account> accounts = userLogic.getAccountsByCardId(cardID);
		LOG.trace("Cart has " + accounts.size() + " accounts");

		if (accounts.size() >= 5) {
			throw new ApplicationException("You can't have more than 5 accounts in one cart(includes the closet accounts)");
		}

		Account newAccount = new Account(accName, START_BALANCE, AccountStatus.OPEN.ordinal(), cardID);
	
		userLogic.addEntity(newAccount);
		LOG.trace("Account added");
	}

	private void checkName(@NonNull String accName) throws ApplicationException {
		LOG.trace("New account name --> " + accName);
		if (!accName.matches(RegularExpressions.ACCOUNT_NAME)) {
			throw new ApplicationException("Wrong account name");
		}
	}

}
