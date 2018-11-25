package ua.khpi.test.finalTask.web.command.user;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ua.khpi.test.finalTask.entity.Payment;
import ua.khpi.test.finalTask.exception.ApplicationException;
import ua.khpi.test.finalTask.web.Path;
import ua.khpi.test.finalTask.web.RequestProcessorInfo;
import ua.khpi.test.finalTask.web.RequestProcessorInfo.ProcessorMode;
import ua.khpi.test.finalTask.web.command.Command;



public class SortPaymentsCommand extends Command {
	
	private static final Logger LOG = LogManager.getLogger(SortPaymentsCommand.class);

	@Override
	public RequestProcessorInfo execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, ApplicationException {
		LOG.debug("Command starts");
		
		HttpSession session = request.getSession();
		@SuppressWarnings("unchecked")
		List<Payment> payments= (List<Payment>) session.getAttribute("accPayments");
		if(payments == null) {
			LOG.trace("Cant get payments from session");
			throw new ApplicationException("Can't get payments");
		}
		
		String sortBy = (String) session.getAttribute("paymentsOrder");
		LOG.trace("List will be sorted by "+sortBy);
		switch (sortBy) {
			case "number":
				payments.sort((paym1, paym2)->paym1.getId()-paym2.getId());
				break;
			case "dateDSC":
				payments.sort((paym1, paym2)->paym1.getDate().compareTo(paym2.getDate()));
				break;
			case "dateASC":
				payments.sort((paym1, paym2)->paym2.getDate().compareTo(paym1.getDate()));
				break;
			default:
				throw new ApplicationException("Can't get sort parameter");
		}

		session.setAttribute("accPayments", payments);
		
		LOG.debug("Command finished");
		return new RequestProcessorInfo(ProcessorMode.FORWARD, Path.PAGE_TRANSACTIONS);
	}
}
