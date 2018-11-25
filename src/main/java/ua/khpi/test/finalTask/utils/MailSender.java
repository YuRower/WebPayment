package ua.khpi.test.finalTask.utils;

import java.io.IOException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ua.khpi.test.finalTask.exception.ApplicationException;
import ua.khpi.test.finalTask.utils.mail.ColleagueEmailDecorator;
import ua.khpi.test.finalTask.utils.mail.Email;
import ua.khpi.test.finalTask.utils.mail.EmailDecorator;
import ua.khpi.test.finalTask.utils.mail.FormalEmailDecorator;
import ua.khpi.test.finalTask.utils.mail.IEmail;
import ua.khpi.test.finalTask.utils.mail.SecureEmailDecorator;

public class MailSender {

	private static final Logger LOG = LogManager.getLogger(MailSender.class);

	public MailSender() {
	}

	public void composeMessage(String recipient, String code, HttpServletRequest request) throws ApplicationException {
		LOG.debug("MailSender started");
		Properties properties = loadMailProperties(request);
		Session session = createSession(properties);
		prepareAndSend(session, recipient, code);
		LOG.debug("Message was successfully sent");
	}

	private Properties loadMailProperties(HttpServletRequest request) throws ApplicationException {
		LOG.debug("Loading properties");
		Properties properties = new Properties();
		ServletContext context = request.getServletContext();
		String filename = context.getInitParameter("mail");
		LOG.trace("filename --> " + filename);
		try {
			properties.load(context.getResourceAsStream(filename));
		} catch (IOException ex) {
			LOG.error("An error occured during loading of mail properties", ex);
			throw new ApplicationException("An error occured during loading of mail properties", ex);
		}
		return properties;
	}

	private Session createSession(Properties properties) throws ApplicationException {
		LOG.debug("Creating session");
		Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(properties.getProperty("mail.user.name"),
						properties.getProperty("mail.user.password"));
			}
		});
		session.setDebug(true);
		return session;
	}

	private void prepareAndSend(Session session, String recipient, String code) throws ApplicationException {
		LOG.debug("Preparing message");
		Message message = new MimeMessage(session);

		String link = "<a href = \"http://localhost:8080/WebPayment/controller?command=verify&email=" + recipient
				+ "&code=" + code + "\">this link</a>";
		String content = "Follow " + link + " in order to verify your email.<br/>Payment system mail bot";
		EmailDecorator external = new SecureEmailDecorator(new ColleagueEmailDecorator(
				new FormalEmailDecorator(
						new Email(content))));
		EmailDecorator external1 = new ColleagueEmailDecorator(
				new FormalEmailDecorator(
						new Email(content)));
		EmailDecorator external2 = new  FormalEmailDecorator(new Email(content));
		
		LOG.info(external.getContents());
		LOG.info(external1.getContents());

		try {
			message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
			message.setSubject("A confirmation email");
			message.setContent(external2.getContents(), "text/html");
			Transport.send(message);
		} catch (AddressException e) {
			LOG.error("Wrong email address", e);
			throw new ApplicationException("Wrong email address", e);
		} catch (MessagingException e) {
			LOG.error("Message sending error", e);
			throw new ApplicationException("Message sending error", e);
		}
	}

}
