package ua.khpi.test.finalTask.web.command.admin;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ua.khpi.test.finalTask.entity.User;
import ua.khpi.test.finalTask.exception.ApplicationException;
import ua.khpi.test.finalTask.logic.AdminLogic;
import ua.khpi.test.finalTask.web.Path;
import ua.khpi.test.finalTask.web.RequestProcessorInfo;
import ua.khpi.test.finalTask.web.RequestProcessorInfo.ProcessorMode;
import ua.khpi.test.finalTask.web.command.Command;

public class GetUserListCommand extends Command {
	AdminLogic adminLogic;
	private static final Logger LOG = LogManager.getLogger(GetUserListCommand.class);

	public GetUserListCommand(AdminLogic adminLogic) {
		this.adminLogic = adminLogic;
	}

	@Override
	public RequestProcessorInfo execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, ApplicationException {
		LOG.debug("Command starts");
		HttpSession session = request.getSession();

		List<User> list = getAllUserList();
		LOG.trace("Returned user list --> " + list);

		session.setAttribute("userList", list);
		LOG.debug("Command finished");
		return new RequestProcessorInfo(ProcessorMode.FORWARD, Path.PAGE_USER_LIST);
	}

	private List<User> getAllUserList() throws ApplicationException {
		return adminLogic.getAllUsers();
	}


}
