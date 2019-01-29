package ua.khpi.test.finalTask.web.command.user.redirect;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ua.khpi.test.finalTask.exception.ApplicationException;
import ua.khpi.test.finalTask.web.Path;
import ua.khpi.test.finalTask.web.RequestProcessorInfo;
import ua.khpi.test.finalTask.web.RequestProcessorInfo.ProcessorMode;
import ua.khpi.test.finalTask.web.command.Command;



public class RedirectNewAccountCommand extends Command {

	private static final Logger LOG = LogManager.getLogger(RedirectNewAccountCommand.class);

	@Override
	public RequestProcessorInfo execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, ApplicationException {
		LOG.debug("Command starts");
		HttpSession session = request.getSession();

		LOG.trace("Redirecting to new account page");
		
		String card_id = String.valueOf(session.getAttribute("current_card"));
		LOG.debug(  " " + card_id + " " );
		LOG.debug("Command finished");
		return new RequestProcessorInfo(ProcessorMode.FORWARD, Path.PAGE_NEW_ACCOUNT);
	}

}
