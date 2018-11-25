package ua.khpi.test.finalTask.utils.mail;

public class Email implements IEmail {

	private String content;

	public Email(String content) {
		this.content = content;
	}

	@Override
	public String getContents() {
		return "\nContents: " + content;
	}

}