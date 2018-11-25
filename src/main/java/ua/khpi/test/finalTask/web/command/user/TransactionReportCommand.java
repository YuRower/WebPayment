package ua.khpi.test.finalTask.web.command.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import ua.khpi.test.finalTask.dao.PaymentsDAO;
import ua.khpi.test.finalTask.dao.mysql.MysqlDAOFactory;
import ua.khpi.test.finalTask.entity.Payment;
import ua.khpi.test.finalTask.exception.ApplicationException;
import ua.khpi.test.finalTask.web.RequestProcessorInfo;
import ua.khpi.test.finalTask.web.command.Command;



public class TransactionReportCommand extends Command {

	private static final Logger LOG = LogManager.getLogger(TransactionReportCommand.class);

	@Override
	public RequestProcessorInfo execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, ApplicationException {
		LOG.debug("Command starts");

		String paymentId = request.getParameter("paymentId");
		Payment payment = getPayment(paymentId);
		ServletOutputStream outputStream = response.getOutputStream();
		response.setContentType("application/pdf");

		switch (payment.getPaymentTypeId()) {
		case 0:
			generateRemittanceBill(payment, outputStream);
			break;
		case 1:
			generateReplenishBill(payment, outputStream);
			break;
		}

		LOG.debug("Command finished");
		return null;
	}

	private void generateRemittanceBill(Payment payment, ServletOutputStream outputStream) {

		Document document = new Document();
		try {
			Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
			PdfWriter.getInstance(document, outputStream);
			document.open();
			document.add(new Paragraph("Remmitance bill", catFont));
			document.add(new Paragraph(""));
			document.add(new Paragraph("From account: " + String.format("%05d", payment.getAccountIdFrom())));
			document.add(new Paragraph("To account: " + String.format("%05d", payment.getAccountIdTo())));
			document.add(new Paragraph("Amount: " + payment.getMoneyAmount() + "$"));
			document.add(new Paragraph("Date: " + payment.getDate()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		document.close();

	}

	private void generateReplenishBill(Payment payment, ServletOutputStream outputStream) {

		Document document = new Document();
		try {
			Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
			PdfWriter.getInstance(document, outputStream);
			document.open();
			document.add(new Paragraph("Replenish bill", catFont));
			document.add(new Paragraph(""));
			document.add(new Paragraph("To account: " + String.format("%05d", payment.getAccountIdTo())));
			document.add(new Paragraph("Amount: " + payment.getMoneyAmount() + "$"));
			document.add(new Paragraph("Date: " + payment.getDate()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		document.close();

	}

	private Payment getPayment(String paymentId) throws ApplicationException {
		PaymentsDAO paymentsDAO = MysqlDAOFactory.getInstance().getPaymentsDAO();
		return paymentsDAO.getPaymentById(Integer.parseInt(paymentId));
	}

}
