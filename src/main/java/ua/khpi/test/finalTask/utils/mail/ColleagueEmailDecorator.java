package ua.khpi.test.finalTask.utils.mail;

public class ColleagueEmailDecorator extends EmailDecorator {
	public ColleagueEmailDecorator(IEmail email) {
		super(email);
	}

	@Override
	public String getContents() {
		return "What's up bro?\n" + super.getContents() + "\nYours best mate,\nMr...";
	}

}
