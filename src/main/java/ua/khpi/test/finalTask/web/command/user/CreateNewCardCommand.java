package ua.khpi.test.finalTask.web.command.user;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ua.khpi.test.finalTask.entity.Card;
import ua.khpi.test.finalTask.entity.User;
import ua.khpi.test.finalTask.entity.enums.Fee;
import ua.khpi.test.finalTask.exception.ApplicationException;
import ua.khpi.test.finalTask.logic.UserLogic;
import ua.khpi.test.finalTask.web.Path;
import ua.khpi.test.finalTask.web.RequestProcessorInfo;
import ua.khpi.test.finalTask.web.RequestProcessorInfo.ProcessorMode;
import ua.khpi.test.finalTask.web.command.Command;

public class CreateNewCardCommand extends Command {

	private static final Logger LOG = LogManager.getLogger(CreateNewCardCommand.class);
	UserLogic userLogic;

	public CreateNewCardCommand(UserLogic userLogic) {
		this.userLogic = userLogic;
	}

	@Override
	public RequestProcessorInfo execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, ApplicationException {
		LOG.debug("Command starts");
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		LOG.debug("user" + user);

		String cardType = request.getParameter("card_type");
		LOG.debug("cardType" +cardType);

		int fee;
		LocalDate expDate;
		if (cardType.equals("gold")) {
			fee = Fee.valueOf("GOLD").ordinal()+1;
			expDate = LocalDate.now().plusYears(7);
		} else if (cardType.equals("silver")) {
			fee = Fee.valueOf("SILVER").ordinal()+1;
			expDate = LocalDate.now().plusYears(5);
		} else {
			fee = Fee.valueOf("DEFAULT").ordinal()+1;
			expDate = LocalDate.now().plusYears(3);

		}
		BigDecimal cardNumber = generateCartNumber();
		Card card = new Card(cardNumber, expDate, cardType, fee,user.getId());
		userLogic.insertCard(card);
		LOG.debug("Command finished");
		LOG.debug("cardType" +cardType);

		return new RequestProcessorInfo(ProcessorMode.REDIRECT, Path.COMMAND_LIST_CARDS);
	}

	public BigDecimal generateCartNumber() {
		String bin = "5169";
		int length = 16;
		Random random = new Random(System.currentTimeMillis());

		int randomNumberLength = length - (bin.length() + 1);

		StringBuilder builder = new StringBuilder(bin);
		for (int i = 0; i < randomNumberLength; i++) {
			int digit = random.nextInt(10);
			builder.append(digit);
		}

		int checkDigit = this.getCheckDigit(builder.toString());
		builder.append(checkDigit);

		return new BigDecimal(builder.toString());
	}

	private int getCheckDigit(String number) {
		int sum = 0;
		for (int i = 0; i < number.length(); i++) {
			int digit = Integer.parseInt(number.substring(i, (i + 1)));

			if ((i % 2) == 0) {
				digit = digit * 2;
				if (digit > 9) {
					digit = (digit / 10) + (digit % 10);
				}
			}
			sum += digit;
		}
		int mod = sum % 10;
		return ((mod == 0) ? 0 : 10 - mod);
	}
}
