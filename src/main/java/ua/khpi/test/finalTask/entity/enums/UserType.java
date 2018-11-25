package ua.khpi.test.finalTask.entity.enums;

import ua.khpi.test.finalTask.entity.User;

public enum UserType {
	USER, ADMIN, SUPERUSER;
	
	public static UserType getType(User user) {
		int typeId = user.getUserTypeId();
		return UserType.values()[typeId];
	}
	
	public String getName() {
		return name().toLowerCase();
	}
}
