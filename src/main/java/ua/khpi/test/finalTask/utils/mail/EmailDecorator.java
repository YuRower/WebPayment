package ua.khpi.test.finalTask.utils.mail;

public class EmailDecorator implements IEmail {
	private IEmail email;

	public EmailDecorator(IEmail email) {
		this.email = email;
	}

	@Override
	public String getContents() {
		return email.getContents();

	}
}