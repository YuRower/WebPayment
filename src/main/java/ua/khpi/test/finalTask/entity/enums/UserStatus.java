package ua.khpi.test.finalTask.entity.enums;

import ua.khpi.test.finalTask.entity.User;

public enum UserStatus {
	VALIDATION, ALLOWED, BLOCKED;
	
	public static UserStatus getStatus(User user) {
		int statusId = user.getUserStatusId();
		return UserStatus.values()[statusId];
	}
	
	public String getName() {
		return name().toLowerCase();
	}
}
