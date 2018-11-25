package ua.khpi.test.finalTask.web.command.superuser;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ua.khpi.test.finalTask.dao.AbstractDAOFactory;
import ua.khpi.test.finalTask.dao.UserDAO;
import ua.khpi.test.finalTask.dao.AbstractDAOFactory.FactoryTypes;
import ua.khpi.test.finalTask.entity.User;
import ua.khpi.test.finalTask.entity.enums.UserStatus;
import ua.khpi.test.finalTask.exception.ApplicationException;
import ua.khpi.test.finalTask.logic.Superuser;
import ua.khpi.test.finalTask.web.Path;
import ua.khpi.test.finalTask.web.RequestProcessorInfo;
import ua.khpi.test.finalTask.web.RequestProcessorInfo.ProcessorMode;
import ua.khpi.test.finalTask.web.command.Command;

public class SuperuserActionCommand extends Command {

	private static final Logger LOG = LogManager.getLogger(SuperuserActionCommand.class);
	Superuser superuser;

	public SuperuserActionCommand(Superuser superuser) {
		this.superuser = superuser;
	}

	@Override
	public RequestProcessorInfo execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, ApplicationException {
		LOG.debug("Command starts");

		int adminId = getAdminId(request);

		String action = request.getParameter("action");

		performAction(action, adminId);

		LOG.debug("Command finished");
		return new RequestProcessorInfo(ProcessorMode.REDIRECT, Path.COMMAND_LIST_ADMINS);
	}

	private void performAction(String action, int adminId) throws ApplicationException {
		LOG.trace("Action to perform --> " + action);

		switch (action) {
		case "block":
			blockAdminById(adminId);
			break;
		case "unlock":
			unlockAdminById(adminId);
			break;
		case "delete":
			deleteAdminById(adminId);
			break;
		default:
			throw new ApplicationException("unknown command");
		}
	}

	private int getAdminId(HttpServletRequest request) {
		String adminId = request.getParameter("adminId");
		LOG.trace("Admin id --> " + adminId);
		return Integer.parseInt(adminId);
	}

	private void blockAdminById(int adminId) throws ApplicationException {
		AbstractDAOFactory mysqlFactory = AbstractDAOFactory.getDAOFactory(FactoryTypes.MYSQL);
		UserDAO userDao = mysqlFactory.getUserDAO();
		User admin = userDao.getEntityById(adminId);
		LOG.trace("Admin before blocking --> " + admin);
		admin.setUserStatusId(UserStatus.BLOCKED.ordinal());
		LOG.trace("Admin after blocking --> " + admin);
		userDao.update(admin);
	}

	private void unlockAdminById(int adminId) throws ApplicationException {
		AbstractDAOFactory mysqlFactory = AbstractDAOFactory.getDAOFactory(FactoryTypes.MYSQL);
		UserDAO userDao = mysqlFactory.getUserDAO();
		User admin = userDao.getEntityById(adminId);
		LOG.trace("Admin before unlocking --> " + admin);
		admin.setUserStatusId(UserStatus.ALLOWED.ordinal());
		LOG.trace("Admin after unlocking --> " + admin);
		userDao.update(admin);
	}

	private void deleteAdminById(int adminId) throws ApplicationException {
		AbstractDAOFactory mysqlFactory = AbstractDAOFactory.getDAOFactory(FactoryTypes.MYSQL);
		UserDAO userDao = mysqlFactory.getUserDAO();
		User admin = userDao.getEntityById(adminId);
		LOG.trace("Admin that will be deleted --> " + admin);
		userDao.removeEntity(admin);
	}

}
