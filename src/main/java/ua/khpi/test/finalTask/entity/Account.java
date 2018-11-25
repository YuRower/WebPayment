package ua.khpi.test.finalTask.entity;

import java.math.BigDecimal;

public class Account extends AbstractEntity {


	private int userId;
	private String name;
	private BigDecimal balance;
	private int accountStatusId;

	public int getUserId() {
		return this.userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getBalance() {
		return this.balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public int getAccountStatusId() {
		return this.accountStatusId;
	}

	public void setAccountStatusId(int accountStatusId) {
		this.accountStatusId = accountStatusId;
	}

	@Override
	public String toString() {
		return "Account [id=" + getId() + ", userId=" + userId + ", name=" + name + ", balance=" + balance
				+ ", accountStatusId=" + accountStatusId + "]";
	}
}
