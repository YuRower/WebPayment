package ua.khpi.test.finalTask.entity.enums;

import ua.khpi.test.finalTask.entity.Account;

public enum AccountStatus {
	OPEN, LOCKED, CLOSED;
	
	public static AccountStatus getStatus(Account account) {
		int statusId = account.getAccountStatusId();
		return AccountStatus.values()[statusId];
	}
	
	public String getName() {
		return name().toLowerCase();
	}
}
