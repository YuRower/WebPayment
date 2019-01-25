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

public class ListUserCardsCommand extends Command {
	UserLogic userLogic;
	private static final Logger LOG = LogManager.getLogger(ListUserCardsCommand.class);

	public ListUserCardsCommand(UserLogic userLogic) {
		this.userLogic = userLogic;
	}

	@Override
	public RequestProcessorInfo execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, ApplicationException {
		LOG.debug("Command starts");
		
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		LOG.debug("Get user with id " + user.getId());
		
		LOG.trace("Get all user cards");
		
		List<Card> cards = userLogic.getAllCardsByUserId(user.getId());
		LOG.debug("cards " + cards);
		session.setAttribute("cards", cards);
		
		LOG.debug("Command finished");
		return new RequestProcessorInfo(ProcessorMode.FORWARD, Path.COMMAND_LIST_ACCOUNTS);
	}

}
