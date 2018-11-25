package ua.khpi.test.finalTask.web.command.superuser;

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
import ua.khpi.test.finalTask.dao.UserDAO;
import ua.khpi.test.finalTask.entity.User;
import ua.khpi.test.finalTask.exception.ApplicationException;
import ua.khpi.test.finalTask.logic.Superuser;
import ua.khpi.test.finalTask.web.Path;
import ua.khpi.test.finalTask.web.RequestProcessorInfo;
import ua.khpi.test.finalTask.web.RequestProcessorInfo.ProcessorMode;
import ua.khpi.test.finalTask.web.command.Command;


public class ListAdminsCommand extends Command {

	private static final Logger LOG = LogManager.getLogger(ListAdminsCommand.class);
	Superuser superuser;

	public ListAdminsCommand(Superuser superuser) {
		this.superuser = superuser;
	}

	@Override
	public RequestProcessorInfo execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, ApplicationException {
		LOG.debug("Command starts");

		HttpSession session = request.getSession();

		List<User> admins = getAllAdmins();
		LOG.trace("Admins --> " + admins);

		session.setAttribute("admins", admins);

		LOG.debug("Command finished");
		return new RequestProcessorInfo(ProcessorMode.FORWARD, Path.PAGE_LIST_ADMINS);
	}

	private List<User> getAllAdmins() throws ApplicationException {
		
		return superuser.getAllAdmins();
	}
}
