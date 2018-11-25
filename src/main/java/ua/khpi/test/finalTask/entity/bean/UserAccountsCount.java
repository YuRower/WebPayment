package ua.khpi.test.finalTask.entity.bean;

public class UserAccountsCount {
	String email;
	int accounts;

	@Override
	public String toString() {
		return "UserAccountsCount [email=" + email + ", accounts=" + accounts + "]";
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getAccounts() {
		return accounts;
	}

	public void setAccounts(int accounts) {
		this.accounts = accounts;
	}
	
	
}
