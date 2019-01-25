package ua.khpi.test.finalTask.web.command.user;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ua.khpi.test.finalTask.entity.Card;
import ua.khpi.test.finalTask.entity.User;
import ua.khpi.test.finalTask.exception.ApplicationException;
import ua.khpi.test.finalTask.logic.UserLogic;
import ua.khpi.test.finalTask.web.Path;
import ua.khpi.test.finalTask.web.RequestProcessorInfo;
import ua.khpi.test.finalTask.web.RequestProcessorInfo.ProcessorMode;
import ua.khpi.test.finalTask.web.command.Command;

public class DeleteCartCommand extends Command {
	private static final Logger LOG = LogManager.getLogger(DeleteCartCommand.class);
	UserLogic userLogic;

	public DeleteCartCommand(UserLogic userLogic) {
		this.userLogic = userLogic;
	}

	@Override
	public RequestProcessorInfo execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, ApplicationException {
		LOG.debug("Command starts");
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		LOG.debug("Get user with id " + user.getId());
		String cardId = request.getParameter("cardID");
		LOG.debug("card id that will be deleted" + cardId);
		deleteCart(user, cardId);

		LOG.debug("Command finished");

		return new RequestProcessorInfo(ProcessorMode.REDIRECT, Path.COMMAND_REDIRECT_CART_REMOVAL_COMPLETED);

	}

	private void deleteCart(User user, String cardId) throws ApplicationException {
		List<Card> allCard = userLogic.getAllCardsByUserId(user.getId());
		Card matchingCard = allCard.stream().filter(p -> p.getCardNumber().toString().equals(cardId)).findAny()
				.orElse(null);
		LOG.debug("cart that will be deleted" + matchingCard);
		
		if (matchingCard == null) {
			throw new ApplicationException("Card with such number not found");
		} else {

			boolean isDeleted = userLogic.deleteCard(matchingCard);
			LOG.debug("card status removed" + isDeleted);

		}
	}

}
