package ua.khpi.test.finalTask.entity;

public class Request{

	private int userId;
	private int accountId;
	
	public Request() {	}
	
	public Request(int userId, int accountId) {
		super();
		this.userId = userId;
		this.accountId = accountId;
	}

	public int getUserId() {
		return this.userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getAccountId() {
		return this.accountId;
	}

	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}

	@Override
	public String toString() {
		return "Request [userId=" + userId + ", accountId=" + accountId +"]";
	}

}
