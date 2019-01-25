package ua.khpi.test.finalTask.web.command.commons;

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

public class ChangeLanguageCommand extends Command {

	private static final Logger LOG = LogManager.getLogger(ChangeLanguageCommand.class);
	
	@Override
	public RequestProcessorInfo execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, ApplicationException {
		LOG.debug("Command starts");
		String locale = request.getParameter("lang");
		LOG.trace("Setting locale -->"+locale);
		
		HttpSession session = request.getSession();
		switch(locale) {
		case "ru":
			session.setAttribute("language", "ru");
			break;
		case "en":
		default:
			session.setAttribute("language", "en");
		}
		LOG.debug("Command finished");

		return new RequestProcessorInfo(ProcessorMode.REDIRECT,Path.PAGE_LOGIN);
	}

}
